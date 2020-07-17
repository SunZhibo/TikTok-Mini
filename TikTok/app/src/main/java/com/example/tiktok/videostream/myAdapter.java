package com.example.tiktok.videostream;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiktok.R;
import com.example.tiktok.ijkplayer.VideoPlayerIJK;
import com.example.tiktok.ijkplayer.VideoPlayerListener;
import com.example.tiktok.lib.ImageHelper;
import com.example.tiktok.list.GridVideoAdapter;
import com.example.tiktok.network.Video;
import com.example.tiktok.view.ControllerView;
import com.example.tiktok.view.LikeView;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    private List<Video> mDataset = new ArrayList<>();
    public boolean leave = false;

    public myAdapter(List<Video> dataset) {
        mDataset.addAll(dataset);

    }

    public void pause() {
        leave = true;
    }

    public void start() {
        leave = false;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public VideoView mVideoView;
        LikeView likeView;
        ControllerView controllerView;
        ImageView ivcover;
        ImageView ivPlay;

        public MyViewHolder(View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.video_view_stream);
            likeView = itemView.findViewById(R.id.likeview);
            controllerView = itemView.findViewById(R.id.controller);
            ivcover = itemView.findViewById(R.id.iv_cover);
            ivPlay = itemView.findViewById(R.id.iv_play);
        }

        public void initVideo(String url) {
            mVideoView.setVideoPath(url);
            mVideoView.start();
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        holder.mVideoView.pause();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        if(leave) holder.mVideoView.pause();
        else if (!holder.mVideoView.isPlaying()) {
            Log.d("VideoAdapt","restart");
            holder.mVideoView.start();
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        Log.d("adapterljdfssd","bdflajsk");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        final Video video = mDataset.get(position);
        viewHolder.controllerView.setVideoData(video);
        ImageHelper.displayWebImage(video.getImageUrl(), viewHolder.ivcover);

        viewHolder.ivPlay.setAlpha(0.4f);

        viewHolder.likeView.setOnLikeListener(new LikeView.OnLikeListener() {
            @Override
            public void onLikeListener() {
                if (!video.getLike()) {  //未点赞，会有点赞效果，否则无
                    viewHolder.controllerView.like();
                }
            }
        });

        viewHolder.likeView.setOnPlayPauseListener(new LikeView.OnPlayPauseListener() {
            @Override
            public void onPlayOrPause() {
                if (viewHolder.mVideoView.isPlaying()) {
                    viewHolder.mVideoView.pause();
                    viewHolder.ivPlay.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.mVideoView.start();
                    viewHolder.ivPlay.setVisibility(View.GONE);
                }
            }
        });

        viewHolder.initVideo(mDataset.get(position).getVideoUrl());
        viewHolder.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                viewHolder.mVideoView.start();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}