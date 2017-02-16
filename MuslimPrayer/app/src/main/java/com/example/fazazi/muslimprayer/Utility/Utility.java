package com.example.fazazi.muslimprayer.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

import java.util.concurrent.TimeUnit;

public class Utility {

    public static String convertDuration(long duration) {
        String hms;

        if (TimeUnit.MILLISECONDS.toHours(duration) > 0) {
            hms = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(duration),
                    TimeUnit.MILLISECONDS.toMinutes(duration)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                    TimeUnit.MILLISECONDS.toSeconds(duration)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        } else {
            hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                    TimeUnit.MILLISECONDS.toSeconds(duration)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        }
        return hms;


    }

    public static boolean isNetworkConnected(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm
                .getActiveNetworkInfo().isConnected());
        return connected;
    }

    public static void showAlert(final Activity activity, final String message, final String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setTitle(title).setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertValidation(final Activity activity, final String message, final String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setTitle(title).setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}