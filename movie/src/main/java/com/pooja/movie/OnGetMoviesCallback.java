package com.pooja.movie;

import com.pooja.movie.model.Movie;

import java.util.List;

/*
    Callback interface to handle http request response
 */
public interface OnGetMoviesCallback {

    void onSuccess(int page, List<Movie> movies);

    void onError(String message);
}
