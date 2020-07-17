package com.example.tiktok.videostream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.example.tiktok.R;
import com.example.tiktok.network.Video;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PagerSnapHelperActivity extends Activity {
    private RecyclerView mRecyclerView;
    private myAdapter mMyAdapter;
    private List<Video> mVideos;
    private Intent intent;
    private Bundle bundle;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_stream);
        initUI();
    }


    public void initUI() {
        mVideos = new ArrayList<>();

        intent = this.getIntent();
        bundle = intent.getExtras();
        int position = bundle.getInt("POSITION");

        ArrayList<String> user_name = bundle.getStringArrayList("USERNAME");
        ArrayList<String> student_id = bundle.getStringArrayList("STUDENTID");
        ArrayList<String> image_url = bundle.getStringArrayList("IMAGEURL");
        ArrayList<String> video_url = bundle.getStringArrayList("VIDEOURL");
        int size = user_name.size();
        for (int i = 0; i < size; i++) {
            mVideos.add(new Video(student_id.get(i), user_name.get(i), image_url.get(i), video_url.get(i)));
        }

        mRecyclerView = findViewById(R.id.recycle_video_stream);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 3000;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mMyAdapter = new myAdapter(mVideos);
        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
