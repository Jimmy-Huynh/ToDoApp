package com.tvnsoftware.simpleapp.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thieusike on 6/3/2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private EditText mEditText;
    private String mFormatDisplay = "MMM dd yyyy";
    private String mFormatConvert = "MM/dd/yyyy";

    public DatePickerFragment(EditText editText) {
        this.mEditText = editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int day;
        final Calendar c = Calendar.getInstance();
        if (!mEditText.getText().toString().isEmpty()) {
            Date date = getDateFromString(mEditText.getText().toString(), mFormatDisplay);
            c.setTime(date);
        }
        // Use the current date as the default date in the picker
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = getDateFromString(month + "/" + dayOfMonth + "/" + year, mFormatConvert);
        SimpleDateFormat sdfDisplay = new SimpleDateFormat(mFormatDisplay);
        mEditText.setText(sdfDisplay.format(date));
    }

    private Date getDateFromString(String dateString, String format) {
        SimpleDateFormat sdfConvert = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdfConvert.parse(dateString);
        } catch (ParseException ex) {
            final Calendar c = Calendar.getInstance();
            date = c.getTime();
        }
        return date;
    }
}
