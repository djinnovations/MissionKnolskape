package dj.missionknolskape.main.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.model.ApodResponse;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.uiutils.TypefaceHelper;
import dj.missionknolskape.main.utils.IntentKeys;

public class MainActivity extends AbstractApodActivity {

    @Override
    protected int getMainLayoutResID() {
        return R.layout.activity_topic_astronomy;
    }

    @Override
    protected Map<String, Object> getReqParams() {
        return null;
    }

    @Bind(R.id.tvExp)
    TextView tvExp;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.tvCopyRight)
    TextView tvCopyRight;

    @Override
    protected void onBaseViewCreated(View view) {
        setProgressBar(progressBar);
        TypefaceHelper.setFont(tvExp, tvCopyRight);
        fab.setImageDrawable(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_hd)
                .color(ResourceReader.getInstance().getColorFromResource(R.color.colorAccent))
                .sizeDp(25));
        fab.setOnClickListener(fabClick);
    }

    View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String hdurl = prefManager.getHDurl();
            //if (!TextUtils.isEmpty(hdurl)) {
            ArrayList<String> list = new ArrayList<>();
            list.add(hdurl);
            Intent intent = new Intent(MainActivity.this, ProjectorViewActivity.class);
            intent.putExtra(IntentKeys.TITLE, prefManager.getTitle());
            intent.putStringArrayListExtra(IntentKeys.PROJECTOR_VIEW_IMAGES_LIST, list);
            startActivity(intent);
            //}
        }
    };

    @Override
    protected Class getParsingAwareClass() {
        return ApodResponse.class;
    }

    @Override
    protected void updateView(View rootView, Object parsingAwareClass) {
        if (parsingAwareClass == null)
            return;
        ApodResponse response = (ApodResponse) parsingAwareClass;
        tvExp.setText(response.getExplanation());
        tvCopyRight.setVisibility(View.VISIBLE);
        tvCopyRight.setText("Copyright: " + response.getCopyright());
    }


}
