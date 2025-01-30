package com.todimssayuda.todosobrimss;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Calendar calendar;
    private Map<Integer, List<Integer>> specialDatesByMonthType1;
    private Map<Integer, List<Integer>> specialDatesByMonthType2;
    private List<Integer> days;
    private final int today;
    private final int currentMonth;
    private int selectedMonth;

    public CalendarAdapter(Calendar calendar, Map<Integer, List<Integer>> specialDatesByMonthType1, Map<Integer, List<Integer>> specialDatesByMonthType2, int currentDay, int currentMonth, int selectedMonth) {
        this.calendar = (Calendar) calendar.clone();
        this.specialDatesByMonthType1 = specialDatesByMonthType1;
        this.specialDatesByMonthType2 = specialDatesByMonthType2;
        this.today = currentDay;
        this.currentMonth = currentMonth;
        this.selectedMonth = selectedMonth;
        generateDays();
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        int day = days.get(position);

        if (day == 0) {
            holder.tvDay.setText("");
            holder.tvDay.setBackgroundResource(android.R.color.transparent);
        } else {
            holder.tvDay.setText(String.valueOf(day));

            List<Integer> specialDatesType1 = specialDatesByMonthType1.get(selectedMonth);
            List<Integer> specialDatesType2 = specialDatesByMonthType2.get(selectedMonth);

            boolean isSpecialType1 = specialDatesType1 != null && specialDatesType1.contains(day);
            boolean isSpecialType2 = specialDatesType2 != null && specialDatesType2.contains(day);
            boolean isToday = (day == today && currentMonth == selectedMonth);

            if (isToday && isSpecialType1) {
                holder.tvDay.setBackgroundResource(R.drawable.diacajayhoyback);
                holder.tvDay.setTextColor(Color.WHITE);
            } else if (isToday && isSpecialType2) {
                holder.tvDay.setBackgroundResource(R.drawable.diafestivoyhoyback);
                holder.tvDay.setTextColor(Color.WHITE);
            } else if (isToday) {
                holder.tvDay.setBackgroundResource(R.drawable.diahoyback);
                holder.tvDay.setTextColor(Color.BLACK);
            } else if (isSpecialType1) {
                holder.tvDay.setBackgroundResource(R.drawable.diacajaback);
                holder.tvDay.setTextColor(Color.WHITE);
            } else if (isSpecialType2) {
                holder.tvDay.setBackgroundResource(R.drawable.diafestivoback);
                holder.tvDay.setTextColor(Color.WHITE);
            } else {
                holder.tvDay.setBackgroundResource(R.drawable.dianormalback);
                holder.tvDay.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void updateCalendar(Calendar newCalendar, int newSelectedMonth) {
        this.calendar = (Calendar) newCalendar.clone();
        this.selectedMonth = newSelectedMonth;
        generateDays();
        notifyDataSetChanged();
    }

    private void generateDays() {
        days = new ArrayList<>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < firstDayOfWeek; i++) {
            days.add(0);
        }

        for (int i = 1; i <= daysInMonth; i++) {
            days.add(i);
        }
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
        }
    }
}
