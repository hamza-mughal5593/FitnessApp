package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

public class AsBookingActivity extends AppCompatActivity {


    Spinner vanue, facility ,state,city,rate_type,bill_unit;
    RelativeLayout data_rel,start_data_rel,end_date_rel,start_time_rel,end_time_rel,submit;
    DatePickerDialog picker;
    TimePickerDialog mTimePicker;

    EditText date_txt,start_date,end_date,start_time,end_time;
    String rate_typeee = "";


    LinearLayout rate_layout,time_lin,enddate_lin;
    ImageView imageView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_booking);

        initi();




        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                R.array.vanue, R.layout.spinner_item);
        adapters.setDropDownViewResource(R.layout.spinner_item);
        vanue.setAdapter(adapters);


        ArrayAdapter<CharSequence> adapters2 = ArrayAdapter.createFromResource(this,
                R.array.faciltiy, R.layout.spinner_item);
        adapters2.setDropDownViewResource(R.layout.spinner_item);
        facility.setAdapter(adapters2);


        ArrayAdapter<CharSequence> adapters3 = ArrayAdapter.createFromResource(this,
                R.array.stateee, R.layout.spinner_item);
        adapters3.setDropDownViewResource(R.layout.spinner_item);
        state.setAdapter(adapters3);


        ArrayAdapter<CharSequence> adapters4 = ArrayAdapter.createFromResource(this,
                R.array.city, R.layout.spinner_item);
        adapters4.setDropDownViewResource(R.layout.spinner_item);
        city.setAdapter(adapters4);


        ArrayAdapter<CharSequence> adapters5 = ArrayAdapter.createFromResource(this,
                R.array.rate_type, R.layout.spinner_item);
        adapters5.setDropDownViewResource(R.layout.spinner_item);
        rate_type.setAdapter(adapters5);


        ArrayAdapter<CharSequence> adapters6 = ArrayAdapter.createFromResource(this,
                R.array.s_abs_bil_unit_tempahan, R.layout.spinner_item);
        adapters6.setDropDownViewResource(R.layout.spinner_item);
        bill_unit.setAdapter(adapters6);

        rate_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                rate_typeee = (String) parent.getItemAtPosition(position);


                if (rate_typeee.equals("Per Jam")){

                    rate_layout.setVisibility(View.VISIBLE);
                    time_lin.setVisibility(View.GONE);
                    enddate_lin.setVisibility(View.VISIBLE);

                }else {

                    rate_layout.setVisibility(View.VISIBLE);
                    time_lin.setVisibility(View.VISIBLE);
                    enddate_lin.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        rate_typeee = rate_type.getSelectedItem().toString();





        data_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PicBirthday();
            }
        });


        start_data_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picstart_date();
            }
        });

        end_date_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picend_date();
            }
        });


        start_time_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picstart_time();
            }
        });


        end_time_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picsend_time();
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goimage();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AsBookingActivity.this,AsBookingConfirmationActivity.class);
                startActivity(intent);
            }
        });


    }

    private void goimage() {


        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropMenuCropButtonTitle("Done")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri imageUri = result.getUri();

                imageView.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void picsend_time() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(AsBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                end_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute,false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void picstart_time() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(AsBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                start_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute,false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void picend_date() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AsBookingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
        picker.show();



    }

    private void Picstart_date() {


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AsBookingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
        picker.show();

    }

    public void PicBirthday() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AsBookingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_txt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
        picker.show();
    }



    private void initi() {
        bill_unit = findViewById(R.id.bill_unit_sp);
        vanue = findViewById(R.id.vanue_sp);
        facility = findViewById(R.id.facility_sp);
        state = findViewById(R.id.stat_sp);
        city = findViewById(R.id.city_sp);
        rate_type = findViewById(R.id.rate_type_sp);
        data_rel = findViewById(R.id.date_rel);
        date_txt = findViewById(R.id.date);
        rate_layout = findViewById(R.id.rate_layout);
        time_lin= findViewById(R.id.time_lin);
        enddate_lin = findViewById(R.id.end_date_lin);
        start_data_rel = findViewById(R.id.startdate_rel);
        end_date_rel = findViewById(R.id.enddate_rel);
        start_time_rel = findViewById(R.id.start_rel);
        end_time_rel = findViewById(R.id.end_rel);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        imageView = findViewById(R.id.image1111);
        submit = findViewById(R.id.submit);




    }
}
