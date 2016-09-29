package dj.missionknolskape.main.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.model.ApodResponse;
import dj.missionknolskape.main.uiutils.TypefaceHelper;
import dj.missionknolskape.main.utils.DateTimeUtils;
import dj.missionknolskape.main.utils.IDUtils;
import dj.missionknolskape.main.utils.MyPrefManager;
import dj.missionknolskape.main.utils.NetworkResultValidator;
import dj.missionknolskape.main.utils.URLHelper;

/**
 * Created by User on 28-09-2016.
 */
public abstract class AbstractApodActivity extends BaseActivity{

    public final String TAG = "AbstractApodActivity";

    abstract protected int getMainLayoutResID();

    abstract protected Map<String, Object> getReqParams();

    abstract protected void onBaseViewCreated(View view);

    //abstract protected String queryUrl();

    abstract protected Class getParsingAwareClass();

    abstract protected void updateView(View rootView, Object parsingAwareClass);

    /*@Bind(R.id.ivHeader)
    ImageView ivHeader;*/
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainLayoutResID());
        ButterKnife.bind(this);
        rootView = LayoutInflater.from(this).inflate(getMainLayoutResID(), null);
        setCustomTitle();
        onBaseViewCreated(rootView);
        checkToQueryAndProceed();
    }

    protected MyPrefManager prefManager;
    private void checkToQueryAndProceed(){
        prefManager = MyPrefManager.getInstance();
        if (prefManager.canQueryNasa()){
            queryForApod();
        }
        else {
            Class parsingClass = getParsingAwareClass();
            if(parsingClass.isAssignableFrom(ApodResponse.class)){
                ApodResponse response = new ApodResponse();
                response.setDate(prefManager.getLastQueryDate());
                response.setTitle(prefManager.getTitle());
                response.setExplanation(prefManager.getExp());
                response.setUrl(prefManager.getNormalurl());
                response.setHdurl(prefManager.getHDurl());
                response.setCopyright(prefManager.getCopyright());
                bindView(rootView, response);
            }
        }
    }


    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    protected void setCustomTitle() {
        try {
            Toolbar tb = (Toolbar) rootView.findViewById(R.id.toolbar);
            setSupportActionBar(tb);
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedText);
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedText);
            collapsingToolbar.setExpandedTitleTypeface(TypefaceHelper.getTypeFace(MyApplication.getInstance(), "din-regular.ttf"));
            collapsingToolbar.setCollapsedTitleTypeface(TypefaceHelper.getTypeFace(MyApplication.getInstance(), "din-regular.ttf"));
            collapsingToolbar.setTitle("Astronomy Picture Of the Day");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int APOD_CALL = IDUtils.generateViewId();

    private void queryForApod(){
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(APOD_CALL);
        ajaxCallback.method(AQuery.METHOD_GET);
        StringBuilder urlBuilder = new StringBuilder();
        Map<String, Object> map = getReqParams();
        urlBuilder.append(URLHelper.getApodAPI())
        .append("api_key").append("=").append(URLHelper.API_KEY_NASA);
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            if (it.hasNext())
                urlBuilder.append("&");
            while (it.hasNext()) {
                Map.Entry<String, String> pair = (Map.Entry) it.next();
                urlBuilder.append(pair.getKey()).append("=").append(pair.getValue());
                if (it.hasNext())
                    urlBuilder.append("&");
            }
        }
        Log.d(TAG, "GET url "+TAG+": "+urlBuilder.toString());
        getAQuery().ajax(urlBuilder.toString().trim(), String.class, ajaxCallback);
    }

    @Override
    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-"+TAG+": " + url);
        Log.d(TAG, "response-"+TAG+": " + json);
        stopProgress();
        if (id == APOD_CALL){
            boolean success = NetworkResultValidator.getInstance().isResultOK(url, (String) json, status, null,
                    ivHeader, this);
            if (success) {
                Gson gson = new Gson();
                Object parsingAwareClass = gson.fromJson((String) json, getParsingAwareClass());
                if (parsingAwareClass instanceof ApodResponse) {
                    ApodResponse response = (ApodResponse) parsingAwareClass;
                    prefManager.setTitle(response.getTitle());
                    prefManager.setExp(response.getExplanation());
                    prefManager.setHDurl(response.getHdurl());
                    prefManager.setLastQueryDate(DateTimeUtils.getFormattedTimestamp("yyyy-MM-dd", System.currentTimeMillis()));
                    prefManager.setNormalurl(response.getUrl());
                    prefManager.setCopyright(response.getCopyright());
                }
                bindView(rootView, parsingAwareClass);
            }
        }else
        super.serverCallEnds(id, url, json, status);
    }

    @Bind(R.id.ivHeader)
    ImageView ivHeader;

    private void bindView(View view, Object parsingAwareClass){
        if (parsingAwareClass instanceof ApodResponse){
            ApodResponse response = (ApodResponse) parsingAwareClass;
            collapsingToolbar.setTitle(response.getTitle());
            if (!TextUtils.isEmpty(response.getUrl()))
                Picasso.with(getApplicationContext())
                        .load(response.getUrl())
                        .placeholder(R.drawable.vector_icon_progress_animation_white)
                        .into(ivHeader);
        }
        updateView(view, parsingAwareClass);
    }
}
