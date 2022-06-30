package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.Complex;
import com.mtechsoft.fitmy.v1.adapter.ComplexAdapter;

import java.util.ArrayList;

public class SelectComplexActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Complex> complexes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slect_complex);

        init();

        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));
        complexes.add(new Complex("Asad","Description is here","03035258350","030352583506","asad@gmail.com","1: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad \n 2: Asad"));


        ComplexAdapter complexAdapter= new ComplexAdapter(complexes, SelectComplexActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectComplexActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(complexAdapter);



    }

    private void init() {

        complexes = new ArrayList<>();
        recyclerView  = findViewById(R.id.complex_recycler);


    }
}
