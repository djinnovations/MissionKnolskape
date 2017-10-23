package dj.missionknolskape.main.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import dj.missionknolskape.main.R;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.uiutils.banes.HackyViewPager;
import dj.missionknolskape.main.utils.IntentKeys;
import uk.co.senab.photoview.PhotoView;

public class ProjectorViewActivity extends AppCompatActivity {

    private ImageView _ivNoYet;
    private ViewPager mViewPager;
    //LinkedHashSet<String> urlSet = new LinkedHashSet<>();
    ArrayList<String> urlArrayList = new ArrayList();
    ArrayList<File> fileArrayList = new ArrayList<>();
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projector_view);

        if (getIntent() != null) {
            urlArrayList = getIntent().getStringArrayListExtra(IntentKeys.PROJECTOR_IMAGE_URL);
            for (String path : getIntent().getStringArrayListExtra(IntentKeys.PROJECTOR_FILES)) {
                fileArrayList.add(new File(path));
            }
            title = getIntent().getStringExtra(IntentKeys.TITLE);
        }
        intializeViews();
        setUpImageList();
    }

    private void setUpImageList() {
        /*MyPreferenceManager mPref = MyApplication.getInstance().getPrefManager();
        ArrayList<String> temp = mPref.getUrlList();
        if (temp != null){
            urlSet.addAll(temp);
        }*/
        //urlArrayList.addAll(urlSet);
        if (urlArrayList != null) {
            if (urlArrayList.size() == 0) {
                displayNoImagesList();
            } else {
                mViewPager.setAdapter(new SamplePagerAdapter());
                mViewPager.setVisibility(View.VISIBLE);
                _ivNoYet.setVisibility(View.GONE);
            }
        } else if (fileArrayList != null) {
            if (fileArrayList.size() == 0) {
                displayNoImagesList();
            } else {
                mViewPager.setAdapter(new SamplePagerAdapter());
                mViewPager.setVisibility(View.VISIBLE);
                _ivNoYet.setVisibility(View.GONE);
            }
        } else displayNoImagesList();
    }


    private void displayNoImagesList() {
        Toast.makeText(getApplicationContext(), "Nothing to display!", Toast.LENGTH_SHORT).show();
        _ivNoYet.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
    }

    private void intializeViews() {
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        //setContentView(mViewPager);
        _ivNoYet = (ImageView) findViewById(R.id.ivNoFav);
        _ivNoYet.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ResourceReader.getInstance().getColorFromResource(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.vector_icon_cross_primary);
        getSupportActionBar().setTitle(title);
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


    class SamplePagerAdapter extends PagerAdapter {

        /*private final int[] sDrawables = {R.drawable.image_1, R.drawable.image_1, R.drawable.image_1,
                R.drawable.image_1, R.drawable.image_1, R.drawable.image_1};*/

        @Override
        public int getCount() {
            if (urlArrayList != null) {
                return urlArrayList.size();
            } else if (fileArrayList != null) {
                return fileArrayList.size();
            } else
                return 0;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            // photoView.setImageResource(sDrawables[position]);
            setImages(photoView, position);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setImages(ImageView imgView, int position) {
        /*final ImageView myImageView;
        if (recycled == null) {
            myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
        } else {
            myImageView = (ImageView) recycled;
        }*/
            String url = "";
            if (urlArrayList != null)
                url = urlArrayList.get(position);
            else if (fileArrayList != null)
                url = fileArrayList.get(position).getAbsolutePath();
            Log.d("dj", "ProjectorViewActivity - url, position: " + url + "  **&&&&&** " + position);
            /*Glide.with(ProjectorViewActivity.this)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.)
                    .crossFade()
                    .into(imgView);*/
            if (urlArrayList != null) {
                if (urlArrayList.size() > 0) {
                    if (!TextUtils.isEmpty(url))
                        Picasso.with(getApplicationContext())
                                .load(url)
                                .placeholder(R.drawable.vector_icon_progress_animation)
                                .into(imgView);
                }
            } else if (fileArrayList != null) {
                if (fileArrayList.size() > 0) {
                    if (!TextUtils.isEmpty(url))
                        Picasso.with(getApplicationContext())
                                .load(fileArrayList.get(position))
                                .placeholder(R.drawable.vector_icon_progress_animation)
                                .into(imgView);
                }
            }

        }

    }


}
