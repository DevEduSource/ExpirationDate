package com.lifegamesapps.expirationdate.ui.dashboard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lifegamesapps.expirationdate.R;
import com.lifegamesapps.expirationdate.databinding.FragmentDashboardBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ImageView themeIcon = contentView.findViewById(R.id.themeIcon);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            // Modo Escuro (Tema Noturno) está ativado
            // Defina a imagem apropriada para o tema escuro no ImageView
            themeIcon.setImageResource(R.drawable.moon);

        } else {
            // Modo Claro (Tema Dia) está ativado
            // Defina a imagem apropriada para o tema claro no ImageView
            themeIcon.setImageResource(R.drawable.sun);
        }


        Switch switchDayNight = contentView.findViewById(R.id.switchDayNight);
        // Configura o estado inicial do switch com base no modo noturno atual
        switchDayNight.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        switchDayNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Alterar para o modo noturno (tema escuro)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Alterar para o modo dia (tema claro)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        ImageView btnWhatsapp = contentView.findViewById(R.id.btnWhatsapp);

        btnWhatsapp.setOnClickListener(v -> {
            String phoneNumber = "5511952248157"; // Substitua pelo seu número

            // Crie um URI no formato correto para abrir o WhatsApp com o número desejado
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber);

            // Crie um Intent e defina a ação para VIEW e o data URI para o URI criado
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            // Inicie o WhatsApp
            startActivity(intent);
        });

        return contentView;
    }


}



