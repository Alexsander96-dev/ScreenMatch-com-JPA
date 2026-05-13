package com.alura.screenmacth.screenmatchComJpa.repository;

import com.alura.screenmacth.screenmatchComJpa.model.Categoria;
import com.alura.screenmacth.screenmatchComJpa.model.DadosTemporada;
import com.alura.screenmacth.screenmatchComJpa.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//Interface criada para pode fazer o crud no banco de dados
public interface SerieRepository extends JpaRepository<Serie, Long> {

    //adicionado metodo para buscar a serie com um trecho do nome
    //tambem ignorando letras maiusculas e minusculas
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    //adicionado metodo para buscar serie pelo nome do ator
    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    //adicionado metodo para buscar serie pelo nome do ator e pelo criterio de avaliação
    //no caso maior ou igual que o parametro que for adicionado
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);


    //todas as séries que contenham um número máximo de temporadas e uma avaliação mínima.
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);
}
