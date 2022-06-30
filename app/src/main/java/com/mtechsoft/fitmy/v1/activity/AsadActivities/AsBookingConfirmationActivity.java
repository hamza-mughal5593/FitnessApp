package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.BookedFacility;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.mtechsoft.fitmy.v1.adapter.BookedFacilitesAdapter;
import com.mtechsoft.fitmy.v1.adapter.ComplexAdapter;

import java.util.ArrayList;

public class AsBookingConfirmationActivity extends AppCompatActivity {

    Spinner payment_method;

    RecyclerView recyclerView;
    ArrayList<BookedFacility> bookedFacilities;
    RelativeLayout add_facility,confirm_booking;

    CheckBox term_cb;
    TextView term_link;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_booking_confirmation);

        initi();

        bookedFacilities.add(new BookedFacility("Dewan","20-10-2020","Per Hour","2","300.00"));
        bookedFacilities.add(new BookedFacility("Dewan","20-10-2020","Per Hour","2","300.00"));

        BookedFacilitesAdapter bookedFacilitesAdapter = new BookedFacilitesAdapter(bookedFacilities, AsBookingConfirmationActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AsBookingConfirmationActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookedFacilitesAdapter);


        ArrayAdapter<CharSequence> adapters6 = ArrayAdapter.createFromResource(this,
                R.array.paymnet_methods, R.layout.spinner_item);
        adapters6.setDropDownViewResource(R.layout.spinner_item);
        payment_method.setAdapter(adapters6);


        add_facility.setOnClickListener(view -> {


            Intent intent = new Intent(AsBookingConfirmationActivity.this,SelectFacilityActivity.class);
            startActivity(intent);

        });


        term_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                term_cb.setChecked(false);

                AlertDialog.Builder mbuilder=new AlertDialog.Builder(AsBookingConfirmationActivity.this);

                View mview = LayoutInflater.from(AsBookingConfirmationActivity.this).inflate(R.layout.ok_dialog,null);
                RelativeLayout ok_rel=mview.findViewById(R.id.i_agree);
                mbuilder.setView(mview);
                final AlertDialog dialog2= mbuilder.create();
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.show();

                ok_rel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog2.dismiss();
                        term_cb.setChecked(true);
                    }
                });



            }
        });

        term_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                term_cb.setChecked(false);

                AlertDialog.Builder mbuilder=new AlertDialog.Builder(AsBookingConfirmationActivity.this);

                View mview = LayoutInflater.from(AsBookingConfirmationActivity.this).inflate(R.layout.ok_dialog,null);
                RelativeLayout ok_rel=mview.findViewById(R.id.i_agree);
                mbuilder.setView(mview);
                final AlertDialog dialog2= mbuilder.create();
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.show();

                ok_rel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                        term_cb.setChecked(true);
                    }
                });



            }
        });

        confirm_booking.setOnClickListener(view -> {

            Intent intent = new Intent(AsBookingConfirmationActivity.this,AsBookingDetailActivity.class);
            startActivity(intent);

        });

    }

    private void initi() {

        bookedFacilities = new ArrayList<>();
        payment_method = findViewById(R.id.payment_methods_sp);
        recyclerView = findViewById(R.id.booked_facility_recycler);
        add_facility = findViewById(R.id.add_facility_btn);
        term_cb = findViewById(R.id.term_cb);
        term_link = findViewById(R.id.term_conditon_link);
        confirm_booking = findViewById(R.id.confirm_booking_rel);


    }


}
