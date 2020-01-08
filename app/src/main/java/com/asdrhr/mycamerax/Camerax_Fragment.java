package com.asdrhr.mycamerax;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;

public class Camerax_Fragment extends Fragment implements LifecycleOwner {

    TextureView textureView;
    ImageButton imageButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camerax_fragment,container,false);
        textureView = view.findViewById(R.id.view_finder);
        imageButton = view.findViewById(R.id.img_button);
        return view;
    }

    /**
     * 相机相关所有操作
     */
    public void startCamera(){
        /**
         * 1.预览相机画面
         */
        PreviewConfig config = new PreviewConfig.Builder()
                .setTargetResolution(new Size(640,480))
                .build();
        Preview preview = new Preview(config);
        preview.setOnPreviewOutputUpdateListener(output -> {
            ViewGroup parent = (ViewGroup) textureView.getParent();
            parent.removeView(textureView);
            parent.addView(textureView,0);

            textureView.setSurfaceTexture(output.getSurfaceTexture());
            updateCamera();
        });

        /**
         * 2.拍照
         */
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder()
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .build();
        ImageCapture imageCapture = new ImageCapture(imageCaptureConfig);

        imageButton.setOnClickListener(v -> {
            File file = new File(getContext().getExternalCacheDir()+"/"+ System.currentTimeMillis() + ".jpg");

           imageCapture.takePicture(file,null,new ImageCapture.OnImageSavedListener() {
               @Override
               public void onImageSaved(@NonNull File file) {
                   Toast.makeText(getContext(),"Save Success",Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                   Toast.makeText(getContext(),"Save Error",Toast.LENGTH_SHORT).show();
               }
           });
        });


        //绑定生命周期
        CameraX.bindToLifecycle( this, preview,imageCapture);

    }

    /**
     * 补偿设备旋转变化
     */
    private void updateCamera(){
        Matrix matrix = new Matrix();
        float centerX = textureView.getWidth() / 2f;
        float centerY = textureView.getHeight() / 2f;

        float[] rotations = {0,90,180,270};
        float rotationDegrees = rotations[textureView.getDisplay().getRotation()];
        matrix.postRotate(-rotationDegrees, centerX, centerY);
        textureView.setTransform(matrix);
    }
}
