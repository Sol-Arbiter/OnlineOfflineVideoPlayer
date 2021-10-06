package video.player.mp4player.videoplayer.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DeanandDanCaten {

    public static final String USER_PREFS = "USER PREFS";
    public SharedPreferences appSharedPref;
    public SharedPreferences.Editor prefEditor;

    public String FileName = "FileName";
    public String PipId = "PipId";
    public String LastAMID = "AMID";
    public String isFirstTimeLoading = "isFirstTimeLoading";
    public String DownloadURL = "DownloadURL";
    public String ALLDataURL = "ALLDataURL";
    public String PrivacyAccepted = "PrivacyAccepted";
    public String SaveDirURL = "SaveDirURL";
    public String BIT128 = "BIT128";


    public String getBIT128() {
        return appSharedPref.getString(BIT128, "");
    }

    public void setBIT128(String _BIT128) {
        this.prefEditor.putString(BIT128, _BIT128).commit();
    }
    public String getDownloadURL() {
        return appSharedPref.getString(DownloadURL, "");
    }

    public void setDownloadURL(String _DownloadURL) {
        this.prefEditor.putString(DownloadURL, _DownloadURL).commit();
    }
    public String getText() {
        return appSharedPref.getString(BIT128, "");
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

    public String isTutorialDialog = "true";
    public String getisTutorialDialog() {
        return appSharedPref.getString(isTutorialDialog, "");
    }

    public void setisTutorialDialog(String _isTutorialDialog) {
        this.prefEditor.putString(isTutorialDialog, _isTutorialDialog).commit();
    }

    public DeanandDanCaten(Context context) {
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

    String Agrement = "Agrement";

    public String getAgrement() {
        return appSharedPref.getString(Agrement, "");
    }

    public void setAgrement(String pipID) {
        this.prefEditor.putString(Agrement, pipID).commit();
    }

    public String getFrameName() {
        return appSharedPref.getString(FrameName, "");
    }

    public void setFrameName(String frameName) {
        this.prefEditor.putString(FrameName, frameName).commit();
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
            Log.e("isDownloadArray",gson.fromJson(json, type).toString());
            return gson.fromJson(json, type);
        } else {
            return null;
        }
    }

}
