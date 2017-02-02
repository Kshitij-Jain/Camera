package com.github.kshitij_jain.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import static android.provider.MediaStore.ACTION_VIDEO_CAPTURE;

public class VideoActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideoView = (VideoView) findViewById(R.id.videoView);
    }

    // Capture a Video with the Camera App
    public void captureVideo(View view) {
        Intent intent = new Intent(ACTION_VIDEO_CAPTURE);
        // Check if is there is any application to capture image
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // View The Video
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            // The Android Camera application returns the video in the Intent delivered
            // to onActivityResult() as a Uri pointing to the video location in storage.
            Uri videoUri = data.getData();
            Log.d("videoUri", videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();
        }
    }
}
