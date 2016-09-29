package dj.missionknolskape.main.utils;

import android.content.SharedPreferences;
import android.util.Log;

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

    public String getCopyright(){
        String txt = pref.getString(KEY_COPYRIGHT, null);
        Log.d(TAG, "getCopyright - MyPrefManager: " + txt);
        return txt;
    }

    public void setCopyright(String txt){
        editor.putString(KEY_COPYRIGHT, txt);
        editor.commit();
        Log.d(TAG, "setCopyright - MyPrefManager: " + txt);
    }

    public String getTitle(){
        String txt = pref.getString(KEY_TITLE, null);
        Log.d(TAG, "getTitle - MyPrefManager: " + txt);
        return txt;
    }

    public void setTitle(String title){
        editor.putString(KEY_TITLE, title);
        editor.commit();
        Log.d(TAG, "setTitle - MyPrefManager: " + title);
    }

    public String getHDurl(){
        String txt = pref.getString(KEY_HD_URL, null);
        Log.d(TAG, "getHDurl - MyPrefManager: " + txt);
        return txt;
    }

    public void setHDurl(String url){
        editor.putString(KEY_HD_URL, url);
        editor.commit();
        Log.d(TAG, "setTitle - MyPrefManager: " + url);
    }


    public String getNormalurl(){
        String txt = pref.getString(KEY_NORMAL_URL, null);
        Log.d(TAG, "getNormalurl - MyPrefManager: " + txt);
        return txt;
    }

    public void setNormalurl(String url){
        editor.putString(KEY_NORMAL_URL, url);
        editor.commit();
        Log.d(TAG, "getNormalurl - MyPrefManager: " + url);
    }


    public String getExp(){
        String txt = pref.getString(KEY_EXP, null);
        Log.d(TAG, "getExp - MyPrefManager: " + txt);
        return txt;
    }

    public void setExp(String exp){
        editor.putString(KEY_EXP, exp);
        editor.commit();
        Log.d(TAG, "getExp - MyPrefManager: " + exp);
    }


    public String getLastQueryDate(){
        String txt = pref.getString(KEY_LAST_QUERIED, null);
        Log.d(TAG, "setLastQueryDate - MyPrefManager: " + txt);
        return txt;
    }

    public void setLastQueryDate(String dateYYYYMMDD){
        editor.putString(KEY_LAST_QUERIED, dateYYYYMMDD);
        editor.commit();
        Log.d(TAG, "setLastQueryDate - MyPrefManager: " + dateYYYYMMDD);
    }

    public boolean canQueryNasa(){
        String last = getLastQueryDate();
        if (last == null) {
            Log.d(TAG, "canQueryNasa - MyPrefManager: true");
            return true;
        }
        String now = DateTimeUtils.getFormattedTimestamp("yyyy-MM-dd", System.currentTimeMillis());
        Log.d(TAG, "storedDate - canQueryNasa: "+last);
        Log.d(TAG, "currentDateFromDevice - canQueryNasa: "+now);
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
        if (last.equals(now)){
            Log.d(TAG, "canQueryNasa - MyPrefManager: false");
            return false;
        }
        Log.d(TAG, "canQueryNasa - MyPrefManager: true");
        return true;
    }
}
