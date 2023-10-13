package com.lifegamesapps.expirationdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View backgroundView = findViewById(R.id.backgroundView);

        ImageView imgViewSplash = findViewById(R.id.imgViewSplash);
        ImageView imgViewGateLogo = findViewById(R.id.imgViewGateLogo);


        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            // Modo Escuro (Tema Noturno) está ativado
            // Defina a imagem apropriada para o tema escuro no ImageView
            imgViewSplash.setImageResource(R.drawable.gatecalendarwhite);
            imgViewGateLogo.setImageResource(R.drawable.gatelogowhite);

        } else {
            // Modo Claro (Tema Dia) está ativado
            // Defina a imagem apropriada para o tema claro no ImageView
            imgViewSplash.setImageResource(R.drawable.gatecalendar);
            imgViewGateLogo.setImageResource(R.drawable.gatelogo);
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        },2000);
    }
}