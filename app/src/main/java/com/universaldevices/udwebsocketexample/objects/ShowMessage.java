/*
 * Copyright (c) 2021. Javier Refuerzo. All rights reserved.
 */

package com.universaldevices.udwebsocketexample.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.universaldevices.udwebsocketexample.R;


public class ShowMessage {


    private static final String LOG_TAG = ShowMessage.class.getSimpleName();

    public interface Completion {
        void onCompletion();
    }

    public static void singleButtonMessage(final String message, Context context, Completion positiveHandler){

        if (context == null){
            Log.v(LOG_TAG, "Context is NULL");
            return;
        }


        new Handler(Looper.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);

            builder.setNegativeButton(R.string.ok, (dialog, id) -> {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the action.
                if (dialog != null) {
                    dialog.dismiss();
                    if (positiveHandler != null) {
                        positiveHandler.onCompletion();
                    }
                }
            });

            Activity activity = (Activity) context;
            //check if the activity is finishing to prevent a crash
            if (!activity.isFinishing()) {
                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else {
                Log.v(LOG_TAG, "Activity is finishing");
            }

        });

    }


    public static void doubleButton(final String message, Context context, int positiveButton, int negativeButton, Completion positiveHandler, Completion negativeHandler ){
        doubleButton(context, message, 0, positiveButton, negativeButton, positiveHandler, negativeHandler);
    }

    public static void doubleButton(int message, Context context, int positiveButton, int negativeButton, Completion positiveHandler, Completion negativeHandler ){
        doubleButton(context, null, message, positiveButton, negativeButton, positiveHandler, negativeHandler);
    }

    private static void doubleButton(Context context, String messageString, int messageInt, int positiveButton, int negativeButton, Completion positiveHandler, Completion negativeHandler) {

        if (context == null) {
            Log.v(LOG_TAG, "Context is NULL");
            return;
        }


        new Handler(Looper.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (messageString != null) {
                builder.setMessage(messageString);
            } else if (messageInt != 0) {
                builder.setMessage(messageInt);
            }

            builder.setNegativeButton(negativeButton, (dialog, id) -> {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the action.
                if (dialog != null) {
                    dialog.dismiss();
                    if (negativeHandler != null) {
                        negativeHandler.onCompletion();
                    }
                }
            });

            builder.setPositiveButton(positiveButton, (dialog, id) -> {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the action.
                if (dialog != null) {
                    dialog.dismiss();
                    if (positiveHandler != null) {
                        positiveHandler.onCompletion();
                    }
                }
            });


            Activity activity = (Activity) context;
            //check if the activity is finishing to prevent a crash
            if (!activity.isFinishing()) {
                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else {
                Log.v(LOG_TAG, "Activity is finishing");
            }


        });


    }

}
