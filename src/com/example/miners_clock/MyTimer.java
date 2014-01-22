package com.example.miners_clock;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.widget.RemoteViews;
 
public class MyTimer {
    RemoteViews remoteViews;
    Context context;
    AppWidgetManager appWidgetManager;
    ComponentName thisWidget;
 
    public MyTimer(Context context) {
 
        appWidgetManager = AppWidgetManager.getInstance(context);
 
        this.context = context;
 
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_main);
 
        thisWidget = new ComponentName(context, MyAppWidgetProvider.class);
 
    }
 
    public synchronized void runAndUpdateTheWidget() {
 
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
 
        for (final int appWidgetId : allWidgetIds) {
 
            System.out.println("UPDATING......" + getTodaysTime() + " ID = "
                    + appWidgetId);
 
            remoteViews.setImageViewBitmap(R.id.imageView_txt,
                    buildUpdate(getTodaysTime()));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
 
        }
 
    }
 
    public Bitmap buildUpdate(String time) {
        int bmpWidth = 250;
        int bmpHeight = 100;
        Bitmap myBitmap = Bitmap.createBitmap(bmpWidth, bmpHeight,
                Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(context.getAssets(),
                "digital-7.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(clock);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(70); 
        paint.setTextAlign(Align.CENTER);
        myCanvas.drawText(time, bmpWidth / 2, bmpHeight / 2 + (bmpHeight / 4),
                paint);
        return myBitmap;
    }
 
    public String getTodaysTime() {
        final Calendar c = Calendar.getInstance();
        int hour = Integer
                .parseInt(convertToNormal(c.get(Calendar.HOUR_OF_DAY)));
        int minute = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        return new StringBuilder().append(pad(hour)).append(":")
                .append(pad(minute)).append(":").append(pad(seconds))
                .toString();
    }
 
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
 
    public String convertToNormal(int hour) {
        if (hour > 12)
            hour = hour - 12;
 
        return pad(hour);
    }
}
