package com.mtechsoft.fitmy.v1.activity.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.mtechsoft.fitmy.R;

public class EventDetails extends AppCompatActivity {


    ImageView imageView;
    TextView description,terms_and_condtion,namee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details2);

        inite();


        Intent intent = getIntent();


        String name = intent.getStringExtra("event_name");
        String image = intent.getStringExtra("event_image");
        String desc = intent.getStringExtra("event_des");
        String term = intent.getStringExtra("event_term");

        Picasso.get()
                .load(image)
                .into(imageView);

        description.setText(desc);
        terms_and_condtion.setText(term);
        namee.setText(name);



    }

    private void inite() {

        imageView = findViewById(R.id.event_image);
        description =findViewById(R.id.tv_rda_09);
        terms_and_condtion =findViewById(R.id.tv_rda_11);
        namee =findViewById(R.id.tv_rda_03);


    }
}
