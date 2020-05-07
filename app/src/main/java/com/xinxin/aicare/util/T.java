package com.xinxin.aicare.util;

import android.content.Context;
import android.widget.Toast;

import com.xinxin.aicare.PaperUrineApplication;

public class T {

    private static PaperUrineApplication app;

    private T() {
    }

    public static void init(PaperUrineApplication app) {
        T.app = app;
    }

    public static void s(String msg) {
        if (app == null) return;
        s(app, msg);
    }

    public static void l(String msg) {
        if (app == null) return;
        l(app, msg);
    }

    public static void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}