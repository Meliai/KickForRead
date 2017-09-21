package com.rudainc.kickforread.mvp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.mvp.presenters.BasePresenter;
import com.rudainc.kickforread.mvp.views.BaseView;

import butterknife.ButterKnife;

public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private View mCustomSnackBarView;

    @Override
    public void showProgress(ViewGroup view) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }


    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    private Snackbar initSnackBar(String message, boolean isError) {
        View snackBarParent = null;

        if (mCustomSnackBarView != null) {
            snackBarParent = mCustomSnackBarView;
        } else if (findViewById(android.R.id.content) != null) {
            snackBarParent = findViewById(android.R.id.content);
        }
        if (snackBarParent != null) {
            Snackbar snackbar = Snackbar.make(snackBarParent, message, Snackbar.LENGTH_SHORT).setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            snackbar.getView().setBackgroundColor(isError?ContextCompat.getColor(this,R.color.colorPrimary):ContextCompat.getColor(this,R.color.colorAccent));
            return snackbar;
        }
        return null;
    }


    public void showSnackBar(String message, boolean isError) {
        Snackbar snackbar = initSnackBar(message,isError);
        if (snackbar != null)
            snackbar.show();

    }
}
