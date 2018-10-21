package com.example.viniciuscoscia.filmesfamosos.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viniciuscoscia.filmesfamosos.R;
import com.example.viniciuscoscia.filmesfamosos.adapter.MovieAdapter;
import com.example.viniciuscoscia.filmesfamosos.entity.Movie;
import com.example.viniciuscoscia.filmesfamosos.utils.AsyncTaskDelegate;
import com.example.viniciuscoscia.filmesfamosos.utils.MovieService;
import com.example.viniciuscoscia.filmesfamosos.utils.MoviesJsonUtils;
import com.example.viniciuscoscia.filmesfamosos.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.FilmeOnClickHandler, AsyncTaskDelegate {

    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    private final String MOVIE = "movie";

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private TextView tv_tipo_ordem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_tipo_ordem = findViewById(R.id.tv_tipo_ordem);

        configurarRecyclerView();

        executar(POPULAR);
    }

    private void executar(final String tipoPesquisa) {
        if(!NetworkUtils.hasInternetConnection(this)){
            View view = this.findViewById(R.id.main_activity);

            Snackbar snackbar = Snackbar.make(view, getString(R.string.sem_conexao_internet), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executar(tipoPesquisa);
                }
            });
            snackbar.show();

            return;
        }

        alterarTextoTipoOrdem(tipoPesquisa);
        new MovieService(this, this).execute(tipoPesquisa);
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
        if(ordem.equals(POPULAR)){
            text = getString(R.string.filmes_populares);
        } else if (ordem.equals(TOP_RATED)){
            text = getString(R.string.filmes_melhores_notas);
        }

        tv_tipo_ordem.setText(text);
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
            tipoOrdem = TOP_RATED;
        } else if(id == R.id.menu_popularidade){
            tipoOrdem = POPULAR;
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
        intent.putExtra(Movie.PARCELABLE_KEY, selectedMovie);
        startActivity(intent);
    }

    @Override
    public void processFinish(Object output) {
        if(output != null) {
            ArrayList<Movie> movies = (ArrayList<Movie>) output;

            movieAdapter.setMovieList(movies);
        }
    }


}


