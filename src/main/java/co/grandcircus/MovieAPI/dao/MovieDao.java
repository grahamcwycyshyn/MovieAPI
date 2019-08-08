package co.grandcircus.MovieAPI.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.MovieAPI.entity.Movie;

public interface MovieDao extends JpaRepository<Movie, Long> {

	List<Movie> findByTitleContainsIgnoreCase(String titleMatch);
	
	List<Movie> findByCategoryIgnoreCase(String category);
	
}
