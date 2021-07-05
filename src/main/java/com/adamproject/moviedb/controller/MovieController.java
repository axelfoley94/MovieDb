package com.adamproject.moviedb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.entity.Review;
import com.adamproject.moviedb.service.MovieService;
import com.adamproject.moviedb.service.MyUserDetailsService;
import com.adamproject.moviedb.service.ReviewService;

//@RequestMapping("/movies/")
@Controller
public class MovieController {
	
	@Value("#{'${list.categories}'.split(',')}") 
	private List<String> movieCategories;

	MovieService movieService;
	MyUserDetailsService myUserDetailsService;
	ReviewService reviewService;
	
	@Autowired
	public MovieController(MovieService movieService, MyUserDetailsService myUserDetailsService,
			ReviewService reviewService) {
		super();
		this.movieService = movieService;
		this.myUserDetailsService = myUserDetailsService;
		this.reviewService = reviewService;
	}
	
	@GetMapping("/movies")
	public String MoviePaginated(
			@RequestParam(value = "pageNo", defaultValue = "1") String pageNo,
	        @RequestParam(value = "sortField", defaultValue =  "id") String sortField,
	        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
	        Model model) {
		
			int pageSize = 10;
			
			Page <Movie> page = movieService.findPaginated(Integer.parseInt(pageNo), pageSize, sortField, sortDir);
	        List <Movie> movieList = page.getContent();
	        
	        model.addAttribute("currentPage", Integer.parseInt(pageNo));
	        model.addAttribute("totalPages", page.getTotalPages());
	        model.addAttribute("totalItems", page.getTotalElements());
	        model.addAttribute("sortField", sortField);
	        model.addAttribute("sortDir", sortDir);
	        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	        model.addAttribute("movieList",movieList);
	        
			return "movies";
	}

	@GetMapping("/movie")
	public String getMovies(Model model,@RequestParam("id") Long id,@AuthenticationPrincipal UserDetails actualUser) {
		
		boolean isLogged = actualUser!=null ? true : false;
		Movie movie = movieService.findOne(id);
		
		boolean isWatchListed = myUserDetailsService.findByUserName(actualUser.getUsername()).getWatchList().contains(movie) ? true : false;

		
		model.addAttribute("movie",movie);
		model.addAttribute("newReview",new Review());
		model.addAttribute("isLogged",isLogged);
		model.addAttribute("reviews",reviewService.lastThreeReviews(movie));
		model.addAttribute("isWatchListed",isWatchListed);
		model.addAttribute("username",actualUser.getUsername());
		
		return "movieDetails";
	}
	
	@GetMapping("/newMovie")
	public String newMovie(Model model) {
		
		Movie newMovie = new Movie();
	
		model.addAttribute("newMovie",newMovie);	
		model.addAttribute("categories", movieCategories);
		
		return "newMovie";
	}
	
	@PostMapping("/newMovie")
	public String newMoviePost(@ModelAttribute("newMovie") Movie movie, @RequestParam("fileImage") MultipartFile multipartfile, RedirectAttributes ra) {
		
		String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
		
		movie.setImgName(fileName);
				
		Movie savedMovie = movieService.addMovie(movie);
		
		Path uploadPath = Paths.get("./brand-logos/"+savedMovie.getId());
		
		if(!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			InputStream inputStream = multipartfile.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ra.addFlashAttribute("message", "New Movie uploaded");
		
		return "redirect:/";
	}
	
	@PostMapping("/newReview")
	public String newReviewPost(@ModelAttribute("newReview") Review review,@RequestParam("id") Long id,@AuthenticationPrincipal UserDetails actualUser,RedirectAttributes ra) {
		
		review.setMovie(movieService.findOne(id));
		review.setUser(myUserDetailsService.findByUserName(actualUser.getUsername()));
		review.setPostDate(LocalDate.now());
		
		reviewService.saveReview(review);
		
		return "redirect:/";
	}
	
	@PostMapping("/addMyMovie")
	public String addMyMovie(@RequestParam("id") Long movieId,@AuthenticationPrincipal UserDetails actualUser,RedirectAttributes ra) {

		myUserDetailsService.findByUserName(actualUser.getUsername()).getWatchList().add(movieService.findOne(movieId));
		return "redirect:/";
		
	}
	
}
