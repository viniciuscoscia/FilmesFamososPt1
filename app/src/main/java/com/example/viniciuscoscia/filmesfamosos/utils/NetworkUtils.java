package com.example.viniciuscoscia.filmesfamosos.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String URL_LINK = "api.themoviedb.org";
    private static final String API_KEY = ""; //TODO Put here your API key
    private static final String API_PARAM = "api_key";

    public static URL buildUrl(String queryOption){
        Uri.Builder uri = new Uri.Builder();
//        http://api.themoviedb.org/3/movie/popular?api_key=[KEY]
        uri.scheme("http")
                .authority(URL_LINK)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(queryOption)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        Log.d("TESTE!", "" + url);

        return url;
    }

    public static String getResponseFromURL(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            boolean hasInput = sc.hasNext();
            if (hasInput) {
                return sc.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
