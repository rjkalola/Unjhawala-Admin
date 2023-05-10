package com.unjhawalateaadmin.common.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.unjhawalateaadmin.R;
import com.priyankvasa.android.cameraviewex.CameraView;
import com.priyankvasa.android.cameraviewex.ErrorLevel;
import com.priyankvasa.android.cameraviewex.Image;
import com.priyankvasa.android.cameraviewex.Modes;


import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import kotlin.Unit;

public class CameraFragment extends Fragment {

    private CameraView camera;
    private ImageView ivFlashSwitch;
    private ImageView ivFramePreview;
    private ImageView ivCapturePreview;
    private ImageView ivCloseCapturePreview;

//    private RequestManager glideManager;

    private final Matrix matrix = new Matrix();

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        glideManager = Glide.with(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
//            camera.start();
//            camera.startVideoRecording(outputFile);
            camera.capture();
        }
    }

    @Override
    public void onPause() {
        camera.stop();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        camera.destroy();
        super.onDestroyView();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        camera = view.findViewById(R.id.camera);
        ImageView ivCapture = view.findViewById(R.id.ivCaptureButton);
        ivFlashSwitch = view.findViewById(R.id.ivFlashSwitch);
        ivFramePreview = view.findViewById(R.id.ivFramePreview);
        ivCapturePreview = view.findViewById(R.id.ivCapturePreview);
        ivCloseCapturePreview = view.findViewById(R.id.ivCloseCapturePreview);
        ivCloseCapturePreview.setOnClickListener((View v) -> {
            ivCapturePreview.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
        });

        ivCapture.setOnClickListener((View v) -> camera.capture());

        ivFlashSwitch.setOnClickListener((View v) -> {

            @DrawableRes int flashDrawableId;

            switch (camera.getFlash()) {

                case Modes.Flash.FLASH_OFF:
                    flashDrawableId = R.drawable.ic_flash_auto;
                    camera.setFlash(Modes.Flash.FLASH_AUTO);
                    break;

//                case Modes.Flash.FLASH_AUTO:
//                    flashDrawableId = R.drawable.ic_flash_on;
//                    camera.setFlash(Modes.Flash.FLASH_ON);
//                    break;
//
//                case Modes.Flash.FLASH_ON:
//                    flashDrawableId = R.drawable.ic_flash_off;
//                    camera.setFlash(Modes.Flash.FLASH_OFF);
//                    break;

                default:
                    return;
            }

            ivFlashSwitch.setImageDrawable(ActivityCompat.getDrawable(requireContext(), flashDrawableId));
        });

        setupCamera();
    }

    private void setupCamera() {

        View view = getView();
        if (view == null) return;

        camera.addCameraOpenedListener(() -> {
            return Unit.INSTANCE;
        });

        camera.addPictureTakenListener((Image image) -> {
            showCapturePreview(image);
            return Unit.INSTANCE;
        });

        camera.setContinuousFrameListener(
            5f, // Max frame rate
            (Image image) -> {
                showFramePreview(image);
                return Unit.INSTANCE;
            }
        );

        camera.addCameraErrorListener((Throwable t, ErrorLevel errorLevel) -> {

            return Unit.INSTANCE;
        });

        camera.addCameraClosedListener(() -> {
            return Unit.INSTANCE;
        });
    }

    private void showCapturePreview(@NotNull Image image) {

    /*    final float previewCaptureScale = 0.2f;

        final RequestOptions requestOptions = new RequestOptions()
            .override((int) (image.getWidth() * previewCaptureScale), (int) (image.getHeight() * previewCaptureScale));

        Activity activity = getActivity();
        if (activity != null) activity.runOnUiThread(() -> {
            glideManager.load(image.getData())
                .apply(requestOptions)
                    .into(ivCapturePreview);
                ivCapturePreview.setVisibility(View.VISIBLE);
                ivCloseCapturePreview.setVisibility(View.VISIBLE);
            }
        );*/
    }

    private void showFramePreview(@NotNull Image image) {

        byte[] jpegData;

        YuvImage yuvImage = new YuvImage(
            image.getData(),
            ImageFormat.NV21,
            image.getWidth(),
            image.getHeight(),
            null
        );

        final ByteArrayOutputStream jpegDataStream = new ByteArrayOutputStream();

        final float previewFrameScale = 0.4f;

        yuvImage.compressToJpeg(
            new Rect(0, 0, image.getWidth(), image.getHeight()),
            (int) (100 * previewFrameScale),
            jpegDataStream
        );

        jpegData = jpegDataStream.toByteArray();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bm = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length, options);

        if (bm == null) return;

    /*    final RequestOptions requestOptions = new RequestOptions()
            .override((int) (bm.getWidth() * previewFrameScale), (int) (bm.getHeight() * previewFrameScale));

        Activity activity = getActivity();
        if (activity != null) activity.runOnUiThread(() ->
            glideManager.load(rotate(bm, image.getExifInterface().getRotation()))
                .apply(requestOptions)
                .into(ivFramePreview)
        );*/
    }

    private Bitmap rotate(Bitmap bm, int rotation) {

        if (rotation == 0) return bm;

        matrix.setRotate(rotation);

        return Bitmap.createBitmap(
            bm,
            0,
            0,
            bm.getWidth(),
            bm.getHeight(),
            matrix,
            true
        );
    }
}
