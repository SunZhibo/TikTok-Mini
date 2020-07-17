package com.example.tiktok.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tiktok.list.GridVideoAdapter;
import com.example.tiktok.network.Video;
import com.example.tiktok.R;
import com.example.tiktok.message.MyAdapter;
import com.example.tiktok.videostream.PagerSnapHelperActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * create by libo
 * create on 2020-05-19
 * description 附近的人fragment
 */
public class list_fragment extends Fragment {
    RecyclerView recyclerView;
    private GridVideoAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    private List<Video> mVideos = new ArrayList<>();
    private Context mContext;

    private static final String TAG = "List";

    public list_fragment(List<Video> mVideos, Context context) {
        this.mVideos = mVideos;
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        initView();
        Log.i(TAG,"page" + "created");
    }

    private void initView() {
        recyclerView = getView().findViewById(R.id.recyclerlist);
        refreshLayout = getView().findViewById(R.id.refreshlayout);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 这里有一片用来测试的
//        Video test = new Video();
//        test.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg");
//        test.setVideoUrl("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8");
//        test.setStudentId("tianyuan");
//        test.setUserName("bebinca");
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
//        mVideos.add(test);
        // 测试代码到这里结束
        adapter = new GridVideoAdapter(mVideos);
        adapter.setOnItemClickListener(new GridVideoAdapter.IOnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                // TODO 点击点开滑动视频
                Intent intent = new Intent(mContext, PagerSnapHelperActivity.class);
                intent.putExtra("POSITION", position);
                putVideoInformation(intent);
                startActivity(intent);
                Log.d(TAG, "click" + position);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.buttonRed);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        refreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });
    }

    private void putVideoInformation(Intent intent) {
        int size = mVideos.size();
        ArrayList<String> student_id = new ArrayList<>();
        ArrayList<String> user_name = new ArrayList<>();
        ArrayList<String> image_url = new ArrayList<>();
        ArrayList<String> video_url = new ArrayList<>();
        for (int i = 0; i< size; i++) {
            Video tmpVideo = mVideos.get(i);
            student_id.add(tmpVideo.getStudentId());
            user_name.add(tmpVideo.getUserName());
            image_url.add(tmpVideo.getImageUrl());
            video_url.add(tmpVideo.getVideoUrl());
        }
        intent.putExtra("STUDENTID", student_id);
        intent.putExtra("USERNAME", user_name);
        intent.putExtra("IMAGEURL", image_url);
        intent.putExtra("VIDEOURL", video_url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }
}
