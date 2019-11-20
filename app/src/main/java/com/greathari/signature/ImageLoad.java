package com.greathari.signature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ImageLoad extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    Button read;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        imageView=findViewById(R.id.imageView);
        read=findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state= Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkPermission()) {
                            File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Signature/newImage.jpeg");
                            if (dir.exists()) {
                                Log.d("path", dir.toString());
                                BitmapFactory.Options options=new BitmapFactory.Options();
                                options.inPreferredConfig= Bitmap.Config.ARGB_8888;
                                Bitmap bitmap=BitmapFactory.decodeFile(String.valueOf(dir), options);
                                imageView.setImageBitmap(bitmap);
                            }
                        } else {
                            requestPermission(); // Code for permission
                        }
                    } else {
                        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Signature/newImage.jpeg");
                        if (dir.exists()) {
                            Log.d("path", dir.toString());
                            BitmapFactory.Options options=new BitmapFactory.Options();
                            options.inPreferredConfig=Bitmap.Config.ARGB_8888;
                            Bitmap bitmap=BitmapFactory.decodeFile(String.valueOf(dir), options);
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                }
            }
        });
    }
    private boolean checkPermission() {
        int result= ContextCompat.checkSelfPermission(ImageLoad.this,     android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result== PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ImageLoad.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(ImageLoad.this, "Write External Storage permission allows us to read  files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ImageLoad.this, new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)  {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }
}
