package dj.missionknolskape.main.activities;

import android.app.Application;

/**
 * Created by DJphy on 28-09-2016.
 */
public class MyApplication extends Application {

    private static MyApplication ourInstance;
    public static MyApplication getInstance(){
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
    }
}
