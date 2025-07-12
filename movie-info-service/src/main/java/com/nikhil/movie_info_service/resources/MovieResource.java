package com.nikhil.movie_info_service.resources;

import com.nikhil.movie_info_service.models.models.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @RequestMapping("/{movieId}")
    public Movie getMovie(@PathVariable("movieId") String movieId){
        return new Movie(movieId,"KGF");
    }
}
