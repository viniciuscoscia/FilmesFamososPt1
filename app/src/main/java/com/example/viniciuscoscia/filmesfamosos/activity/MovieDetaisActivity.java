package com.example.viniciuscoscia.filmesfamosos.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viniciuscoscia.filmesfamosos.R;
import com.example.viniciuscoscia.filmesfamosos.entity.Movie;
import com.example.viniciuscoscia.filmesfamosos.utils.Utils;
import com.squareup.picasso.Picasso;

public class MovieDetaisActivity extends AppCompatActivity {

    private final String MOVIE = "movie";
    private final String IMAGE_PATH = "http://image.tmdb.org/t/p/w500//";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detais);

        TextView tvTitulo = findViewById(R.id.tvTitulo);
        ImageView tvImagemResumo = findViewById(R.id.tvImagemResumo);
        TextView tvNota = findViewById(R.id.tvNota);
        TextView tvDataLancamento = findViewById(R.id.tvDataLancamento);
        TextView tvResumo = findViewById(R.id.tvResumo);

        Intent intent = getIntent();
        Movie movie = (Movie)intent.getSerializableExtra(MOVIE);

        tvTitulo.setText(movie.getTitle());
        tvTitulo.setPaintFlags(tvTitulo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Picasso.with(this).load(IMAGE_PATH + movie.getPosterPath()).into(tvImagemResumo);
        tvNota.setText(getString(R.string.nota_media)+ movie.getVoteAverage());

        tvDataLancamento.setText(Utils.formatarData(movie.getReleaseDate()));
        tvResumo.setText(movie.getOverview());
    }


}
