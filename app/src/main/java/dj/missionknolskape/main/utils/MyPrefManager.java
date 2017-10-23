package dj.missionknolskape.main.utils;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import dj.missionknolskape.main.activities.MyApplication;

/**
 * Created by DJphy on 28-09-2016.
 */
public class MyPrefManager {

    private static final String TAG = "MyPrefManager";
    private SharedPreferences pref;
    private static MyPrefManager mPrefManager;

    private SharedPreferences.Editor editor;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    private final String PREF_NAME = "dj_knolskape_pref";

    private MyPrefManager() {
        pref = MyApplication.getInstance().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public static MyPrefManager getInstance() {
        if (mPrefManager == null) {
            mPrefManager = new MyPrefManager();
        }
        return mPrefManager;
    }

    public static final String KEY_LAST_QUERIED = "last_query";
    public static final String KEY_EXP = "explanation";
    public static final String KEY_HD_URL = "hd_url";
    public static final String KEY_NORMAL_URL = "url";
    public static final String KEY_TITLE = "topic_title";
    public static final String KEY_COPYRIGHT = "copyright";

    //public static final String KEY_PROJECT_TITLE = "vision_tiles";
    public static final String KEY_NAME = "vision_name";
    public static final String KEY_EMAIL = "vision_email";
    public static final String KEY_PHONE = "vision_ph";
    public static final String KEY_CUSTID = "vision_id";
    public static final String KEY_DETAILS_DONE = "vision_is_done";
    public static final String KEY_ALL_PROJECT = "vision_projects";
    public static final String KEY_PIC_DETAILS_DONE = "vision_project_pic_details";



    public void setAllProjectNames(Set<String> projectNames) {
        editor.putStringSet(KEY_ALL_PROJECT, projectNames);
        editor.commit();
        Log.d(TAG, "setAllProjectNames - MyPrefManager: " + projectNames);
    }

    public Set<String> getProjectNames() {
        Set<String> txt = pref.getStringSet(KEY_ALL_PROJECT, new HashSet<String>());
        Log.d(TAG, "getProjectNames - MyPrefManager: " + txt);
        return txt;
    }


    public void setEachProjectData(String key, Set<String> filepath) {
        editor.putStringSet(key, filepath);
        editor.commit();
        Log.d(TAG, "setEachProjectData - MyPrefManager: " + filepath);
    }


    public Set<String> getEachProjectData(String key) {
        Set<String> txt = pref.getStringSet(key, new HashSet<String>());
        Log.d(TAG, "getEachProjectData - MyPrefManager: " + txt);
        return txt;
    }

    public void setIsPersonDetailsDone(boolean isDone) {
        editor.putBoolean(KEY_DETAILS_DONE, isDone);
        editor.commit();
        Log.d(TAG, "setIsPersonDetailsDone - MyPrefManager: " + isDone);
    }


    private boolean getIsPersonDataCollectionDone() {
        boolean isDone = pref.getBoolean(KEY_DETAILS_DONE, false);
        Log.d(TAG, "getIsPersonDataCollectionDone - MyPrefManager: " + isDone);
        return isDone;
    }

    public boolean getIsAllDataCollectionDone(){
        return getIsPersonDataCollectionDone() && getIsPicDataCollectionDone();
    }


    public void setIsPicDataCollectionDone(boolean isDone){
        editor.putBoolean(KEY_PIC_DETAILS_DONE, isDone);
        editor.commit();
        Log.d(TAG, "setIsPicDataCollectionDone - MyPrefManager: " + isDone);
    }


    private boolean getIsPicDataCollectionDone(){
        boolean isDone = pref.getBoolean(KEY_PIC_DETAILS_DONE, false);
        Log.d(TAG, "getIsPicDataCollectionDone - MyPrefManager: " + isDone);
        return isDone;
    }


    public void setName(String txt) {
        editor.putString(KEY_NAME, txt);
        editor.commit();
        Log.d(TAG, "setName - MyPrefManager: " + txt);
    }

    public String getName() {
        String txt = pref.getString(KEY_NAME, null);
        Log.d(TAG, "getName - MyPrefManager: " + txt);
        return txt;
    }


    public void setEmail(String txt) {
        editor.putString(KEY_EMAIL, txt);
        editor.commit();
        Log.d(TAG, "setEmail - MyPrefManager: " + txt);
    }

    public String getEmail() {
        String txt = pref.getString(KEY_EMAIL, null);
        Log.d(TAG, "getEmail - MyPrefManager: " + txt);
        return txt;
    }


    public void setPhone(String txt) {
        editor.putString(KEY_PHONE, txt);
        editor.commit();
        Log.d(TAG, "setPhone - MyPrefManager: " + txt);
    }

    public String getPhone() {
        String txt = pref.getString(KEY_PHONE, null);
        Log.d(TAG, "getPhone - MyPrefManager: " + txt);
        return txt;
    }


    public void setCustId(String txt) {
        editor.putString(KEY_CUSTID, txt);
        editor.commit();
        Log.d(TAG, "setCustId - MyPrefManager: " + txt);
    }

    public String getCustId() {
        String txt = pref.getString(KEY_CUSTID, null);
        Log.d(TAG, "getCustId - MyPrefManager: " + txt);
        return txt;
    }


    /*public void setProjTitles(HashSet<String > txt){
        editor.putStringSet(KEY_PROJECT_TITLE, txt);
        editor.commit();
        Log.d(TAG, "setTitles - MyPrefManager: " + txt);
    }*/


    public String getCopyright() {
        String txt = pref.getString(KEY_COPYRIGHT, null);
        Log.d(TAG, "getCopyright - MyPrefManager: " + txt);
        return txt;
    }

    public void setCopyright(String txt) {
        editor.putString(KEY_COPYRIGHT, txt);
        editor.commit();
        Log.d(TAG, "setCopyright - MyPrefManager: " + txt);
    }

    public String getTitle() {
        String txt = pref.getString(KEY_TITLE, null);
        Log.d(TAG, "getTitle - MyPrefManager: " + txt);
        return txt;
    }

    public void setTitle(String title) {
        editor.putString(KEY_TITLE, title);
        editor.commit();
        Log.d(TAG, "setTitle - MyPrefManager: " + title);
    }

    public String getHDurl() {
        String txt = pref.getString(KEY_HD_URL, null);
        Log.d(TAG, "getHDurl - MyPrefManager: " + txt);
        return txt;
    }

    public void setHDurl(String url) {
        editor.putString(KEY_HD_URL, url);
        editor.commit();
        Log.d(TAG, "setTitle - MyPrefManager: " + url);
    }


    public String getNormalurl() {
        String txt = pref.getString(KEY_NORMAL_URL, null);
        Log.d(TAG, "getNormalurl - MyPrefManager: " + txt);
        return txt;
    }

    public void setNormalurl(String url) {
        editor.putString(KEY_NORMAL_URL, url);
        editor.commit();
        Log.d(TAG, "getNormalurl - MyPrefManager: " + url);
    }


    public String getExp() {
        String txt = pref.getString(KEY_EXP, null);
        Log.d(TAG, "getExp - MyPrefManager: " + txt);
        return txt;
    }

    public void setExp(String exp) {
        editor.putString(KEY_EXP, exp);
        editor.commit();
        Log.d(TAG, "getExp - MyPrefManager: " + exp);
    }


    public String getLastQueryDate() {
        String txt = pref.getString(KEY_LAST_QUERIED, null);
        Log.d(TAG, "setLastQueryDate - MyPrefManager: " + txt);
        return txt;
    }

    public void setLastQueryDate(String dateYYYYMMDD) {
        editor.putString(KEY_LAST_QUERIED, dateYYYYMMDD);
        editor.commit();
        Log.d(TAG, "setLastQueryDate - MyPrefManager: " + dateYYYYMMDD);
    }

    public boolean canQueryNasa() {
        String last = getLastQueryDate();
        if (last == null) {
            Log.d(TAG, "canQueryNasa - MyPrefManager: true");
            return true;
        }
        String now = DateTimeUtils.getFormattedTimestamp("yyyy-MM-dd", System.currentTimeMillis());
        Log.d(TAG, "storedDate - canQueryNasa: " + last);
        Log.d(TAG, "currentDateFromDevice - canQueryNasa: " + now);
        /*String[] lastarr = last.split("-");
        String[] nowArr = now.split("-");
        int i = 0;
        if (lastarr[0].trim().equals(nowArr[0].trim())){
            if (lastarr[1].trim().equals(nowArr[1].trim()))
                if (lastarr[2].trim().equals(nowArr[2].trim())) {
                    Log.d(TAG, "canQueryNasa - MyPrefManager: false");
                    return false;
                }
        }*/
        if (last.equals(now)) {
            Log.d(TAG, "canQueryNasa - MyPrefManager: false");
            return false;
        }
        Log.d(TAG, "canQueryNasa - MyPrefManager: true");
        return true;
    }
}
