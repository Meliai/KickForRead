package com.rudainc.kickforread.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.custom_views.CircleView;
import com.rudainc.kickforread.utils.HelpUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private final Context context;
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private ArrayList<Long> allEvents = new ArrayList<>();
    private OnClickHandler mClickHandler;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, ArrayList<Long> allEvents, OnClickHandler onClickHandler) {
        super(context, R.layout.single_day_layout);
        this.context = context;
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        this.mClickHandler = onClickHandler;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.single_day_layout, parent, false);
        }
        if (displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey));
        }
        //Add day to calendar
        TextView cellDay =view.findViewById(R.id.calendar_date_id);
        final CircleView mIndicator = view.findViewById(R.id.read_indicator);
        cellDay.setText(String.valueOf(dayValue));

//        Calendar eventCalendar = Calendar.getInstance();

        if (!allEvents.isEmpty())
            for (int i = 0; i < allEvents.size(); i++) {
//                eventCalendar.setTime(HelpUtil.covertLongToDate(allEvents.get(i)));
                if (dayValue == (HelpUtil.setCalendarDate(allEvents.get(i))).get(Calendar.DAY_OF_MONTH) && displayMonth == ((HelpUtil.setCalendarDate(allEvents.get(i))).get(Calendar.MONTH) + 1)
                        && displayYear == (HelpUtil.setCalendarDate(allEvents.get(i))).get(Calendar.YEAR)) {
                    mIndicator.setVisibility(View.VISIBLE);
                }
            }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIndicator.setVisibility(View.VISIBLE);

                Date date = monthlyDates.get(position);
                mClickHandler.onClick(date);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    public interface OnClickHandler {
        void onClick(Date date);
    }
}