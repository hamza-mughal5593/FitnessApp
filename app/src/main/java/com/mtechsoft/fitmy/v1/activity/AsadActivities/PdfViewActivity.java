package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.itextpdf.text.pdf.PdfReader;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.AppExt;
import com.mtechsoft.fitmy.v1.FileDownloader;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;

import java.io.File;
import java.io.IOException;

public class PdfViewActivity extends AppCompatActivity implements OnPageChangeListener {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PDFView pdfView;
    private PdfReader reader;
    private File pdfFile;
    private ProgressDialog progressDialog;

    int rendom = 0;
    String link_pdf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        link_pdf = getIntent().getStringExtra("form_link");


        pdfView = findViewById(R.id.pdf4);



        verifyStoragePermissions(PdfViewActivity.this);
//
//        isReadStoragePermissionGranted();
//        isWriteStoragePermissionGranted();

        loadpdf(link_pdf);

    }



    public void loadpdf(final String linkk) {


        SharedPreferences mPrefs = getSharedPreferences("NotificationBadgeCount", Context.MODE_PRIVATE);
        int count = mPrefs.getInt("count", 0);
        count = count + 1;

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt("count", count);
        editor.apply();


        pdfFile = new File(Environment.getExternalStorageDirectory() + "/fitapp/" + "bookingform"+count +".pdf");
//                    Uri path = Uri.fromFile(pdfFile);
        if (!pdfFile.exists()) {

            new DownloadFile().execute(linkk, "bookingform"+count + ".pdf");

        } else {

            AppExt.showPDFFile(pdfView,this, pdfFile);

        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }


    private class DownloadFile extends AsyncTask<String, Void, Void> {
        OnPageChangeListener listener;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new ProgressDialog(PdfViewActivity.this);

            progressDialog.setMessage("Downloading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "fitapp");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            AppExt.showPDFFile(pdfView, listener,  pdfFile);
        }
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );


        }else {


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(PdfViewActivity.this, BookingActivity.class));

    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Asad","Permission is granted1");
                return true;
            } else {

                Log.v("Asad","Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Asad","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Asad","Permission is granted2");
                return true;
            } else {

                Log.v("Asad","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Asad","Permission is granted2");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d("Asad", "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v("Asad","Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
//                    downloadPdfFile();

                    loadpdf(link_pdf);
                }else{

                }
                break;

            case 3:
                Log.d("Asad", "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v("Asad","Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                }else{
                }
                break;
        }
    }

}
