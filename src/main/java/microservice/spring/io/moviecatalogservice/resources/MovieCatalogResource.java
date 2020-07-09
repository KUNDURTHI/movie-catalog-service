package microservice.spring.io.moviecatalogservice.resources;

import microservice.spring.io.moviecatalogservice.models.CatalogItem;
import microservice.spring.io.moviecatalogservice.models.Movie;
import microservice.spring.io.moviecatalogservice.models.Rating;
import microservice.spring.io.moviecatalogservice.models.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
  //RestTemplate does synchronus calls,
  //which means we need to wait for the first API call
  //that need to be completed for the next call.

    private RestTemplate restTemplate; //Dependency Injection

  /*  //Web client is used for reactive programming, which means it does asynchronus calls.
  @Qualifier("getWebClientBuilder")
  @Autowired
    private  WebClient.Builder builder;
*/

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) throws Exception {

        //RestTemplate restTemplate = new RestTemplate();
       UserRatings userRatings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId,UserRatings.class);

        return userRatings.getUserRatings().stream().map(rating -> {

         //for each movie id, call movie info service and get details.

            Movie movie =   restTemplate.getForObject("http://movie-info-service/movies/" + rating.getName(),Movie.class);
            //bodyToMono does the asynchronus part of job.
         //   Movie movie = builder.build().get().uri("http://localhost:8083/movies /" + rating.getName()).retrieve().bodyToMono(Movie.class).block();
            return new CatalogItem(movie.getName(), "Desc", rating.getRating());}).collect(Collectors.toList());
    }
}

