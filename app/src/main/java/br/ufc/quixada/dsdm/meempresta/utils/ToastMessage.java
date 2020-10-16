package br.ufc.quixada.dsdm.meempresta.utils;

import android.content.Context;
import android.widget.Toast;

public abstract class ToastMessage {

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(Context context, String message, Integer duration) {
        Toast.makeText(context, message, duration).show();
    }

}
