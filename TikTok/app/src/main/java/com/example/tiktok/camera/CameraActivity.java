package com.example.tiktok.camera;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tiktok.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    final int PERMISSION_REQUEST_CODE = 100;
    final int ACTIVITY_REQUEST_CODE = 200;

    private CameraView mPreview;
    private Camera mCamera;
    private ImageView mImageView;
    private FrameLayout mpreview;
    private MediaRecorder mMediaRecorder;
    private Button mButton;
    private boolean isRecording;
    private boolean hasPhoto;
    private String videoPath;
    private String imagePath;
    private Camera.PictureCallback mPictureCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        isRecording = false;
        hasPhoto = false;
        mImageView = findViewById(R.id.image_view_take_video);
        mpreview = findViewById(R.id.camera_view);
        mButton = findViewById(R.id.button_photo_ensure);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        intiCameraView();
        initImageView();

        mPictureCallBack = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream fos = null;
                String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "1.jpg";
                imagePath = filePath;
                File file = new File(filePath);
                try {
                    fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mCamera == null) {
            initCamera();
        }
        mCamera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    private void intiCameraView() {
        initCamera();
        mPreview = new CameraView(this, mCamera);
        mpreview.addView(mPreview);
    }

    private void initCamera() {
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.set("orientation", "portrait");
        parameters.set("rotation", 90);
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
    }

    private void initImageView() {
        mImageView.setImageResource(R.drawable.ic_baseline_brightness_white);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPhoto) {
                    hasPhoto = true;
                    mCamera.takePicture(null, null, mPictureCallBack);
                    mImageView.setVisibility(View.INVISIBLE);
                    mButton.setVisibility(View.VISIBLE);
                }
                else {
                    if (!isRecording) {
                        if (prepareVideoRecorder()) {
                            mMediaRecorder.start();
                            ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageView, "ScaleX", 1f, 0.5f);
                            ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageView, "ScaleY", 1f, 0.5f);
                            animator1.setDuration(500);
                            animator2.setDuration(500);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(animator1, animator2);
                            animatorSet.start();
                            isRecording = true;
                        } else {
                            releaseMediaRecorder();
                        }
                    } else {
                        mMediaRecorder.stop();
                        releaseMediaRecorder();
                        mCamera.lock();
                        mImageView.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                        isRecording = false;
                        Intent intent = new Intent(CameraActivity.this, VideoPlayActivity.class);
                        intent.putExtra("VIDEOPATH", videoPath);
                        startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
                    }
                }
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setVisibility(View.INVISIBLE);
                mImageView.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                mImageView.setVisibility(View.VISIBLE);
                mCamera.startPreview();
            }
        });
    }

    private String getOutputMediaPath() {
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir, "VEDIO_" + timeStamp + ".mp4");
        if (!mediaFile.exists()) {
            mediaFile.getParentFile().mkdirs();
        }
        return mediaFile.getAbsolutePath();
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCamera.lock();
        }
    }

    private boolean prepareVideoRecorder() {
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        videoPath = getOutputMediaPath();
        mMediaRecorder.setOutputFile(videoPath);
        mMediaRecorder.setPreviewDisplay(mPreview.mHolder.getSurface());
        mMediaRecorder.setOrientationHint(90);
        try {
            mMediaRecorder.prepare();
        } catch (Exception e) {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent tmpData = new Intent();
            tmpData.putExtra("VIDEOPATH", videoPath);
            tmpData.putExtra("IMAGEPATH", imagePath);
            setResult(RESULT_OK, tmpData);
            finish();
        }
    }
}
