package com.example.tiktok.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tiktok.R;
import com.example.tiktok.lib.ImageHelper;
import com.example.tiktok.network.Video;

import java.util.ArrayList;
import java.util.List;
/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class GridVideoAdapter extends RecyclerView.Adapter<GridVideoAdapter.GridVideoViewHolder> {

    private List<Video> mVideos = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;

    public static class GridVideoViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        ImageView head;
        TextView username;

        private View contentview;
        public GridVideoViewHolder(View v) {
            super(v);
            contentview = v;
            cover = v.findViewById(R.id.iv_cover);
            head = v.findViewById(R.id.iv_head);
            username = v.findViewById(R.id.tv_username);
        }

        public void onBind(int position, Video video) {
            if (video.getImageHeight() < 1000) cover.setMinimumHeight(1000);
            ImageHelper.displayWebImage(video.getImageUrl(),cover);
            ImageHelper.displayWebImage(video.getImageUrl(),head);
            username.setText(video.getUserName());
            Log.d("Adapter","bind "+ position);
        }
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(GridVideoViewHolder holder, final int position) {
        holder.onBind(position, mVideos.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemCLick(position);
            }
        });
    }

    public interface IOnItemClickListener {
        void onItemCLick(int position);
    }

    public GridVideoAdapter(List<Video> myVideos) {

        mVideos.addAll(myVideos);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    @Override
    public GridVideoAdapter.GridVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GridVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridvideo, parent, false));
    }
}
