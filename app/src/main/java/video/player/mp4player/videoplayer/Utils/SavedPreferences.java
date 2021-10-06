package video.player.mp4player.videoplayer.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavedPreferences {

    public static final String USER_PREFS = "USER PREFS";
    public SharedPreferences appSharedPref;
    public SharedPreferences.Editor prefEditor;





    public String video_path = "video_path";
    public String video_position = "video_position";

    public String FileName = "FileName";
    public String PipId = "PipId";
    public String LastAMID = "AMID";
    public String isFirstTimeLoading = "isFirstTimeLoading";
    public String DownloadURL = "DownloadURL";
    public String ALLDataURL = "ALLDataURL";
    public String StickersDataURL = "StickersDataURL";
    public String PrivacyAccepted = "PrivacyAccepted";
    public String SaveDirURL = "SaveDirURL";
    public String BIT128 = "BIT128";
    public String currentNoOfImages = "currentNoOfImages";


    public String getDownloadURL() {
        return appSharedPref.getString(DownloadURL, "");
    }

    public void setDownloadURL(String _DownloadURL) {
        this.prefEditor.putString(DownloadURL, _DownloadURL).commit();
    }

    public String getSaveDirURL() {
        return appSharedPref.getString(SaveDirURL, "");
    }

    public void setSaveDirURL(String _SaveDirURL) {
        this.prefEditor.putString(SaveDirURL, _SaveDirURL).commit();
    }

    public String getPrivacyAccepted() {
        return appSharedPref.getString(PrivacyAccepted, "");
    }

    public void setPrivacyAccepted(String _PrivacyAccepted) {
        this.prefEditor.putString(PrivacyAccepted, _PrivacyAccepted).commit();
    }

    public String getStickersDataURL() {
        return appSharedPref.getString(StickersDataURL, "");
    }

    public void setStickersDataURL(String _ALLDataURL) {
        this.prefEditor.putString(StickersDataURL, _ALLDataURL).commit();
    }

    public String getALLDataURL() {
        return appSharedPref.getString(ALLDataURL, "");
    }

    public void setALLDataURL(String _ALLDataURL) {
        this.prefEditor.putString(ALLDataURL, _ALLDataURL).commit();
    }

    public String getFileName() {
        return appSharedPref.getString(FileName, "");
    }

    public void setFileName(String _FileName) {
        this.prefEditor.putString(FileName, _FileName).commit();
    }

    public String getLastAMID() {
        return appSharedPref.getString(LastAMID, "");
    }

    public void setLastAMID(String _LastAMID) {
        this.prefEditor.putString(LastAMID, _LastAMID).commit();
    }

    public String getIsFirstTimeLoading() {
        return appSharedPref.getString(isFirstTimeLoading, "");
    }

    public void setIsFirstTimeLoading(String _isFirstTimeLoading) {
        this.prefEditor.putString(isFirstTimeLoading, _isFirstTimeLoading).commit();
    }

    public String getPipId() {
        return appSharedPref.getString(PipId, "");
    }

    public void setPipId(String _PipId) {
        this.prefEditor.putString(PipId, _PipId).commit();
    }

    public String isRatingDialog = "isRatingDialog";

    public int getisRatingDialog() {
        return appSharedPref.getInt(isRatingDialog, 0);
    }

    public void setisRatingDialog(int _isRatingDialog) {
        this.prefEditor.putInt(isRatingDialog, _isRatingDialog).commit();
    }

    public void setFrameType(String _FileName) {
        this.prefEditor.putString(FileName, _FileName).commit();
    }

    public void setcurrentNoOfImages(String _setcurrentNoOfImages) {
        this.prefEditor.putString(currentNoOfImages, _setcurrentNoOfImages).commit();
    }

    public SavedPreferences(Context context) {
        this.appSharedPref = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedPref.edit();
    }

    String PipName = "PipName";
    String FrameName = "FrameName";

    public String getPipName() {
        return appSharedPref.getString(PipName, "");
    }

    public void setPipName(String pipID) {
        this.prefEditor.putString(PipName, pipID).commit();
    }

    public String getFrameName() {
        return appSharedPref.getString(FrameName, "");
    }

    public void setFrameName(String frameName) {
        this.prefEditor.putString(FrameName, frameName).commit();
    }

    public String getText() {
        return appSharedPref.getString(BIT128, "");
    }

    public void setBIT128(String _BIT128) {
        this.prefEditor.putString(BIT128, _BIT128).commit();
    }

    public String isDownloadArray = "isDownloadArray";

    public void setDownloadArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        this.prefEditor.putString(isDownloadArray, json).commit();
    }

    public ArrayList<String> getDownloadArrayList() {
        Gson gson = new Gson();
        String json = appSharedPref.getString(isDownloadArray, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            Log.e("isDownloadArray", gson.fromJson(json, type).toString());
            return gson.fromJson(json, type);
        } else {
            return null;
        }
    }

    public String POSITION = "position";

    public int getposition() {
        return appSharedPref.getInt(POSITION, 0);
    }

    public void setposition(int position) {
        this.prefEditor.putInt(POSITION, position).commit();
    }

    public String SEEK = "seek_";

    public int getSeekValue(int i,int defValue) {
        return appSharedPref.getInt(SEEK + i, defValue);
    }

    public void setSeekValue(int i, int position) {
        this.prefEditor.putInt(SEEK + i, position).commit();
    }

    public String getVideo_path() {
        return appSharedPref.getString(video_path, "");
    }

    public void setVideo_path(String video_path) {
        this.prefEditor.putString(this.video_path, video_path).commit();
    }

    public int getVideo_position() {
        return appSharedPref.getInt(video_position, 0);
    }

    public void setVideo_position(int video_position) {
        this.prefEditor.putInt(this.video_position, video_position).commit();
    }

    public String Short_list = "Short_list";

    public int getShort_list() {
        return appSharedPref.getInt(Short_list, 3);
    }

    public void setShort_list(int _Short_list) {
        this.prefEditor.putInt(Short_list, _Short_list).commit();
    }

    public String newtab = "newtab";

    public void setNewtab(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        this.prefEditor.putString(newtab, json).commit();
    }

    public ArrayList<String> getNewtab() {
        Gson gson = new Gson();
        String json = appSharedPref.getString(newtab, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            Log.e("newtab", gson.fromJson(json, type).toString());
            return gson.fromJson(json, type);
        } else {
            return null;
        }
    }

    public String language = "language";

    public int getLanguage() {
        return appSharedPref.getInt(language, 2);
    }

    public void setLanguage(int _language) {
        this.prefEditor.putInt(language, _language).commit();
    }

    public String theem= "theem";

    public int getTheem() {
        return appSharedPref.getInt(theem,1);
    }

    public void setTheem(int _theem) {
        this.theem = theem;
        this.prefEditor.putInt(theem, _theem).commit();
    }

    public String actuly_video_position = "actuly_video_position";

    public int getActuly_video_position() {
        return appSharedPref.getInt(actuly_video_position, 0);
    }

    public void setActuly_video_position(int actuly_video_position) {
        this.prefEditor.putInt(this.actuly_video_position, actuly_video_position).commit();
    }


}
