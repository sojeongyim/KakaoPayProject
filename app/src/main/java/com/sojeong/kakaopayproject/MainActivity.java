package com.sojeong.kakaopayproject;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editText;
    ImageButton imageButton;
    private static Animation clickanimation;
    ConnectivityManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickanimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.clickanimation);

        editText=(EditText)findViewById(R.id.editText);
        imageButton=(ImageButton)findViewById(R.id.search_button);
        imageButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        view.startAnimation(clickanimation);
        switch (view.getId()){
            case R.id.search_button:
                if(editText.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this,"검색어를 입력해주세요!",Toast.LENGTH_LONG).show();
                }else{
                    manager = (ConnectivityManager) MainActivity.this.getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                    if(networkInfo == null){
                        Toast.makeText(MainActivity.this,"인터넷을 연결해주세요!",Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("Keyword", editText.getText().toString());
                        startActivity(intent);
                    }
                }
                break;

            default:
                break;
        }
    }
}

