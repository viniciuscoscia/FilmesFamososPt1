package com.example.viniciuscoscia.filmesfamosos.utils;

import android.util.Log;

import com.example.viniciuscoscia.filmesfamosos.entity.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesJsonUtils {

    private static final String LOG_TAG = MoviesJsonUtils.class.getName();

    public static ArrayList<Movie> jsonToMovieList(String jsonString) throws JSONException {
        ArrayList<Movie> listaFilmes = new ArrayList<Movie>();

        JSONArray moviesJson = new JSONObject(jsonString).optJSONArray("results");

        Log.v(LOG_TAG, moviesJson.toString());

        for(int i = 0; i < moviesJson.length(); i++){
            Movie movie = new Movie();

            JSONObject movieJson =  moviesJson.optJSONObject(i);

            movie.setVoteCount(movieJson.optInt("vote_count"));
            movie.setId(movieJson.optLong("id"));
            movie.setVideo(movieJson.optBoolean("video"));
            movie.setVoteAverage(movieJson.optDouble("vote_average"));
            movie.setTitle(movieJson.optString("title"));
            movie.setPopularity(movieJson.optDouble("popularity"));
            movie.setPosterPath(movieJson.optString("poster_path"));
            movie.setOriginalLanguage(movieJson.optString("original_language"));
            movie.setOriginalTitle(movieJson.optString("original_title"));

            JSONArray genreIds = movieJson.optJSONArray("genre_ids");
            ArrayList<Integer> genreIdsList = new ArrayList<Integer>();

            for(int j = 0; j < genreIds.length(); j++){
                genreIdsList.add(genreIds.optInt(j));
            }

            movie.setGenreIds(genreIdsList);
            movie.setBackdropPath(movieJson.optString("backdrop_path"));
            movie.setAdult(movieJson.optBoolean("adult"));
            movie.setOverview(movieJson.optString("overview"));
            movie.setReleaseDate(movieJson.optString("release_date"));

            listaFilmes.add(movie);
        }

        return listaFilmes;
    }
}
