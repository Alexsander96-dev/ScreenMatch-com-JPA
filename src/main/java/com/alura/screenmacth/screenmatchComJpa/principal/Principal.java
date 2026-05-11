package com.alura.screenmacth.screenmatchComJpa.principal;



import com.alura.screenmacth.screenmatchComJpa.model.DadosSerie;
import com.alura.screenmacth.screenmatchComJpa.model.DadosTemporada;
import com.alura.screenmacth.screenmatchComJpa.model.Episodio;
import com.alura.screenmacth.screenmatchComJpa.model.Serie;
import com.alura.screenmacth.screenmatchComJpa.repository.SerieRepository;
import com.alura.screenmacth.screenmatchComJpa.service.ConsumoApi;
import com.alura.screenmacth.screenmatchComJpa.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=9c0d6e0";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    //variavel local feita para a busca dos episodios
    //feito local para poder buscar os episodios pelas series salva no banco
    //nao mais na web
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Lista de Séries buscadas
                    
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    //menu alterado para salvar serie no BD
    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //        dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    //adicionado o metodo listarSeriesBuscadas(); para recuperar as series salvas no BD
    //adicionado interação com o usuario para ele escolher a serie que quer buscar os episodios
    //adicionado o Optional para ver se a serie com o nome buscado existe no BD
    //adicionado um stream na list<episiodio> para buscar cada temporada da serie escolhida e salvar os episodios
    //criando um relacionamento bidirecional das temporadas/episodios com a serie escolhida
    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha a serie pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();


            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }else {
            System.out.println("Série não encontrada!");
        }
    }
    //metodo mudado, para buscar as series salvas no BD
    //mudou o list<Serie> para series para variavel ser local e utilizada em outros metodos
    //para poder usar as lista buscadas no BD
    private void listarSeriesBuscadas(){
        series = repositorio.findAll();
        //pega a lista criada orgazina
        //compara por genero e orgazina
        //depois imprimi cada serie armazenada
       series.stream()
               .sorted(Comparator.comparing(Serie::getGenero))
               .forEach(System.out::println);
    }
}
