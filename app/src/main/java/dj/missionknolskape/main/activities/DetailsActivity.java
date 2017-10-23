package dj.missionknolskape.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.modulecore.fragments.CreateProjectFragment;
import dj.missionknolskape.main.modulecore.fragments.PersonDetailFragment;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.utils.MyPrefManager;

/**
 * Created by User on 25-10-2016.
 */
public class DetailsActivity extends BaseActivity {

    @Bind(R.id.fabNext)
    FloatingActionButton fabNext;
    @Bind(R.id.fabPrev)
    FloatingActionButton fabPrev;
    @Bind(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyPrefManager.getInstance().getIsAllDataCollectionDone()){
            Toast.makeText(getApplicationContext(), "Hi "+MyPrefManager.getInstance().getName()+", Welcome", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 300);
            startActivity(new Intent(this, ProjectGalleryActivity.class));
            return;
        }

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        /*fabNext.setImageDrawable(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_arrow_forward)
                .color(ResourceReader.getInstance().getColorFromResource(R.color.colorAccent))
                .sizeDp(25));*/
        fabPrev.setVisibility(View.GONE);
        changeDrawable(fabNext, GoogleMaterial.Icon.gmd_arrow_forward);
        changeDrawable(fabPrev, GoogleMaterial.Icon.gmd_arrow_back);
        fabNext.setOnClickListener(fabClick);
        fabPrev.setOnClickListener(prevFabClick);
        //fabNext.performClick();
        changeDrawable(fabNext, GoogleMaterial.Icon.gmd_arrow_forward);
        PersonDetailFragment fragment = personDetailFragment = new PersonDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_into_left)
                .replace(container.getId(), fragment).commit();
        currentPage = 2;
    }


    private void changeDrawable(FloatingActionButton fab, IIcon icon) {
        fab.setImageDrawable(new IconicsDrawable(this)
                .icon(icon)
                .color(ResourceReader.getInstance().getColorFromResource(R.color.colorAccent))
                .sizeDp(25));
    }

    int currentPage = 0;
    PersonDetailFragment personDetailFragment;
    CreateProjectFragment createProjectFragment;

    private View.OnClickListener prevFabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fabPrev.getVisibility() == View.VISIBLE) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.SlideOutDown).duration(300).playOn(fabPrev);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fabPrev.setVisibility(View.GONE);
                            }
                        }, 300);
                    }
                }, 100);
            } else fabPrev.setVisibility(View.GONE);
            changeDrawable(fabNext, GoogleMaterial.Icon.gmd_arrow_forward);
            PersonDetailFragment fragment;
            if (personDetailFragment == null)
                fragment = personDetailFragment = new PersonDetailFragment();
            else
                fragment = personDetailFragment;
            if (createProjectFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .hide(createProjectFragment).commit();
            }
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_into_left)
                    .show(fragment).commit();
            currentPage = 2;
        }
    };


    View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentPage == 2) {
                if (!personDetailFragment.canSaveAndProceed())
                    return;
                changeDrawable(fabNext, GoogleMaterial.Icon.gmd_check);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabPrev.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInUp).duration(300).playOn(fabPrev);
                    }
                }, 100);
                CreateProjectFragment fragment;
                if (createProjectFragment == null) {
                    fragment = createProjectFragment = new CreateProjectFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_into_left)
                            .add(container.getId(), fragment).commit();
                }
                else {
                    fragment = createProjectFragment;
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_into_left)
                                .show(fragment).commit();
                }

                if (personDetailFragment != null)
                    getSupportFragmentManager().beginTransaction()
                            .hide(personDetailFragment).commit();
                currentPage = 3;

            } else if (currentPage == 3) {
                createProjectFragment.saveProjects();
                Toast.makeText(getApplicationContext(), "Project Names Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailsActivity.this, ProjectDisplayActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);

            }
        }
    };
}
