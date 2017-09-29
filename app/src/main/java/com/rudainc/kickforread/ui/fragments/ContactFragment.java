package com.rudainc.kickforread.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.ui.activities.MainActivity;

import butterknife.ButterKnife;

public class ContactFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, v);
        ((MainActivity)getActivity()).setToolbarText(getString(R.string.title_contacts));

        return v;
    }
}
