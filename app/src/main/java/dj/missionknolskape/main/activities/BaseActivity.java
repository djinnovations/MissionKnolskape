package dj.missionknolskape.main.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class BaseActivity extends AppCompatActivity {

    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }


    protected void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    protected void startProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    protected void stopProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }


    protected AjaxCallback getAjaxCallback(final int id) {
        AjaxCallback<Object> ajaxCallback = new AjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status) {
                serverCallEnds(id, url, json, status);
            }
        };
        /*List<Cookie> cookies = getApp().getCookies();
        if (cookies != null && cookies.size() != 0) {
            for (Cookie cookie : cookies) {
                ajaxCallback.cookie(cookie.getName(), cookie.getValue());
            }
        }*/
        return ajaxCallback;
    }


    private AQuery aQuery;

    protected AQuery getAQuery() {
        if (aQuery == null)
            aQuery = new AQuery(this);
        return aQuery;
    }

    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {

    }

}
