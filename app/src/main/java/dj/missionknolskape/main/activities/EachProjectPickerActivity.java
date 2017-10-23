package dj.missionknolskape.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.modulecore.adapters.EachPicAdapter;
import dj.missionknolskape.main.modulecore.adapters.ThumbnailAdapter;
import dj.missionknolskape.main.modulecore.imagepicker.ImageSelector;
import dj.missionknolskape.main.uiutils.ColoredSnackbar;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.utils.IntentKeys;
import dj.missionknolskape.main.utils.MyPrefManager;

/**
 * Created by User on 27-10-2016.
 */
public class EachProjectPickerActivity extends BaseActivity implements ImageSelector.RegisterImageUploadCallBack {


    ImageSelector imageSelector;
    @Bind(R.id.rvThumbnail)
    RecyclerView rvThumbnail;
    @Bind(R.id.previewImage)
    ImageView previewImage;
    @Bind(R.id.trigger)
    ImageButton trigger;
    @Bind(R.id.ivAddImg)
    ImageView ivAddImg;
    @Bind(R.id.btnSave)
    Button btnSave;
    EachPicAdapter eachPicAdapter;
    private String title;

    public void optionImagePicker() {

    }


    boolean canExit = false;
    View.OnClickListener saveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Set<String> paths = new HashSet<>();
            for (File file: fileListMain){
                paths.add(file.getAbsolutePath());
            }
            MyPrefManager.getInstance().setEachProjectData(title, paths);
            Toast.makeText(getApplicationContext(), "Project Saved", Toast.LENGTH_SHORT).show();
            MyPrefManager.getInstance().setIsPicDataCollectionDone(true);
            canExit = true;
        }
    };

    @Override
    public void onBackPressed() {
        if (canExit)
            super.onBackPressed();
        else {
            final Snackbar snackbar = Snackbar.make(btnSave, "Changes not saved", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(ResourceReader.getInstance().getColorFromResource(R.color.colorWhite));
            snackbar.setAction("Back", new View.OnClickListener() {
                public void onClick(View v) {
                    snackbar.dismiss();
                    finish();
                }
            });
            ColoredSnackbar.alert(snackbar).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_project_picker);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            title = getIntent().getStringExtra(IntentKeys.TITLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(ResourceReader.getInstance().getColorFromResource(R.color.colorPrimary));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.vector_icon_cross_primary);
            getSupportActionBar().setTitle(title);
        }
        btnSave.setOnClickListener(saveClick);
        this.imageSelector = new ImageSelector(this, this, previewImage, trigger, ivAddImg);
        menuSelectionListener = new ThumbnailAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(String data) {
                pickClick(data);
            }
        };
        setUpRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            //finish();
        }
        return super.onOptionsItemSelected(item);

    }


    private String filePathMain;

    private void pickClick(String data) {
        Toast.makeText(getApplicationContext(), "Now click on the Edit icon", Toast.LENGTH_SHORT).show();
        filePathMain = data;
        imageSelector.showPreviewForEdit(data);
    }

    ThumbnailAdapter.MenuSelectionListener menuSelectionListener;

    private void setUpRecyclerView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvThumbnail.setHasFixedSize(false);
        rvThumbnail.setLayoutManager(mLayoutManager);
        rvThumbnail.setItemAnimator(new DefaultItemAnimator());
        eachPicAdapter = new EachPicAdapter(menuSelectionListener);
        rvThumbnail.setAdapter(eachPicAdapter);
    }

    Set<File> fileList = new TreeSet<>();
    Set<File> fileListMain = new TreeSet<>();

    public void updateDataList(List<File> list) {
        Toast.makeText(getApplicationContext(), "Click on any thumbnail to edit", Toast.LENGTH_SHORT).show();
        fileList = new TreeSet<>(list);
        fileListMain.addAll(fileList);
        canExit = false;
        eachPicAdapter.changeDataFiles(fileListMain);
    }

    public void updateSingleFile(String filePath) {
        File tempfile = null;
        for (File file : fileListMain) {
            if (filePathMain.equals(file.getAbsolutePath())){
                tempfile = file;
                break;
            }
        }
        if (fileListMain.remove(tempfile))
            fileListMain.add(new File(filePath));
        eachPicAdapter.changeDataFiles(fileListMain);
        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerImageUploadCallBack(ImageSelector imageSelector) {
        this.imageSelector = imageSelector;
    }

    @Override
    public void unRegisterImageUploadCallBack(ImageSelector imageSelector) {
        /*if (imageSelector == this.imageSelector)
            this.imageSelector = null;*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageSelector.onActivityResult(requestCode, resultCode, data);
    }
}
