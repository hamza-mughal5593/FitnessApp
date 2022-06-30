package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.Facility;
import com.mtechsoft.fitmy.v1.adapter.ComplexAdapter;
import com.mtechsoft.fitmy.v1.adapter.FacilityAdapter;

import java.util.ArrayList;

public class SelectFacilityActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Facility> facilities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility);


        inti();

        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));
        facilities.add(new Facility(R.drawable.backimage,"Asad","Ali","RM 300","RM 300","RM 300"));


        FacilityAdapter facilityAdapter= new FacilityAdapter(facilities, SelectFacilityActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SelectFacilityActivity.this,2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(facilityAdapter);

    }

    private void inti() {


        facilities = new ArrayList<>();
        recyclerView = findViewById(R.id.facility_recycler);



    }


}
