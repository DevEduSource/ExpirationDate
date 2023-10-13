package com.lifegamesapps.expirationdate.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.lifegamesapps.expirationdate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private EditText editTextBaseDate;
    private EditText editTextDaysToAdd;
    private TextView txtExpireDate;
    private Button button;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView imgViewCalendarLogo = contentView.findViewById(R.id.imgViewCalendarLogo);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            // Modo Escuro (Tema Noturno) está ativado
            // Defina a imagem apropriada para o tema escuro no ImageView
            imgViewCalendarLogo.setImageResource(R.drawable.gatecalendarwhite);

        } else {
            // Modo Claro (Tema Dia) está ativado
            // Defina a imagem apropriada para o tema claro no ImageView
            imgViewCalendarLogo.setImageResource(R.drawable.gatecalendar);
        }

        editTextBaseDate = contentView.findViewById(R.id.editTextBaseDate);
        editTextDaysToAdd = contentView.findViewById(R.id.editTextDaysToAdd);
        txtExpireDate = contentView.findViewById(R.id.txtExpireDate);
        button = contentView.findViewById(R.id.button);

        editTextBaseDate.setOnClickListener(v -> {

            showDatePopup(v, contentView);
        });

        button.setOnClickListener(v -> {
            String baseDateString = editTextBaseDate.getText().toString();
            String daysToAddString = editTextDaysToAdd.getText().toString();

            if (baseDateString.isEmpty() || daysToAddString.isEmpty()) {
                Toast.makeText(contentView.getContext(), "You must fill in the fields to calculate the date!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse das datas
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            Date baseDate;
            try {
                baseDate = sdf.parse(baseDateString);
            } catch (Exception e) {
                Toast.makeText(contentView.getContext(), "Date Invalid!", Toast.LENGTH_SHORT).show();
                return;
            }

            int daysToAdd = Integer.parseInt(daysToAddString);
            // Adiciona os dias à data base
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(baseDate);
            calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

            // Exibe a data resultante
            txtExpireDate.setText(sdf.format(calendar.getTime()));
        });

        return contentView;
    }

    private void showDatePopup(View anchorView, View contentView) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_full, null);

        Button btnOpenCalendar = popupView.findViewById(R.id.btnOpenCalendar);
        Button btnManualDate = popupView.findViewById(R.id.btnManualDate);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background)); // Define o fundo com alpha

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        btnOpenCalendar.setOnClickListener(v -> {
            final Calendar currentDate = Calendar.getInstance();
            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH);
            int day = currentDate.get(Calendar.DAY_OF_MONTH);

            // Cria o DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(contentView.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                    // Atualiza o EditText com a data selecionada
                    currentDate.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                    editTextBaseDate.setText(sdf.format(currentDate.getTime()));
                }
            }, year, month, day);

            datePickerDialog.show();
            popupWindow.dismiss();
        });

        btnManualDate.setOnClickListener(v -> {
            showManualDateInput();
            popupWindow.dismiss();
        });
    }

    private void showManualDateInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final EditText editTextManualDate = new EditText(requireContext());

        // Defina o tipo de entrada do EditText para texto
        editTextManualDate.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Adicione um TextWatcher para formatar a entrada como data enquanto é digitada
        editTextManualDate.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private String oldText = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Não é necessário implementar
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Não é necessário implementar
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String input = editable.toString();
                String date = input.replaceAll("[^0-9]", ""); // Remove todos os caracteres não numéricos

                if (!date.equals(input)) {
                    isUpdating = true;
                    editTextManualDate.setText(date);
                    editTextManualDate.setSelection(date.length());
                } else if (date.length() == 6) {
                    // Formate a data como "dd/MM/yyyy" enquanto o usuário digita
                    isUpdating = true;
                    String formattedDate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4, 6);
                    editTextManualDate.setText(formattedDate);
                    editTextManualDate.setSelection(formattedDate.length());
                }

                if (date.length() == 6) {
                    // Se a data tiver 8 caracteres, atualize o texto
                    isUpdating = true;
                    editTextManualDate.setText(formatDate(date));
                    editTextManualDate.setSelection(editTextManualDate.getText().length());
                }
            }

            private String formatDate(String date) {
                // Formate a data como "dd/MM/yyyy"
                return date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4, 6);
            }
        });

        builder.setView(editTextManualDate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String manualDate = editTextManualDate.getText().toString();
                        if (isValidDate(manualDate)) {
                            editTextBaseDate.setText(manualDate);
                        } else {
                            editTextManualDate.setError("Data inválida (dd/MM/yy)");
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lidar com o cancelamento, se necessário
                    }
                });

        builder.create().show();
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            // Tenta fazer o parsing da data
            Date parsedDate = sdf.parse(date);
            if (parsedDate != null) {
                // Verifica se a data parseada é igual à data inserida
                return sdf.format(parsedDate).equals(date);
            }
            return false;
        } catch (ParseException e) {
            return false; // A exceção de análise indica uma data inválida
        }
    }
}