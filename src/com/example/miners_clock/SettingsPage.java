package com.example.miners_clock;

 
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
 
public class SettingsPage extends Activity implements OnClickListener {
 
    int thisWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        getIdOfCurrentWidget(savedInstanceState);
 
        ((Button) findViewById(R.id.create_widget)).setOnClickListener(this);
 
    }
 
    /**
     * Get the Id of Current Widget from the intent from the Widget or if it is
     * <span id="IL_AD9" class="IL_AD">the first time</span>
     **/
    void getIdOfCurrentWidget(Bundle savedInstanceState) {
        setResult(RESULT_CANCELED);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            thisWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }
 
    /**
     * Update the widget on first run
     * 
     * @param widgetId
     *            - the ID of current widget to be created
     */
    public void updateWidget(int widgetId) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.widget_main);
 
        MyTimer myTimer = new MyTimer(this);
        Bitmap bmp = myTimer.buildUpdate(myTimer.getTodaysTime());
        remoteViews.setImageViewBitmap(R.id.imageView_txt, bmp);
 
        // set a background
        remoteViews.setImageViewResource(R.id.imageView_bg, R.drawable.theme0);
 
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(SettingsPage.this);
        // update the widget
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
 
    public void setResultDataToWidget(int result, int widgetId) {
        System.out.println("WID ID = " + widgetId);
 
        // update the widget on creation
        updateWidget(widgetId);
 
        // set the result back to widget
        Intent resultValue = new Intent();
        // pass the widget ID along with the intent so that we will get it on
        // the cofiguration activity
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(result, resultValue);
        finish();
    }
 
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_widget)
            setResultDataToWidget(Activity.RESULT_OK, thisWidgetId);
    }
 
}