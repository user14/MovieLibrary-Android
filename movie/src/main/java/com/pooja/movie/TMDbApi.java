package com.pooja.movie;

import com.pooja.movie.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("movie/popular")
    Call<MoviesResponse> getAllMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("search/movie")
    Call<MoviesResponse> getSearchedMovies(
            @Query("api_key") String apiKey,
            @Query("query") String searchText,
            @Query("page") int page
    );
}