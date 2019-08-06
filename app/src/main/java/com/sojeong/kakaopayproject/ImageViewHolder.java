package com.sojeong.kakaopayproject;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView mPlace;

    public ImageViewHolder(View itemView) {
        super(itemView);
        mPlace = (ImageView)itemView.findViewById(R.id.oneimage);
    }
}