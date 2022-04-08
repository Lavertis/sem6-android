package com.lavertis.project3.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.lavertis.project3.MainActivity;
import com.lavertis.project3.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {
    public static final String NOTIFICATION = "com.lavertis.project3.services.DownloadService.NOTIFICATION";
    public static final String INFO = "INFO";
    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_DOWNLOAD_FILE = "com.lavertis.project3.services.DownloadService.action.download_file";
    private static final String PARAM_DOWNLOAD_FILE = "com.lavertis.project3.services.DownloadService.extra.param_download_file";
    private NotificationManager mNotificationManager;


    public DownloadService() {
        super("MyIntentService");
    }

    public static void startActionDownloadFile(Context context, String param1) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD_FILE);
        intent.putExtra(PARAM_DOWNLOAD_FILE, param1);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        setupNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD_FILE.equals(action)) {
                final String url = intent.getStringExtra(PARAM_DOWNLOAD_FILE);
                handleActionDownloadFile(url);
            } else {
                Log.e("MyIntentService", "Unknown action: " + action);
            }
        }
        Log.d("MyIntentService", "Service has done its job");
    }

    private void handleActionDownloadFile(String address) {
        try {

            // create url and connect
            URL url = new URL(address);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a typical 0-100% progress bar
            int bytesTotal = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());

            String path = Environment.getExternalStorageDirectory() + File.separator + "file.txt";
            OutputStream output = new FileOutputStream(path);

            byte[] data = new byte[1024];
            int bytesDownloaded = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                bytesDownloaded += count;
                output.write(data, 0, count);
                Log.i("MyIntentService", "Downloaded " + bytesDownloaded + " of " + bytesTotal);
                sendBroadcast(bytesDownloaded, bytesTotal);
            }

            // close streams
            output.flush();
            output.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupNotificationChannel() {
        CharSequence name = getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(
                getString(R.string.app_name), name, NotificationManager.IMPORTANCE_LOW);
        mNotificationManager.createNotificationChannel(channel);
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder = new Notification.Builder(this, getString(R.string.app_name));
        notificationBuilder.setContentTitle(getString(R.string.notification_title))
                .setProgress(100, 0, false)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_HIGH);

        notificationBuilder.setChannelId(getString(R.string.app_name));
        return notificationBuilder.build();
    }

    private void sendBroadcast(int bytesDownloaded, int bytesTotal) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("downloaded", bytesDownloaded);
        intent.putExtra("total", bytesTotal);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
