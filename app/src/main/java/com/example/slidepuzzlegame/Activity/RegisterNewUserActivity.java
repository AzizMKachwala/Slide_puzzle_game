package com.example.slidepuzzlegame.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterNewUserActivity extends AppCompatActivity {

    ImageView imgPhotoClick;
    CircleImageView imgUser;
    EditText etvUserName;
    Button btnStart;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    ActivityResultLauncher<Intent> cameraLauncher;
    String currentPhotoPath = "";
    private File currentPhotoFile;
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        imgUser = findViewById(R.id.imgUser);
        imgPhotoClick = findViewById(R.id.imgPhotoClick);
        etvUserName = findViewById(R.id.etvUserName);
        btnStart = findViewById(R.id.btnStart);

        tools = new Tools(this);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etvUserName.getText().toString().trim().isEmpty() && !imgUser.toString().trim().isEmpty()) {
                    MyDatabaseHandler myDatabaseHandler = new MyDatabaseHandler(RegisterNewUserActivity.this);
                    myDatabaseHandler.insertData(etvUserName.getText().toString().trim(), imgUser.toString().trim());
                    Toast.makeText(RegisterNewUserActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterNewUserActivity.this, GameScreenActivity.class);
                    intent.putExtra("Name", etvUserName.getText().toString().trim());
                    intent.putExtra("ImagePath", currentPhotoPath);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterNewUserActivity.this, "Fill in All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {

                new android.os.Handler().postDelayed(() -> {
                    Tools.DisplayImage(RegisterNewUserActivity.this, imgUser, currentPhotoPath);
                },1000);

            } else {
                Toast.makeText(this, "Can't Complete The Action", Toast.LENGTH_SHORT).show();
            }
        });

        imgPhotoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    currentPhotoPath = "";
                    if (checkCameraPermission()) {
                        openCamera();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.slidepuzzlegame", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoFile = image;
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}