package com.sojeong.kakaopayproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ResultActivity extends AppCompatActivity {
    List<ImageData> ImageDataList;
    RecyclerView recyclerView;
    private static String API_KEY="40a8869addf46860862567acb6fb2fdd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(ResultActivity.this, 3);
        recyclerView.setLayoutManager(mGridLayoutManager);
        ImageDataList =new ArrayList<ImageData>();

        Intent intent=getIntent();
        String Keyword=intent.getStringExtra("Keyword");
        if(Keyword==null){
            Toast.makeText(ResultActivity.this,"검색어를 다시입력해주세요.",Toast.LENGTH_LONG).show();
        }else {
            GetData task = new GetData();
            task.execute(Keyword);
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;
        int errortoast=0;
        String searchKeyword;
        String convertKeyword;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ResultActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            searchKeyword=params[0];
            try {
                convertKeyword=URLEncoder.encode(searchKeyword,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String serverURL = "https://dapi.kakao.com/v2/search/image?query="+convertKeyword;
            try {
                URL url = new URL(serverURL);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestProperty("Authorization", "KakaoAK " + API_KEY);
                httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpsURLConnection.setRequestProperty("charset", "utf-8");
                httpsURLConnection.setReadTimeout(5000);
                httpsURLConnection.setConnectTimeout(5000);
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.connect();

                int responseStatusCode = httpsURLConnection.getResponseCode();
                Log.d("response", "response code - " + responseStatusCode);
                InputStream inputStream;
                if (responseStatusCode == HttpsURLConnection.HTTP_OK) {
                    inputStream = httpsURLConnection.getInputStream();
                } else {
                    //error
                    inputStream = httpsURLConnection.getErrorStream();
                    Log.d("inputStream", "getErrorStream - " + inputStream);
                    errortoast=1;
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {

                Log.d("", "InsertData: Error ", e);
                errorString = e.toString();
                return null;

            }

        }

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            progressDialog.dismiss();

            if(errortoast==1){
                Toast.makeText(ResultActivity.this,searchKeyword+"에 대한 검색결과없음.",Toast.LENGTH_LONG).show();
            }
            Log.d("getData(post)", "response - " + result);

            if (result == null) {
                Log.d("getData(post)", "error - " + result);
            } else {
                Log.e("onPost", "onPostElse");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("documents");
                    //meta어케받을지고민

                    Log.e("sojeong---",""+jsonArray);
                    if(jsonArray.length()==0){
                        Toast.makeText(ResultActivity.this,searchKeyword+"에 대한 검색결과없음.",Toast.LENGTH_LONG).show();
                    }

                    Log.e("try", "try");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("hashmap", "hashmap");
                        JSONObject item = jsonArray.getJSONObject(i);

                        String collection=item.getString("collection");
                        String thumbnail_url = item.getString("thumbnail_url");
                        String image_url = item.getString("image_url");
                        String width = item.getString("width");
                        String height = item.getString("height");
                        String display_sitename=item.getString("display_sitename");
                        String doc_url = item.getString("doc_url");
                        String datetime=item.getString("datetime");

                        ImageData imageData=new ImageData(collection,thumbnail_url,image_url,
                                width,height,display_sitename,doc_url,datetime);
                        ImageDataList.add(imageData);

                        ResultAdapter myAdapter = new ResultAdapter(ResultActivity.this, ImageDataList);
                        recyclerView.setAdapter(myAdapter);
                    }
                } catch (JSONException e) {

                    Log.d("showResult", "showResult : ", e);
                }


            }

        }


    }
}
