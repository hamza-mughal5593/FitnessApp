package com.mtechsoft.fitmy.v1.activity.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.mtechsoft.fitmy.R;

public class NewsDetailsActivity extends AppCompatActivity {

    ImageView large_image, small_image;
    TextView title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_news_detail_activity);

        small_image = findViewById(R.id.iv_and_01);
        large_image = findViewById(R.id.iv_and_02);
        title = findViewById(R.id.tv_and_09);
        description = findViewById(R.id.newd_description);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String image = intent.getStringExtra("image");
        String desc = intent.getStringExtra("description");

        Picasso.get()
                .load(image)
                .into(small_image);

        Picasso.get()
                .load(image)
                .into(large_image);
        title.setText(name);
        description.setText(desc);


    }


}
