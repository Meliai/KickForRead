package com.rudainc.kickforread.mvp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.mvp.presenters.BasePresenter;
import com.rudainc.kickforread.mvp.views.BaseView;
import com.rudainc.kickforread.utils.KickForReadKeys;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView, KickForReadKeys {

    protected P mPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showProgress(ViewGroup view) {

    }


    @Override
    public void hideProgress() {
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mPresenter.saveInstance(outState);
    }

    public Snackbar showSnackBar(String message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setActionTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

        snackbar.show();
        return snackbar;
    }



}
