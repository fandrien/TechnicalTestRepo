package com.andrien.technicaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private GridView gridGambar;

    private ArrayList<Photo> photos;

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        gridGambar = findViewById(R.id.gridGambar);
        mydb = new DBHelper(this);
        photos = mydb.getAllPhotos();
        gridGambar.setAdapter(new GridAdapter(this));

        new GetData().execute();
    }

    public class GridAdapter extends BaseAdapter {
        private Context context;

        public GridAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public Object getItem(int i) {
            return photos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int idx = i;
            View convertView= null;

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.photo_layout, viewGroup,false);
                ImageView ivGambar = convertView.findViewById(R.id.ivGambar);
                TextView tvTitle = convertView.findViewById(R.id.tvTitle);
                tvTitle.setText(photos.get(idx).getTitle());

                String imageUrl = photos.get(idx).getImageUrl();
               // tvTitle.setText(imageUrl);
                //Glide.with(context).clear(myImage);
                Glide.with(MainActivity.this).load(imageUrl).into(ivGambar);


            }

            return convertView;
        }
    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://jsonplaceholder.typicode.com/photos";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {

                    // Getting JSON Array node
                    JSONArray photosArr = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < photosArr.length(); i++) {
                        JSONObject c = photosArr.getJSONObject(i);
                        int albumid = Integer.parseInt(c.getString("albumId"));
                        int id = Integer.parseInt(c.getString("id"));
                        String title = c.getString("title");
                        String imageUrl = c.getString("url");
                        String thumbnailUrl = c.getString("thumbnailUrl");


                        Cursor rs = mydb.getData(id);
                        if(rs.moveToFirst() == false){
                            mydb.insertPhoto(albumid, id, title, imageUrl, thumbnailUrl);
                        }

                        if (!rs.isClosed())  {
                            rs.close();
                        }

                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            photos = mydb.getAllPhotos();

            gridGambar.setSelection(photos.size());
        }
    }
}
