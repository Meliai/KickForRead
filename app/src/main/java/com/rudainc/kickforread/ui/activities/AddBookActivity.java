package com.rudainc.kickforread.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.models.BooksModel;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBookActivity extends BaseActivity {

    @BindView(R.id.et_title)
    EditText etTitle;

    @BindView(R.id.et_author)
    EditText etAuthor;

    @BindView(R.id.et_category)
    EditText etCategory;

    @BindView(R.id.start_day)
    TextView mStartDay;

    private long dateinMillis;
    private String date;

    @OnClick(R.id.start_day)
    void start_date(){
        showDatePicker();
    }

    @OnClick(R.id.btnAddBook)
    void btnAddBook(){
       BaseActivity.addBook(new BooksModel(etTitle.getText().toString().trim(),etAuthor.getText().toString().trim(), etCategory.getText().toString().trim(),
              "1" ,String.valueOf(dateinMillis), "false"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
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
    }
}
