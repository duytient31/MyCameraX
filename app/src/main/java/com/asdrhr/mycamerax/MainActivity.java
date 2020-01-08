package com.asdrhr.mycamerax;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSION = 101;
    private String[] mPermissionArray = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    List<String> mPermissionList =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission(mPermissionArray,mPermissionList,REQUEST_CODE_PERMISSION);

    }


    private void initPermission(String[] mPermissionArray,List<String> mPermissionList,int REQUEST_CODE_PERMISSION ){
        for (int i=0; i< mPermissionArray.length; i++){
           if (ContextCompat.checkSelfPermission(this,mPermissionArray[i]) != PackageManager.PERMISSION_GRANTED){
               mPermissionList.add(mPermissionArray[i]);
           }
        }
        if (!mPermissionList.isEmpty()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                requestPermissions(permissions,REQUEST_CODE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION){

        }
    }
}
