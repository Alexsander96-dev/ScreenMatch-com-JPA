package com.alura.screenmacth.screenmatchComJpa.repository;

import com.alura.screenmacth.screenmatchComJpa.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

//Interface criada para pode fazer o crud no banco de dados
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
