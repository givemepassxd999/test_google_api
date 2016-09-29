package com.example.givemepass.bookapidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<JsonData.Item> dataList;
    private MyAdapter mAdapter;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        dataList = new ArrayList<>();
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10")
                .build();
        client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Json get failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String result = response.body().string();
                    Gson gson = new Gson();
                    final JsonData jsonData = gson.fromJson(result, JsonData.class);
                    dataList.addAll(jsonData.getItemList());
                    mAdapter.setData(dataList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new MyAdapter(dataList);
        mListView.setAdapter(mAdapter);
    }

    private class MyAdapter extends BaseAdapter{
        private List<JsonData.Item> datas;
        private MyAdapter(List<JsonData.Item> datas){
            this.datas = datas;
        }

        public void setData(List<JsonData.Item> datas){
            this.datas = datas;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder holder;
            if(view == null){
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter_item, null);
                holder = new Holder();
                holder.title = (TextView) view.findViewById(R.id.book_name);
                holder.publisher = (TextView) view.findViewById(R.id.book_publisher);
                holder.img = (ImageView) view.findViewById(R.id.book_avatar);
                view.setTag(holder);
            } else{
                holder = (Holder) view.getTag();
            }
            JsonData.Item item = datas.get(position);
            if(item != null && item.getInfo() != null) {
                String title = item.getInfo().getTitle();
                if(title != null) {
                    holder.title.setText(title);
                }
                String publisher = item.getInfo().getPublisher();
                if(publisher != null) {
                    holder.publisher.setText(publisher);
                }
                if(item.getInfo().getImageLink() != null) {
                    String thumbnailUrl = item.getInfo().getImageLink().getSmallThumbnail();
                    if (thumbnailUrl == null) {
                        thumbnailUrl = item.getInfo().getImageLink().getThumbnail();
                    }
                    if (thumbnailUrl != null) {
                        Glide.with(MainActivity.this)
                            .load(thumbnailUrl)
                            .error(R.drawable.ic_android_black_24dp)
                            .centerCrop()
                            .fitCenter()
                            .into(holder.img);
                    }
                }
            }
            return view;
        }
        class Holder{
            public ImageView img;
            public TextView title;
            public TextView publisher;

        }
    }
}
