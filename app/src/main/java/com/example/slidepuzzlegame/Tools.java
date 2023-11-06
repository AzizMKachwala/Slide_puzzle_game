package com.example.slidepuzzlegame;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Tools {

    Context context;
    private final Dialog dialog;

    public Tools(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public void showLoading() {
        try {
            if (dialog != null) {
                dialog.setContentView(R.layout.loading_dialog);
                dialog.setCancelable(false);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLoading() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DisplayImage(Context context, ImageView img, String urlImg) {
        Glide.with(context)
                .load(urlImg)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.user_icon)
                        .error(R.drawable.baseline_error_24))
                .into(img);
    }

//    public static void DisplayImageWithDelay(Context context, ImageView img, String urlImg, long delayMillis) {
//        // Show loading placeholder immediately
//        img.setImageResource(R.drawable.user_icon);
//
//        // Use a Handler to introduce a delay before loading the actual image
//        new Handler().postDelayed(() -> {
//            Glide.with(context)
//                    .load(urlImg)
//                    .apply(new RequestOptions()
//                            .error(R.drawable.baseline_error_24))
//                    .into(img);
//        }, delayMillis);
//    }

}