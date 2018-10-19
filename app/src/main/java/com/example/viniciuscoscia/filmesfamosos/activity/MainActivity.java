package com.example.viniciuscoscia.filmesfamosos.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.viniciuscoscia.filmesfamosos.R;
import com.example.viniciuscoscia.filmesfamosos.adapter.MovieAdapter;
import com.example.viniciuscoscia.filmesfamosos.entity.Movie;
import com.example.viniciuscoscia.filmesfamosos.utils.MoviesJsonUtils;
import com.example.viniciuscoscia.filmesfamosos.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.FilmeOnClickHandler {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarRecyclerView();

        String tipoPesquisa = "popular";
        executar(tipoPesquisa);
    }

    private void executar(String tipoPesquisa) {
        alterarTextoTipoOrdem(tipoPesquisa);
        new MoviesAsyncTask().execute(tipoPesquisa);
    }

    private void configurarRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_filmes);
        movieAdapter = new MovieAdapter(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter( movieAdapter );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void alterarTextoTipoOrdem(String ordem){
        String text = "";
        if(ordem.equals("popular")){
            text = "Filmes populares";
        } else if (ordem.equals("top_rated")){
            text = "Filmes com melhores notas";
        }

        ((TextView) findViewById(R.id.tv_tipo_ordem)).setText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String tipoOrdem = "";

        if (id == R.id.menu_melhores_notas) {
            tipoOrdem = "top_rated";
        } else if(id == R.id.menu_popularidade){
            tipoOrdem = "popular";
        }

        if(!tipoOrdem.equals("")){
            movieAdapter.setMovieList(null);

            executar(tipoOrdem);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie selectedMovie) {
        Intent intent = new Intent(this, MovieDetaisActivity.class);
        intent.putExtra("movie", selectedMovie);
        startActivity(intent);
    }

    public class MoviesAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {

            if(strings.length == 0){
                return null;
            }

            findViewById(R.id.tv_tipo_ordem);

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
            if (movies != null)
                movieAdapter.setMovieList(movies);
        }
    }



}


