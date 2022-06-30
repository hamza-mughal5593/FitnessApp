package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.BookedFacility;
import com.mtechsoft.fitmy.v1.adapter.BookedFacilitesAdapter;

import java.util.ArrayList;

public class AsBookingDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BookedFacility> bookedFacilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_booking_detail);

        initi();

        bookedFacilities.add(new BookedFacility("Dewan","20-10-2020","Per Hour","2","300.00"));
        bookedFacilities.add(new BookedFacility("Dewan","20-10-2020","Per Hour","2","300.00"));

        BookedFacilitesAdapter bookedFacilitesAdapter = new BookedFacilitesAdapter(bookedFacilities, AsBookingDetailActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AsBookingDetailActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookedFacilitesAdapter);
    }


    private void initi() {

        bookedFacilities = new ArrayList<>();
        recyclerView = findViewById(R.id.booking_detail_facility_recycler);

    }
}
