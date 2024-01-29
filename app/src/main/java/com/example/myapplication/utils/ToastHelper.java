package com.example.myapplication.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ToastHelper {

    public static void showCustomToast(Context context, String type, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_custom, null);

        LinearLayout toastLayout = layout.findViewById(R.id.toast_layout_root);
        ImageView imageToast = layout.findViewById(R.id.image_toast);
        TextView textToast = layout.findViewById(R.id.text_toast);

        switch (type) {
            case "success":
                toastLayout.setBackgroundResource(R.drawable.toast_background_success);
                imageToast.setImageResource(R.drawable.ic_success);
                break;
            case "alert":
                toastLayout.setBackgroundResource(R.drawable.toast_background_alert);
                imageToast.setImageResource(R.drawable.ic_alert);
                break;
            case "failure":
                toastLayout.setBackgroundResource(R.drawable.toast_background_failure);
                imageToast.setImageResource(R.drawable.ic_failure);
                break;
            case "info":
                toastLayout.setBackgroundResource(R.drawable.toast_background_info);
                imageToast.setImageResource(R.drawable.ic_info);
                break;
            default:
                toastLayout.setBackgroundResource(R.drawable.toast_background_default);
                imageToast.setImageResource(R.drawable.ic_info);
                break;
        }

        textToast.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.END, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
