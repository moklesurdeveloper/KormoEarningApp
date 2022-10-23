package com.bdearning.group.adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bdearning.group.model.SliderData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import bdearning.kormoappp.R;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<SliderData> mSliderItems;
    private Context context;
    SharedPreferences sharedPreferences;


    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.context = context;
        sharedPreferences  = context.getSharedPreferences("user", Context.MODE_PRIVATE);;

    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(v ->
        {
            if (position ==0)
            {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(sharedPreferences.getString("sliderUrl1","")));
                context.startActivity(i);
            }
            else if (position ==1)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(sharedPreferences.getString("sliderUrl2","")));
                context.startActivity(i);
            }
            else if (position ==2)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(sharedPreferences.getString("sliderUrl3","")));
                context.startActivity(i);
            }
            else if (position ==3)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(sharedPreferences.getString("sliderUrl4","")));
                context.startActivity(i);
            }
            else if (position ==4)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(sharedPreferences.getString("sliderUrl5","")));
                context.startActivity(i);
            }
        });
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;
        }
    }
}
