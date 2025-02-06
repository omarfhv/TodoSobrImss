package com.todimssayuda.todosobrimss;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarioCaja extends AppCompatActivity {

    private TextView tvMonthYear;
    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;

    private Calendar calendar;
    private List<Integer> specialDates;
    private int today;
    private int currentMonth;
    private int selectedMonth;
    private Map<Integer, List<Integer>> diascaja;
    private Map<Integer, List<Integer>> diasfestivos;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendariocaja);
        setTitle("Prestamos Excepcionales");


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        iniciardiascaja();
        iniciardiasfestivos();
        tvMonthYear = findViewById(R.id.tv_month_year);
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        Button btnPrevMonth = findViewById(R.id.btn_prev_month);
        Button btnNextMonth = findViewById(R.id.btn_next_month);

        // Inicializamos el calendario
        calendar = Calendar.getInstance();
        today = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        selectedMonth = currentMonth;


        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            btnPrevMonth.setEnabled(false);
        }
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            btnNextMonth.setEnabled(false);
        }


        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        // Configurar el adaptador con el mes actual
        calendarAdapter = new CalendarAdapter(calendar, diascaja, diasfestivos, today, currentMonth, selectedMonth);
        calendarRecyclerView.setAdapter(calendarAdapter);

        updateMonthYearText();

        btnPrevMonth.setOnClickListener(v -> {
            if (!btnNextMonth.isEnabled()) {
                btnNextMonth.setEnabled(true);
            }
            if (calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                btnPrevMonth.setEnabled(false);
                calendar.add(Calendar.MONTH, -1);
                updateCalendar();
            } else {
                calendar.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        btnNextMonth.setOnClickListener(v -> {

            if (!btnPrevMonth.isEnabled()) {
                btnPrevMonth.setEnabled(true);
            }
            if (calendar.get(Calendar.MONTH) == Calendar.NOVEMBER) {
                btnNextMonth.setEnabled(false);
                calendar.add(Calendar.MONTH, 1);
                updateCalendar();
            } else {
                calendar.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

    }

    private void updateCalendar() {
        updateMonthYearText();
        Calendar updatedCalendar = (Calendar) calendar.clone();
        selectedMonth = updatedCalendar.get(Calendar.MONTH);

        // Actualizamos el adaptador con el mes correcto
        calendarAdapter.updateCalendar(updatedCalendar, selectedMonth);
    }

    private void updateMonthYearText() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime()).toUpperCase(Locale.getDefault());
        tvMonthYear.setText(formattedDate);
    }

    private void iniciardiasfestivos() {
        diasfestivos = new HashMap<>();

        // Definir las fechas festivas para cada mes
        diasfestivos.put(Calendar.JANUARY, List.of(1));
        diasfestivos.put(Calendar.FEBRUARY, List.of(3));
        diasfestivos.put(Calendar.MARCH, List.of(17));
        diasfestivos.put(Calendar.APRIL, List.of(17, 18, 19));
        diasfestivos.put(Calendar.MAY, List.of(1, 10));
        diasfestivos.put(Calendar.SEPTEMBER, List.of(15, 16));
        diasfestivos.put(Calendar.NOVEMBER, List.of(17));
        diasfestivos.put(Calendar.DECEMBER, List.of(25));
    }

    private void iniciardiascaja() {
        diascaja = new HashMap<>();

        // Definir las fechas de la caja para cada mes
        diascaja.put(Calendar.JANUARY, List.of(6, 7, 8, 9, 20, 21, 22, 23));
        diascaja.put(Calendar.FEBRUARY, List.of(6, 7, 10, 19, 20, 21));
        diascaja.put(Calendar.MARCH, List.of(6, 7, 10, 11, 20, 21, 24, 25));
        diascaja.put(Calendar.APRIL, List.of(3, 4, 7, 8, 21, 22, 23));
        diascaja.put(Calendar.MAY, List.of(6, 7, 8, 9, 20, 21, 22, 23));
        diascaja.put(Calendar.JUNE, List.of(4, 5, 6, 9, 18, 19, 20, 23));
        diascaja.put(Calendar.JULY, List.of(3, 4, 7, 8, 21, 22, 23, 24));
        diascaja.put(Calendar.AUGUST, List.of(5, 6, 7, 8, 20, 21, 22, 25));
        diascaja.put(Calendar.SEPTEMBER, List.of(3, 4, 5, 8, 19, 22, 23, 24));
        diascaja.put(Calendar.OCTOBER, List.of(6, 7, 8, 9, 10, 20, 21, 22, 23, 24));
        diascaja.put(Calendar.NOVEMBER, List.of(5, 6, 7, 10, 19, 20, 21));
        diascaja.put(Calendar.DECEMBER, List.of(3, 4, 5, 8));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
