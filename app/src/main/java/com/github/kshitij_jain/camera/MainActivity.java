package com.github.kshitij_jain.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.provider.MediaStore.EXTRA_OUTPUT;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SAVE_IMAGE_CAPTURE = 2;

    private String mCurrentPhotoPath;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    // Take a Photo with the Camera App
    public void takePicture(View view) {
        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        // Check if is there is any application to capture image
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Take and Save a Photo with the Camera App
    public void takeSavePicture(View view) {
        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        // Check if is there is any application to capture image
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = saveImage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.github.kshitij_jain.camera.fileprovider",
                        photoFile);
                // It will stop android to provide thumbnail
                intent.putExtra(EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, SAVE_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get The Thumbnail
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            // The Android Camera application encodes the photo in the return Intent delivered
            // to onActivityResult() as a small Bitmap in the extras, under the key "data"/
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    // Save Image To Public Image Directory
    private File saveImage() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // Files you save in the directories provided by getExternalFilesDir()
        // or getFilesDir() are deleted when the user uninstalls your app.
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("Image Save Path", mCurrentPhotoPath);
        return image;
    }

    public void videoActivityIntent(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
}
