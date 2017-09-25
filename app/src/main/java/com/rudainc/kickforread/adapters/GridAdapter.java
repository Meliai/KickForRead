package com.rudainc.kickforread.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.custom_views.CircleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private final Context context;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private ArrayList<Long> allEvents = new ArrayList<>();
    private OnClickHandler mClickHandler;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, ArrayList<Long> allEvents, OnClickHandler onClickHandler) {
        super(context, R.layout.single_cell_layout);
        this.context = context;
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;

        this.mClickHandler = onClickHandler;
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
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.single_cell_layout, parent, false);
        }
        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorWhite));
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorGrey));
        }
        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        final CircleView mIndicator = (CircleView)view.findViewById(R.id.read_indicator);
        cellNumber.setText(String.valueOf(dayValue));
        //Add events to the calendar
//        TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);
        Calendar eventCalendar = Calendar.getInstance();
//        if (!allEvents.isEmpty())
//        for(int i = 0; i < allEvents.size(); i++){
//            eventCalendar.setTime(new Date(allEvents.get(i) * 1000));
//            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
//                    && displayYear == eventCalendar.get(Calendar.YEAR)){
//                mIndicator.setVisibility(View.VISIBLE);
//             }
//        }

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

    public void updateMoviesList(ArrayList<Long> list) {
        allEvents.clear();
        this.allEvents.addAll(list);
        notifyDataSetChanged();
    }

}