package com.example.viniciuscoscia.filmesfamosos.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viniciuscoscia.filmesfamosos.R;
import com.example.viniciuscoscia.filmesfamosos.entity.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetaisActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private ImageView tvImagemResumo;
    private TextView tvNota;
    private TextView tvDataLancamento;
    private TextView tvResumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detais);

        tvTitulo = findViewById(R.id.tvTitulo);
        tvImagemResumo = findViewById(R.id.tvImagemResumo);
        tvNota = findViewById(R.id.tvNota);
        tvDataLancamento = findViewById(R.id.tvDataLancamento);
        tvResumo = findViewById(R.id.tvResumo);

        Intent intent = getIntent();
        Movie movie = (Movie)intent.getSerializableExtra("movie");

        tvTitulo.setText(movie.getTitle());
        tvTitulo.setPaintFlags(tvTitulo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w500//" + movie.getPosterPath()).into(tvImagemResumo);
        tvNota.setText("Nota média: " + movie.getVoteAverage());

        String data = movie.getReleaseDate();

        tvDataLancamento.setText(formatarData(movie.getReleaseDate()));
        tvResumo.setText(movie.getOverview());
    }

    private String formatarData(String data){
        return data.split("-")[2] + "/" + data.split("-")[1]  + "/" + data.split("-")[0];
    }
}
