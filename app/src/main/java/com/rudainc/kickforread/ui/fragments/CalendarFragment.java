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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.adapters.GridAdapter;
import com.rudainc.kickforread.database.DatabaseQuery;
import com.rudainc.kickforread.database.FavoritesContract;
import com.rudainc.kickforread.ui.activities.BaseActivity;
import com.rudainc.kickforread.utils.KickForReadKeys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;


public class CalendarFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> , GridAdapter.OnClickHandler, KickForReadKeys {
    private static final String TAG = CalendarFragment.class.getSimpleName();

    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private DatabaseQuery mQuery;
    private ArrayList<Long> mEvents;
    private Cursor mCursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);
        getActivity().getSupportLoaderManager().initLoader(ID_LOADER, null, this);
        previousButton = (ImageView) v.findViewById(R.id.previous_month);
        nextButton = (ImageView) v.findViewById(R.id.next_month);
        currentDate = (TextView) v.findViewById(R.id.display_current_date);

        calendarGridView = (GridView) v.findViewById(R.id.calendar_grid);

        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
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

    private void setGridCellClickEvents() {
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Clicked " + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpCalendarAdapter() {
        List<Date> dayValueInCells = new ArrayList<Date>();
//         mQuery = new DatabaseQuery(context);
        //  List<EventObjects> mEvents = mQuery.getAllFutureEvents();


        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
//        currentDate.setText(sDate);
        if (getActivity()!=null) {
            mAdapter = new GridAdapter(getActivity(), dayValueInCells, cal, mEvents, this);
            calendarGridView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(Date date) {
        if (!BaseActivity.checkIsDataAlreadyInDBorNot(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            BaseActivity.addDate(calendar.getTimeInMillis());
            Log.i("DATE", date + "");
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {


        switch (loaderId) {

            case ID_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri movieQueryUri = FavoritesContract.MovieEntry.CONTENT_URI;
                /* Sort order: Ascending by date */
//                String sortOrder = FavoritesContract.MovieEntry.COLUMN_MOVIE_ID + " ASC";
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */


                return new CursorLoader(getActivity(),
                        movieQueryUri,
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

        if (!BaseActivity.getAllCheckedDays(data).isEmpty()) {
            mEvents = BaseActivity.getAllCheckedDays(data);
//            mAdapter.updateMoviesList(mEvents);
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /*
         * Since this Loader's data is now invalid, we need to clear the Adapter that is
         * displaying the data.
         */
        mCursor = null;
    }

}
