package com.sojeong.kakaopayproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailImageActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView origin_image;
    TextView[] detail=new TextView[5];
    String url;
    private static Animation clickanimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailimage);
        clickanimation = AnimationUtils.loadAnimation(DetailImageActivity.this, R.anim.clickanimation);
        initView();


        Intent intent =getIntent();
        detail[0].setText(intent.getStringExtra("datetime"));
        detail[1].setText(intent.getStringExtra("collection_site"));
        detail[2].setText(intent.getStringExtra("size"));
        url=intent.getStringExtra("doc_url");
        detail[3].setText(url);

        Picasso.get()
                .load(intent.getStringExtra("image_url"))
                .error(R.drawable.ic_launcher_foreground)
                .into(origin_image);
        }

        void initView(){
            origin_image=(ImageView)findViewById(R.id.origin_image);
            detail[0]=(TextView)findViewById(R.id.datetime);
            detail[1]=(TextView)findViewById(R.id.collection_site);
            detail[2]=(TextView)findViewById(R.id.size);
            detail[3]=(TextView)findViewById(R.id.doc_url);
            detail[4]=(TextView)findViewById(R.id.gotosite);

            origin_image.setOnClickListener(this);
            for(int i=3;i<5;i++){
                detail[i].setOnClickListener(this);
            }
        }


    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        switch (view.getId()){
            case R.id.doc_url:
                view.startAnimation(clickanimation);
                startActivity(intent);
                break;
            case R.id.origin_image:
                view.startAnimation(clickanimation);
                startActivity(intent);
                break;
            case R.id.gotosite:
                view.startAnimation(clickanimation);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
