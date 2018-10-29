package com.mhaseeb.property.ui.common.config;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Mohammad.Haseeb on 12/15/2016.
 */

public class AppModel {

    private static AppModel instance;
    private ProgressDialog progressDialog;

    public static AppModel getInstance() {
        if (instance == null) {
            synchronized (AppModel.class) {

                if (instance == null)
                    instance = new AppModel();
            }
        }
        return instance;

    }

    public void showLoader(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public void showLoader(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public void hideLoader() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

    }



    public String getCurrentDateTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return (df.format(Calendar.getInstance().getTime()));
    }



}
