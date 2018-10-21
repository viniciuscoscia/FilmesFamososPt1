package com.example.viniciuscoscia.filmesfamosos.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.viniciuscoscia.filmesfamosos.entity.Movie;

import java.net.URL;
import java.util.ArrayList;

public class MovieService extends AsyncTask<String, Void, ArrayList<Movie>> {

    private AsyncTaskDelegate delegate = null;

    public MovieService(Context context, AsyncTaskDelegate responder){
        this.delegate = responder;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {

        if(strings.length == 0){
            return null;
        }

        URL urlResponse = NetworkUtils.buildUrl(strings[0]);

        try{
            String resposta = NetworkUtils.getResponseFromURL(urlResponse);
            ArrayList<Movie> listaFilmes = MoviesJsonUtils.jsonToMovieList(resposta);

            return listaFilmes;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        if (movies != null)
            delegate.processFinish(movies);
    }
}
