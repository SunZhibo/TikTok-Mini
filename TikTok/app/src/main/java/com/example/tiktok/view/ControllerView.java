package com.example.tiktok.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tiktok.R;
import com.example.tiktok.lib.ImageHelper;
import com.example.tiktok.network.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.INFINITE;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class ControllerView extends RelativeLayout {

    CircleImageView ivHead;
    LottieAnimationView animationView;
    RelativeLayout rlLike;
    RelativeLayout rlRecord;
    TextView tvNickname;
    CircleImageView ivHeadAnim;
    IconFontTextView ivLike;
    ImageView ivFocus;
    MarqueeTextView tvMusic;
    private OnVideoControllerListener listener;
    private Video videoData;

    public ControllerView(Context context) {
        super(context);
        init();
    }
    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    }

    private void init() {
        Log.d("bdfjslak","hfuadsjlk.");
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_controller, this);
        onBind();
        rlLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                like();
            }
        });
        setRotateAnim();
    }

    private void onBind() {
        ivHead = findViewById(R.id.iv_head);
        animationView = findViewById(R.id.lottie_anim);
        rlLike = findViewById(R.id.rl_like);
        rlRecord = findViewById(R.id.rl_record);
        tvNickname = findViewById(R.id.tv_nickname);
        ivHeadAnim = findViewById(R.id.iv_head_anim);
        ivLike = findViewById(R.id.iv_like);
        ivFocus = findViewById(R.id.iv_focus);
        tvMusic = findViewById(R.id.tv_music_name);
    }

    public void setVideoData(Video videoData) {
        this.videoData = videoData;
        ImageHelper.displayWebImage(videoData.getImageUrl(), ivHead);
        tvNickname.setText("@" + videoData.getUserName());
        ImageHelper.displayWebImage(videoData.getImageUrl(), ivHeadAnim);
        tvMusic.setText(" @" + videoData.getUserName() + " 创作的原声 - " + videoData.getUserName());

        animationView.setAnimation("like.json");

        //点赞状态
        if (videoData.getLike()) {
            ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            ivLike.setTextColor(getResources().getColor(R.color.white));
        }

        //关注状态
        ivFocus.setVisibility(VISIBLE);
    }


    public void setListener(OnVideoControllerListener listener) {
        this.listener = listener;
    }

//    @Override
//    public void onClick(View v) {
//        if (listener == null) {
//            return;
//        }
//        switch (v.getId()) {
//            case R.id.rl_like:
//                listener.onLikeClick();
//                like();
//                break;
//        }
//    }

    /**
     * 点赞动作
     */
    public void like() {
        if (!videoData.getLike()) {
            //点赞
            animationView.setVisibility(VISIBLE);
            animationView.playAnimation();
            ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            //取消点赞
            animationView.setVisibility(INVISIBLE);
            ivLike.setTextColor(getResources().getColor(R.color.white));
        }

        videoData.setLike(!videoData.getLike());
    }

    /**
     * 循环旋转动画
     */
    private void setRotateAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 359,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(INFINITE);
        rotateAnimation.setDuration(8000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rlRecord.startAnimation(rotateAnimation);
    }
}
