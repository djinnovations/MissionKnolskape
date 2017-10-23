package dj.missionknolskape.main.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.modulecore.fragments.ProjectDisplayFragment;
import dj.missionknolskape.main.uiutils.ColoredSnackbar;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.utils.MyPrefManager;

/**
 * Created by User on 30-10-2016.
 */
public class ProjectDisplayActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_display);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ResourceReader.getInstance().getColorFromResource(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.vector_icon_cross_primary);
        getSupportActionBar().setTitle("All Projects");

        getSupportFragmentManager().beginTransaction().replace(container.getId(), new ProjectDisplayFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        final Snackbar snackbar = Snackbar.make(container, "Project will be lost if not saved", Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ResourceReader.getInstance().getColorFromResource(R.color.colorWhite));
        snackbar.setAction("Exit", new View.OnClickListener() {
            public void onClick(View v) {
                snackbar.dismiss();
                finish();
            }
        });
        ColoredSnackbar.alert(snackbar).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            //finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public Set<String> getTitles() {
        return MyPrefManager.getInstance().getProjectNames();
    }
}
