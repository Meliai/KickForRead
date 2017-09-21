package com.rudainc.kickforread.mvp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.mvp.presenters.CalendarFragmentPresenter;
import com.rudainc.kickforread.mvp.views.CalendarFragmentView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarFragment extends BaseFragment<CalendarFragmentPresenter> implements CalendarFragmentView {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);
        return v;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
