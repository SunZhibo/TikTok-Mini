package com.example.tiktok.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tiktok.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    public static final int TYPE_MES = 1;
    public static final int TYPE_FUN = 0;
    private List<Messeges> mDataset = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView tvimage;
        TextView tvname;
        TextView tvtime;
        TextView tvmessage;
        View funct;
        private View contentView;
        public MyViewHolder(View v) {
            super(v);
            contentView = v;
            funct = v.findViewById(R.id.funct);
            tvimage = v.findViewById(R.id.tvimage);
            tvtime = v.findViewById(R.id.tvtime);
            tvname = v.findViewById(R.id.tvname);
            tvmessage = v.findViewById(R.id.tvmessage);
        }
        public void onBind(int position, Messeges data) {
            tvtime.setText(data.time);
            tvname.setText(data.name);
            tvmessage.setText(data.message);
            tvimage.setImageResource(data.image);
        }
        public void onBindf(int position) {
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (position == 0) holder.onBindf(position);
        else holder.onBind(position, mDataset.get(position-1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mItemClickListener.onItemCLick(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_FUN;//banner在开头
        }else {
            return TYPE_MES;
        }
    }

    public interface IOnItemClickListener {
        void onItemCLick(int position);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public MyAdapter(List<Messeges> myDataset) {

        mDataset.addAll(myDataset);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case TYPE_FUN:
                return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.functions, parent, false));
            case TYPE_MES:
                return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
        }
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

}

