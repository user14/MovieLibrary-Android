package com.pooja.movie.repository;

import android.util.Log;

import com.pooja.movie.OnGetMoviesCallback;
import com.pooja.movie.TMDbApi;
import com.pooja.movie.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    Repository to get data from Http request using retrofit library
 */
public class MoviesRepository {

    private static final String TAG = "MovieRepository";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    private static MoviesRepository repository;

    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    /*
        This method will return singleton MoviesRepository instance
     */
    public static MoviesRepository getInstance()
    {
        if(repository == null)
        {
            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }
        return  repository;
    }

    /*
        @param : page number, response interface, apikey for database
        Get all movies list from api
     */
    public void getMovies(int page, final OnGetMoviesCallback callback, String apiKey)
    {
        api.getAllMovies(apiKey, LANGUAGE,page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if(response.isSuccessful())
                        {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getPage(),moviesResponse.getMovies());
                            } else {

                                callback.onError(response.message());
                            }
                        }else {
                            Log.d(TAG, response.message());
                            callback.onError(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError(t.getLocalizedMessage());
                    }
                });
    }
    /*
        @param : movie search text, page number, response interface, apikey for database
        search all movies list with search text from api
     */
    public void getSearchedMovies(String searchText,int page, final OnGetMoviesCallback callback, String apiKey)
    {
        api.getSearchedMovies(apiKey, searchText,page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if(response.isSuccessful())
                        {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null ) {
                                if(moviesResponse.getMovies().size()>0)
                                    callback.onSuccess(moviesResponse.getPage(),moviesResponse.getMovies());
                                else
                                    callback.onError("No Movie Available");
                            } else {
                                callback.onError(response.message());
                            }
                        }else {
                            Log.d(TAG, response.message());
                            callback.onError(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError(t.getLocalizedMessage());
                    }
                });
    }
}
