package com.example.computerworld.autotorch;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Camera camera;
    Camera.Parameters parameters;
    boolean hasFlash = false;
    boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton flashBtn=(ImageButton)findViewById(R.id.flash_button);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){

            if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                Log.d("Torchu","Has Flash");
                camera=Camera.open();
                parameters = camera.getParameters();
                hasFlash = true;
            }

        }


        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){

                    if(status)
                    {
                        flashBtn.setImageResource(R.drawable.off);
                    }
                    else{
                        flashBtn.setImageResource(R.drawable.on);
                    }
                    CheckUserPermsions();

                }
                else {
                                    if (hasFlash) {
                                        if (status) {

                                            flashBtn.setImageResource(R.drawable.off);
                                            turnOff();
                                        } else {
                                            flashBtn.setImageResource(R.drawable.on);
                                            turnOn();
                                        }

                                    }

                                    else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Error");
                                        builder.setMessage("Flashlight Not Availble.");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();


                                    }

                }

            }
        });
    }

    void CheckUserPermsions() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        /*if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.d("Torchu", "Has Flash");
            camera = Camera.open();
            parameters = camera.getParameters();
            hasFlash = true;
        }
             if (hasFlash) {
                              if (status) {
                // flashBtn.setImageResource(R.drawable.off);
                                       turnOff();
                              } else {
                                        turnOn();
                // flashBtn.setImageResource(R.drawable.on);
                                      }
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error");
            builder.setMessage("Flashlight Not Availble.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();



        }*/

        if(status){
            // flashBtn.setImageResource(R.drawable.off);
            turnOff2();
        }
        else{
            turnOn2();
            // flashBtn.setImageResource(R.drawable.on);
        }
    }
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                 /*   if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        Log.d("Torchu", "Has Flash");
                        camera = Camera.open();
                        parameters = camera.getParameters();
                        hasFlash = true;
                    }
                    if (hasFlash) {
                              if (status) {
                                           // flashBtn.setImageResource(R.drawable.off);
                                            turnOff();
                                          } else
                                         {
                                              turnOn();
                                          // flashBtn.setImageResource(R.drawable.on);
                                        }
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Flashlight Not Availble.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();



                    }*/

                       if(status){
                            // flashBtn.setImageResource(R.drawable.off);
                            turnOff2();
                        }
                        else{
                            turnOn2();
                            // flashBtn.setImageResource(R.drawable.on);
                        }
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Permission Denied" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void turnOn(){
        if(camera==null){
            camera=Camera.open();
            parameters=camera.getParameters();

        }
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        status=true;
        Log.d("Torchu","Camera On");

    }

    public void turnOff(){
        if(camera==null){
            camera=Camera.open();
            parameters=camera.getParameters();

        }
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
        status=false;
        Log.d("Torchu","Camera Off");


    }
    CameraManager mCameraManager;
    public void turnOn2(){

         mCameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            try {
                   // for(String mCameraId : mCameraManager.getCameraIdList()){

             //   CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics("0");
               // boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);




              /*     if(mCameraManager.getCameraCharacteristics(mCameraId).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {

                      hasFlash=true;

                   //}
                else{

                       AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                       builder.setTitle("Error");
                       builder.setMessage("Flashlight Not Availble.");
                       builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                               finish();
                           }
                       });
                       AlertDialog alertDialog = builder.create();
                       alertDialog.show();




                   }*/
                        if(mCameraManager.getCameraCharacteristics("0").get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
                            mCameraManager.setTorchMode("0", true);
                            status = true;
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Error");
                            builder.setMessage("Flashlight Not Availble.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }

                    }



            catch (CameraAccessException e) {
                e.printStackTrace();
            }



    }
    public void turnOff2(){
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {

           // CameraCharacteristics cameraCharacteristics=mCameraManager.getCameraCharacteristics("0");
            mCameraManager.getCameraCharacteristics("0").get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            //boolean flashAvailable=cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            if(mCameraManager.getCameraCharacteristics("0").get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
                mCameraManager.setTorchMode("0",false);
                status=false;
            }
            else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Flashlight Not Availble.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

            }



           /* for(String mCameraId : mCameraManager.getCameraIdList()){

                if(mCameraManager.getCameraCharacteristics(mCameraId).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {

                  hasFlash=true;
                    /*  mCameraManager.setTorchMode(mCameraId, false);
                    status = false;*/
               /* }


                if(hasFlash){
                      mCameraManager.setTorchMode(mCameraId, false);
                    status=false;

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Flashlight Not Availble.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }


            }*/

        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onStop() {
        super.onStop();
       /* if(camera != null){
            camera.release();
            camera = null;
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(camera != null){
            camera.release();
            camera = null;
        }

    }
}
