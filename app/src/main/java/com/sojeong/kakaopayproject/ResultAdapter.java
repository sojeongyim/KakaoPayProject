package com.sojeong.kakaopayproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    List<ImageData> ImageUrlList;
    private static Animation clickanimation;

    public ResultAdapter(Context mContext,List<ImageData> ImageUrlList){
        this.mContext=mContext;
        this.ImageUrlList = ImageUrlList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Picasso.get()
                .load(ImageUrlList.get(position).getThumbnail_url())
                .error(R.drawable.ic_launcher_foreground)
                .into( ((ImageViewHolder)holder).mPlace);

        clickanimation = AnimationUtils.loadAnimation(mContext, R.anim.clickanimation);
        ((ImageViewHolder)holder).mPlace.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                view.startAnimation(clickanimation);
                String tempExtra;
                Intent intent =new Intent(mContext,DetailImageActivity.class);

                tempExtra=ImageUrlList.get(position).getDatetime();
                intent.putExtra("datetime",tempExtra.substring(0,tempExtra.indexOf("T")));

                tempExtra=ImageUrlList.get(position).getCollection()+" > "+ImageUrlList.get(position).getDisplay_sitename();
                intent.putExtra("collection_site",tempExtra);

                intent.putExtra("doc_url",ImageUrlList.get(position).getDoc_url());

                tempExtra=ImageUrlList.get(position).getWidth()+" x "+ImageUrlList.get(position).getHeight();
                intent.putExtra("size",tempExtra);

                intent.putExtra("image_url",ImageUrlList.get(position).getImage_url());

                mContext.startActivity(intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return ImageUrlList.size();
    }


}
