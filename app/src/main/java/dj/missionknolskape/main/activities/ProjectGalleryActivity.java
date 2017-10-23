package dj.missionknolskape.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.model.GalleryDataObject;
import dj.missionknolskape.main.modulecore.adapters.GalleryAdapter;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.utils.IntentKeys;
import dj.missionknolskape.main.utils.MyPrefManager;

/**
 * Created by User on 30-10-2016.
 */
public class ProjectGalleryActivity extends BaseActivity{

    @Bind(R.id.rvGallery)
    RecyclerView rvGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_projects);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ResourceReader.getInstance().getColorFromResource(R.color.colorPrimary));
        getSupportActionBar().setTitle("Projects Gallery");
        setUpRecycleView();
        prepareData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private GalleryAdapter.ProjectSelectionListener listener;

    private GalleryAdapter galleryAdapter;
    private void setUpRecycleView() {
        listener = new GalleryAdapter.ProjectSelectionListener() {
            @Override
            public void onProjectSelected(GalleryDataObject dataObject) {
                startProjector(dataObject);
            }
        };
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvGallery.setHasFixedSize(false);
        rvGallery.setLayoutManager(mLayoutManager);
        rvGallery.setItemAnimator(new DefaultItemAnimator());
        galleryAdapter = new GalleryAdapter(listener);
        rvGallery.setAdapter(galleryAdapter);
    }

    private void startProjector(GalleryDataObject dataObject) {
        ArrayList<String> list = new ArrayList<>(dataObject.getPaths());
        Intent intent = new Intent(this, ProjectorViewActivity.class);
        intent.putExtra(IntentKeys.TITLE, dataObject.getTitle());
        intent.putStringArrayListExtra(IntentKeys.PROJECTOR_FILES, list);
        startActivity(intent);
    }

    public void prepareData(){
        List<GalleryDataObject> list = new ArrayList<>();
        for (String title: MyPrefManager.getInstance().getProjectNames()){
            GalleryDataObject dataObject = new GalleryDataObject(new ArrayList<>(MyPrefManager.getInstance()
                    .getEachProjectData(title)), title);
            list.add(dataObject);
        }
        galleryAdapter.changeData(list);
    }
}
