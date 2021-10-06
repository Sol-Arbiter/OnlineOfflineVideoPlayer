package video.player.mp4player.videoplayer.Utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import video.player.mp4player.videoplayer.model.FolderDataModel;
import video.player.mp4player.videoplayer.model.VideoListModel;
import com.smarteist.autoimageslider.SliderView;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class CommonUtilities {

    private static boolean isFolderFound = false;
    public int currentState = -1;
    public int currentScreen = -1;

    public static ArrayList<VideoListModel> FinalVideoList;

    public static ArrayList<String> getAllMedia(Context context) {
        ArrayList<String> videoItemHashSet = new ArrayList<>();
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }

    public static ArrayList<String> getFolderMedia(Context context, String Path) {
        ArrayList<String> path_vid = new ArrayList<>();
        String pattern1 = ".mp4";
        String pattern2 = ".mkv";
        String pattern3 = ".avi";
        String pattern4 = ".flv";
        String pattern5 = ".mov";
        String pattern6 = ".mpg";
        String pattern7 = ".3gp";
        String pattern8 = ".wmv";
        String pattern9 = ".asf";
        String pattern10 = ".ts";
        String pattern11 = ".rmvb";
        String pattern12 = ".m4v";

        File dir = new File(Path);
        final File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                } else {
                    if (listFile[i].getName().endsWith(pattern1)
                            || listFile[i].getName().endsWith(pattern2)
                            || listFile[i].getName().endsWith(pattern3)
                            || listFile[i].getName().endsWith(pattern4)
                            || listFile[i].getName().endsWith(pattern5)
                            || listFile[i].getName().endsWith(pattern6)
                            || listFile[i].getName().endsWith(pattern7)
                            || listFile[i].getName().endsWith(pattern8)
                            || listFile[i].getName().endsWith(pattern9)
                            || listFile[i].getName().endsWith(pattern10)
                            || listFile[i].getName().endsWith(pattern11)
                            || listFile[i].getName().endsWith(pattern12)) {
                        path_vid.add(listFile[i].getAbsolutePath());
                    }
                }
            }
        }
        ArrayList<String> downloadedList = new ArrayList<>(path_vid);
        return downloadedList;
    }

    public static ArrayList<FolderDataModel> getmediaperent(ArrayList<String> folder_list_t, ArrayList<FolderDataModel> folder_list) {
        for (int i = 0; i < folder_list_t.size(); i++) {

            if (folder_list != null) {
                if (folder_list.size() > 0) {
                    File f = new File(folder_list_t.get(i));
                    for (int j = 0; j < folder_list.size(); j++) {
                        if (folder_list.get(j).getFolder_name().equals(f.getParent())) {
                            folder_list.get(j).setItems(folder_list.get(j).getItems() + 1);
                            isFolderFound = true;
                            break;
                        } else {
                            isFolderFound = false;
                        }
                    }
                    if (!isFolderFound) {
                        FolderDataModel a = new FolderDataModel(f.getParent(), 1);
                        folder_list.add(a);
                    }
                } else {
                    File f = new File(folder_list_t.get(i));
                    folder_list.add(new FolderDataModel(f.getParent(), 1));
                }
            }
        }

        return folder_list;
    }


    private ArrayList<String> FetchImages(String path) {

        ArrayList<String> filenames = new ArrayList<String>();
        path = Environment.getExternalStorageDirectory()
                + File.separator + "Your folder name";

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {

            String file_name = files[i].getName();

            filenames.add(file_name);
        }
        return filenames;
    }

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        String minuteString = "";


        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else {
            minuteString = "" + minutes;
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minuteString + ":" + secondsString;


        return finalTimerString;
        }



    public static class LongOperation extends AsyncTask<String, Void, String> {
        SliderView sliderView;
        String str_id;
        final String[] title = {null};

        public LongOperation(SliderView sliderView,String str_id) {
            this.sliderView = sliderView;
            this.str_id = str_id;

        }

        @Override
        protected String doInBackground(String... params) {
            String youtubeVideoId = params[0];
            try {
                if (youtubeVideoId != null) {
                    InputStream is = new URL("http://www.youtube.com/oembed?url=https://www.youtube.com/watch?v="+youtubeVideoId+"&format=json").openStream();
                    Log.e("is",String.valueOf(is));
                    title[0] = new JSONObject(IOUtils.toString(is)).getString("title");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return title[0];
        }

        @Override
        protected void onPostExecute(String result) {
            sliderView.setDescription(title[0]);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}