package com.rudainc.kickforread.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rudainc.kickforread.R;
import com.rudainc.kickforread.adapters.GridAdapter;
import com.rudainc.kickforread.database.DaysContract;
import com.rudainc.kickforread.ui.activities.BaseActivity;
import com.rudainc.kickforread.utils.KickForReadKeys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, GridAdapter.OnClickHandler, KickForReadKeys {
    private static final String TAG = CalendarFragment.class.getSimpleName();

    @BindView(R.id.previous_month)
    ImageView previousButton;
    @BindView(R.id.next_month)
    ImageView nextButton;
    @BindView(R.id.display_current_date)
    TextView currentDate;
    @BindView(R.id.calendar_grid)
    GridView calendarGridView;
    @BindView(R.id.my_ads_banner)
    AdView mAdView;

    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private ArrayList<Long> mEvents = new ArrayList<>();
    private Cursor mCursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);

        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();

        return v;
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }


    private void setUpCalendarAdapter() {
        List<Date> dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        if (getActivity() != null) {
            mAdapter = new GridAdapter(getActivity(), dayValueInCells, cal, mEvents, this);
            calendarGridView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (!BaseActivity.checkIsDataAlreadyInDBorNot(calendar.getTimeInMillis())) {
            BaseActivity.addDate(calendar.getTimeInMillis());
//            mCursor.setNotificationUri(getContext().getContentResolver(), DaysContract.DayEntry.CONTENT_URI);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {


        switch (loaderId) {

            case ID_LOADER:

                Uri daysQueryUri = DaysContract.DayEntry.CONTENT_URI;

                return new CursorLoader(getActivity(),
                        daysQueryUri,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        if (!BaseActivity.getAllCheckedDays(data).isEmpty()) {
            mEvents = BaseActivity.getAllCheckedDays(data);
            setUpCalendarAdapter();
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
    }



    private void loadAds() {

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
