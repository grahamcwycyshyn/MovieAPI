package co.grandcircus.MovieAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.MovieAPI.dao.MovieDao;
import co.grandcircus.MovieAPI.entity.Movie;

@RestController
public class MoviesApiController {

	@Autowired
	private MovieDao dao;
	
	@GetMapping("/movie/{id}")
	public Movie movie(@PathVariable("id") Long id) {
		return dao.findById(id).get();
	}

	@GetMapping("/movies")
	public List<Movie> listMovies(
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value="title", required=false) String title) {
		if ((category == null || category.isEmpty()) && (title == null || title.isEmpty())) {
			return dao.findAll();
		} else if(title == null || title.isEmpty()){
			return dao.findByCategoryIgnoreCase(category);
		} else {
			return dao.findByTitleContainsIgnoreCase(title);
		}
	}

	@GetMapping("/movies/random")
	public Movie randomMovie(@RequestParam(value = "category", required = false) String category) {
		if (category == null || category.isEmpty()) {
			List<Movie> list = dao.findAll();
			int random = (int) (Math.random() * list.size());
			long rando = (long) random;
			Long rand = (Long) (rando + 1);
			return dao.findById(rand).get();
		} else { 
			List<Movie> list = dao.findByCategoryIgnoreCase(category);
			long random = (long) (Math.random() * list.size());
			return dao.findById(list.get((int) random).getId()).get();
		}
	}

	@GetMapping("/movies/random-list")
	public ArrayList<Movie> randomMovieList(@RequestParam(value="quantity", required=false) Integer quantity) {
		List<Movie> list = dao.findAll();
		ArrayList<Movie> result = new ArrayList<Movie>();
		for(int i = 0; i < quantity; i++) {
			long random = (long) (Math.random()*list.size()); 
			if(result.contains(list.get((int) random))) {
				quantity++;
			} else {
			result.add(list.get((int) random));
			}
		}
		return result;
	}
	
	@GetMapping("/categories")
	public TreeSet<String> listCategories() {
		List<Movie> list = dao.findAll();
		TreeSet<String> categories = new TreeSet<String>();
		for(int i = 0; i < list.size(); i++) {
			categories.add(list.get(i).getCategory());
			}
		return categories;
	}
}
