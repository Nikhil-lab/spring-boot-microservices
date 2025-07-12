package com.nikhil.movie_catalog_service.resources;

import com.nikhil.movie_catalog_service.models.CatalogItem;
import com.nikhil.movie_catalog_service.models.Movie;
import com.nikhil.movie_catalog_service.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {


//    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public MovieCatalogResource(RestTemplate restTemplate,WebClient.Builder builder) {
//        this.restTemplate = restTemplate;
        this.webClientBuilder = builder;
    }

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(String userId){


        //Movie movie = restTemplate.getForObject("http://localhost:8082/movies/ad", Movie.class);

        // get all rated movie IDs
        List<Rating> ratings = Arrays.asList(
                new Rating("KGF",5),
                new Rating("KGF2",4)
        );


        //For each movie ID, call movie info service and get details
        return ratings.stream().map(rating ->{

//            RestTemplate way
//            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();

            assert movie != null;
            return new CatalogItem(movie.getName(), "test cool movie", rating.getRating());
        }).collect(Collectors.toList());

    }
}
