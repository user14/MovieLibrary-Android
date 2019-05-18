package com.pooja.movie;

import com.pooja.movie.model.Movie;

import java.util.List;

public interface OnGetMoviesCallback {
    void onSuccess(int page, List<Movie> movies);

    void onError(String message);
}
