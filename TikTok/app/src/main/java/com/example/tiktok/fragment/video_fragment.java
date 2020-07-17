package com.example.tiktok.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tiktok.R;
import com.example.tiktok.network.Video;
import com.example.tiktok.videostream.myAdapter;

import java.util.ArrayList;
import java.util.List;

public class video_fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private myAdapter MyAdapter;
    private List<Video> mVideos = new ArrayList<>();
    private Context mContext;

    public video_fragment(List<Video> mVideos, Context mContext) {
        this.mVideos = mVideos;
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_video_stream, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mRecyclerView = getView().findViewById(R.id.recycle_video_stream);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        MyAdapter = new myAdapter(mVideos);
        mRecyclerView.setAdapter(MyAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyAdapter.start();
        Log.d("dlsjk","Resume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("dlsjk","pause");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
