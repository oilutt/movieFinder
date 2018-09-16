package oilutt.sambatechproject.utils.integration;

import io.reactivex.Observable;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.Configuration;
import oilutt.sambatechproject.model.DiscoverResponse;
import oilutt.sambatechproject.model.Movie;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @GET(Constants.Retrofit.CONFIGURATION)
    Observable<Configuration> getConfiguration(@Query("api_key") String apiKey);

    @GET(Constants.Retrofit.DISCOVER_MOVIES)
    Observable<DiscoverResponse> getMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("include_adult") Boolean adult, @Query("page") Integer page);

    @GET(Constants.Retrofit.MOVIE_DETAILS)
    Observable<Movie> getMovieDetails(@Path("movie_id") String movieId, @Query("api_key") String apiKey, @Query("language") String language);

    @GET(Constants.Retrofit.DISCOVER_MOVIES)
    Observable<DiscoverResponse> getMoviesOrderBy(@Query("api_key") String apiKey, @Query("sort_by") String sortBy, @Query("language") String language, @Query("include_adult") Boolean adult, @Query("page") Integer page);
}
