package com.rudainc.kickforread.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rudainc.kickforread.R;
import com.rudainc.kickforread.utils.KickForReadKeys;
import com.rudainc.kickforread.utils.KickForReadPreferences;
import com.rudainc.kickforread.widget.WidgetService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBookActivity extends BaseActivity implements Validator.ValidationListener {

    @NotEmpty
    @Length(min = 1, max = 160)
    @BindView(R.id.et_title)
    EditText etTitle;

    @NotEmpty
    @Length(min = 1, max = 160)
    @BindView(R.id.et_author)
    EditText etAuthor;

    @NotEmpty
    @Length(min = 1, max = 160)
    @BindView(R.id.et_category)
    EditText etCategory;

    @BindView(R.id.start_day)
    TextView mStartDay;

    private long dateinMillis;
    private String date;
    ImageButton currPaint;
    @BindView(R.id.paint_colors)
    LinearLayout paintLayout;
    private String label;
    private Validator mValidator;

//    @OnClick(R.id.start_day)
//    void start_date(){
//        dateinMillis = System.currentTimeMillis();
////        showDatePicker();
//    }

    @OnClick(R.id.btnAddBook)
    void btnAddBook() {
        mValidator.validate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mStartDay.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(System.currentTimeMillis()));
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        label = currPaint.getTag().toString();
    }


    private void showDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this, R.style.Theme_AppCompat_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.YEAR, year);
                dateinMillis = cal.getTimeInMillis() / 1000;
                date = DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTimeInMillis());
                mStartDay.setText(date);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    public void paintClicked(View view) {
        if (view != currPaint) {
            ImageView imgView = (ImageView) view;
            label = view.getTag().toString();
            Log.i("COLOR", label);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
        }
    }

    @Override
    public void onValidationSucceeded() {
        BaseActivity.addBook(etTitle.getText().toString().trim(), etAuthor.getText().toString().trim(), etCategory.getText().toString().trim(),
                label, String.valueOf(System.currentTimeMillis()));
        KickForReadPreferences.setBookAdded(this,true);
        KickForReadPreferences.setBookData(this,etTitle.getText().toString().trim(),etAuthor.getText().toString().trim(), etCategory.getText().toString().trim(),String.valueOf(System.currentTimeMillis()));
        WidgetService.startActionUpdateRecipeWidget(this);
        onBackPressed();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
