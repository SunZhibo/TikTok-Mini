package com.example.tiktok;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.example.tiktok.camera.CameraActivity;
import com.example.tiktok.fragment.main_fragment;
import com.example.tiktok.fragment.message_fragment;
import com.example.tiktok.fragment.personal_fragment;
import com.example.tiktok.fragment.list_fragment;
import com.example.tiktok.fragment.video_fragment;
import com.example.tiktok.network.GetVideosResponse;
import com.example.tiktok.network.IMiniDouyinService;
import com.example.tiktok.network.PostVideoResponse;
import com.example.tiktok.network.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_REQUSET_CODE = 1;

    private static final int PAGE_COUNT = 5;
    private ArrayList<String> list = new ArrayList<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);
    private List<Video> mVideos = new ArrayList<>();

    private ViewPager pager;
    private XTabLayout tabLayout;
    private ImageView camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();
        fetchFeed();

        pager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_mainmenu);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, CAMERA_REQUSET_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUSET_CODE && resultCode == RESULT_OK) {
            String videoPath = data.getStringExtra("VIDEOPATH");
            String imagePath = data.getStringExtra("IMAGEPATH");
            postVideo(imagePath, videoPath);
        }
    }

    private void initFragment() {
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                if (i == 0) return new video_fragment(mVideos, MainActivity.this);
                if (i == 1) return new list_fragment(mVideos, MainActivity.this);
                if(i == 3) return new message_fragment();
                if(i == 4) return new personal_fragment();
                return new main_fragment();
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 2) return null;
                else return list.get(position);
            }
        });
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
    }

    private void initList() {
        list.add("首页");
        list.add("关注");
        list.add("");
        list.add("消息");
        list.add("我");
    }

    private MultipartBody.Part getMultipartFromUri(String name, String path) {
        File f = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestBody);
    }

    private void postVideo(String mSelectedImage, String mSelectedVideo) {
        MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", mSelectedImage);
        MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);
        miniDouyinService.postVideo("dy908lan10", "test_tyyyy", coverImagePart, videoPart).enqueue(
                new Callback<PostVideoResponse>() {
                    @Override
                    public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                        if (response.body() != null) {

                        }
                    }

                    @Override
                    public void onFailure(Call<PostVideoResponse> call, Throwable throwable) {

                    }
                });
    }

    private void fetchFeed() {
        miniDouyinService.getVideos().enqueue(new Callback<GetVideosResponse>() {
            @Override
            public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                if (response.body() != null && response.body().videos != null) {
                    mVideos = response.body().videos;
                    initFragment();
                }
            }

            @Override
            public void onFailure(Call<GetVideosResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}