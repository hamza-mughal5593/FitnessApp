package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import com.mtechsoft.fitmy.R;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
//        viewHolder.textViewDescription.setText("This is slider item " + position);

        switch (position) {
            case 0:
                viewHolder.imageViewBackground.setImageResource(R.drawable.bg_01);
                break;
            case 1:
                viewHolder.imageViewBackground.setImageResource(R.drawable.bg_02);
                break;
            case 2:
                viewHolder.imageViewBackground.setImageResource(R.drawable.bg_03);
                break;
            case 3:
                viewHolder.imageViewBackground.setImageResource(R.drawable.bg_04);
                break;
            case 4:
                viewHolder.imageViewBackground.setImageResource(R.drawable.bg_05);
                break;
            default:
                viewHolder.imageViewBackground.setImageResource(R.drawable.bg_06);
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
