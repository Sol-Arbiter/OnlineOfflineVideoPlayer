package video.player.mp4player.videoplayer.download;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class DownloadNotificationService extends IntentService {

    public DownloadNotificationService() {
        super("Service");
    }

    String title, url;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    String channelId = "Download_Video_Channel_Id";


    @Override
    protected void onHandleIntent(Intent intent) {
        title = intent.getStringExtra("TITLE");
        url = intent.getStringExtra("URL");

        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, "VideoPlayer", NotificationManager.IMPORTANCE_LOW);

                notificationChannel.setDescription("Downloading...");
                notificationChannel.setSound(null, null);
                notificationChannel.enableLights(false);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(false);
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }


        notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(title)
                .setContentText("Downloading... ")
                .setDefaults(0)
                .setAutoCancel(true);
        notificationManager.notify(url.hashCode(), notificationBuilder.build());

        initRetrofit();

    }

    private void initRetrofit() {
        String exampleUrl = "/*  #Part1:   https://r2---sn-ci5gup-8one.googlevideo.com/     */           #Part2   :      videoplayback?expire=1625163159&ei=NrHdYKbfOoy8owPajb6IBg&ip=117.96.186.38&id=o-AELoSExrIUS_H13UItTE0-GQLUY5j3aTMyQY6bttSifI&itag=22&source=youtube&requiressl=yes&mh=t1&mm=31%2C29&mn=sn-ci5gup-8one%2Csn-ci5gup-qxay&ms=au%2Crdu&mv=m&mvi=2&pl=21&initcwndbps=216250&vprv=1&mime=video%2Fmp4&ns=zrrUjBHmveJYJ6Q3Ao22g1QG&cnr=14&ratebypass=yes&dur=26.610&lmt=1624942748352074&mt=1625141339&fvip=2&fexp=24001373%2C24007246&beids=9466587&c=TVHTML5&txp=5432432&n=DwK_eRwuWgaM1E&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAIsEVd8EuxSWYXNwzBuNDncBd4pM";
        String[] u = url.split(".com/");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(u[0].concat(".com/"))
                .build();

        UrlInterface downloadMethodInterface = retrofit.create(UrlInterface.class);

        Call<ResponseBody> request = downloadMethodInterface.downloadVideo(u[1]);
        try {
//https://r5---sn-ci5gup-8one.googlevideo.com/videoplayback?expire=1625165274&ei=erndYJ_vK-GFjuMPrZ2JsAw&ip=117.96.186.38&id=o-ALNQps8pWPBD_VjH_--nY3DdGuSo4lMtbp51M4RX7s9q&itag=22&source=youtube&requiressl=yes&mh=Er&mm=31%2C29&mn=sn-ci5gup-8one%2Csn-ci5gup-qxa6&ms=au%2Crdu&mv=m&mvi=5&pl=21&hcs=%2Csd&smhost=%2Cr5---sn-ci5gup-qxay.googlevideo.com&initcwndbps=215000&vprv=1&mime=video%2Fmp4&ns=_9ju8Jxu0Rw-394c7NsJ7cIG&ratebypass=yes&dur=1970.700&lmt=1625118386667479&mt=1625143503&fvip=16&fexp=24001373%2C24007246&c=TVHTML5&txp=5516222&n=fwfCPUk4S6UqO8&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAOV4dA1HShfHSF8cE780qlWXUPaoKnJqSzwu8bz_DbIOAiB4gju5GPTuY0rPi95dGnh1IX5ebQzQp0Vc8qnRRAT0uw%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Chcs%2Csmhost%2Cinitcwndbps&lsig=AG3C_xAwRAIgOvOEAsDM8rFRw2UBajeRCBqqObksL-tLXILEvKNxObgCIED2XvhBYMlddMkjxSdF_gV2L3uhML5IfCZTrmoSpJpa
//             https://r2---sn-ci5gup-8one.googlevideo.com/
//            https://r2---sn-qxaeen7l.googlevideo.com/
            downloadImage(request.execute().body());

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadImage(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
        // Add a specific media item.
        ContentResolver resolver = getApplicationContext()
                .getContentResolver();

// Find all audio files on the primary external storage device.
        Uri videoUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            videoUri = MediaStore.Video.Media
                    .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            videoUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

// Publish a new song.
        ContentValues newSong = new ContentValues();
        newSong.put(MediaStore.Audio.Media.DISPLAY_NAME,
                title + ".flv");

// Keeps a handle to the new song's URI in case we need to modify it
// later.
        Uri finalUri = resolver
                .insert(videoUri, newSong);


        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),  title + ".flv");
//        OutputStream outputStream = new FileOutputStream(finalUri.getPath());
        OutputStream outputStream =null;

// save to a folder
        newSong.put(MediaStore.MediaColumns.DISPLAY_NAME, title);
        newSong.put(MediaStore.MediaColumns.MIME_TYPE, "video/x-flv");
        newSong.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" + "VideoPlayer");
         finalUri = resolver.insert(MediaStore.Files.getContentUri("external"), newSong);
// You can use this outputStream to write whatever file you want:
        outputStream = resolver.openOutputStream(finalUri);
        long total = 0;
        boolean downloadComplete = false;
        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        while ((count = inputStream.read(data)) != -1) {

            total += count;
            int progress = (int) ((double) (total * 100) / (double) fileSize);


            updateNotification(progress);
            outputStream.write(data, 0, count);
            downloadComplete = true;
        }
        onDownloadComplete(downloadComplete);
        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    private void updateNotification(int currentProgress) {


        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendProgressUpdate(boolean downloadComplete) {

        Intent intent = new Intent("DOWNLOAD_UPDATE");
        intent.putExtra("downloadComplete", downloadComplete);
        LocalBroadcastManager.getInstance(DownloadNotificationService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(boolean downloadComplete) {
        sendProgressUpdate(downloadComplete);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("Image Download Complete");
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}