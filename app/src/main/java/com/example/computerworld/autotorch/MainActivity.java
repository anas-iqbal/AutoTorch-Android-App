package com.example.computerworld.autotorch;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
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
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.d("Torchu","Has Flash");
            camera=Camera.open();
            parameters = camera.getParameters();
            hasFlash = true;
        }

        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasFlash){

                            if(status){
                                flashBtn.setImageResource(R.drawable.off);
                                CheckUserPermsions();
                            }
                            else{
                                flashBtn.setImageResource(R.drawable.on);
                                CheckUserPermsions();
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
        });
    }

    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        if(hasFlash){

            if(status){
               // flashBtn.setImageResource(R.drawable.off);
                turnOff();


            }
            else{
                turnOn();
               // flashBtn.setImageResource(R.drawable.on);
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
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(hasFlash){

                        if(status){
                            // flashBtn.setImageResource(R.drawable.off);
                            turnOff();


                        }
                        else{
                            turnOn();
                            // flashBtn.setImageResource(R.drawable.on);
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
                } else {
                    // Permission Denied
                    Toast.makeText( this,"your message" , Toast.LENGTH_SHORT)
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
