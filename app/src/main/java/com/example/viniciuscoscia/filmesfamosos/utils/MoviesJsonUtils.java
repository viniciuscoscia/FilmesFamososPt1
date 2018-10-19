package com.example.viniciuscoscia.filmesfamosos.utils;

import android.util.Log;

import com.example.viniciuscoscia.filmesfamosos.entity.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesJsonUtils {
    public static ArrayList<Movie> jsonToMovieList(String jsonString) throws JSONException {
        ArrayList<Movie> listaFilmes = new ArrayList<Movie>();

        JSONArray moviesJson = new JSONObject(jsonString).getJSONArray("results");

        Log.v("RESULTADO", moviesJson.toString());

        for(int i = 0; i < moviesJson.length(); i++){
            Movie movie = new Movie();

            JSONObject movieJson =  moviesJson.getJSONObject(i);

            movie.setVoteCount(movieJson.getInt("vote_count"));
            movie.setId(movieJson.getLong("id"));
            movie.setVideo(movieJson.getBoolean("video"));
            movie.setVoteAverage(movieJson.getDouble("vote_average"));
            movie.setTitle(movieJson.getString("title"));
            movie.setPopularity(movieJson.getDouble("popularity"));
            movie.setPosterPath(movieJson.getString("poster_path"));
            movie.setOriginalLanguage(movieJson.getString("original_language"));
            movie.setOriginalTitle(movieJson.getString("original_title"));

            JSONArray genreIds = movieJson.getJSONArray("genre_ids");
            ArrayList<Integer> genreIdsList = new ArrayList<Integer>();

            for(int j = 0; j < genreIds.length(); j++){
                genreIdsList.add(genreIds.getInt(j));
            }

            movie.setGenreIds(genreIdsList);
            movie.setBackdropPath(movieJson.getString("backdrop_path"));
            movie.setAdult(movieJson.getBoolean("adult"));
            movie.setOverview(movieJson.getString("overview"));
            movie.setReleaseDate(movieJson.getString("release_date"));

            listaFilmes.add(movie);
        }

        return listaFilmes;
    }
}
