package dj.missionknolskape.main.modulecore.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.modulecore.adapters.ThumbnailAdapter;
import dj.missionknolskape.main.uiutils.ResourceReader;
import dj.missionknolskape.main.uiutils.TypefaceHelper;
import dj.missionknolskape.main.utils.MyPrefManager;

/**
 * Created by User on 25-10-2016.
 */
public class CreateProjectFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_projects, container, false);
    }

    @Bind(R.id.llAddHolder)
    View llAddHolder;
    @Bind(R.id.etProjName)
    EditText etProjName;
    @Bind(R.id.add)
    ImageView btnAdd;
    @Bind(R.id.llBtn)
    View llBtn;

    @OnClick(R.id.llBtn)
    void onAddClick() {
        YoYo.with(Techniques.Landing).duration(300).playOn(btnAdd);
        String name = etProjName.getText().toString().trim();
        if (name.length() <= 0) {
            YoYo.with(Techniques.FadeIn).duration(300).playOn(etProjName);
            return;
        }
        //// TODO: 25-10-2016
        boolean isAdded = dataList.add(name);
        if (!isAdded) {
            Toast.makeText(getActivity(), "Project Already Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        addData(dataList);
        index++;
    }

    int index = 0;

    HashSet<String> dataList = new HashSet<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        TypefaceHelper.setFont(etProjName);

        /*listener = new ThumbnailAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(String data) {

            }
        };*/
        changeDrawable(btnAdd, GoogleMaterial.Icon.gmd_add_circle);
        if (isShowAddLayout())
            llAddHolder.setVisibility(View.VISIBLE);
        else llAddHolder.setVisibility(View.GONE);
        setUpRecycleView();
    }

    private void changeDrawable(ImageView imageView, IIcon icon) {
        imageView.setImageDrawable(new IconicsDrawable(getActivity())
                .icon(icon)
                .color(ResourceReader.getInstance().getColorFromResource(R.color.colorWhite))
                .sizeDp(20));
    }

    protected boolean isShowAddLayout() {
        return true;
    }

    @Bind(R.id.rvThumbnail)
    RecyclerView rvThumbnail;
    protected ThumbnailAdapter thumbnailAdapter;


    //protected ThumbnailAdapter.MenuSelectionListener listener;

    private void setUpRecycleView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvThumbnail.setHasFixedSize(false);
        rvThumbnail.setLayoutManager(mLayoutManager);
        rvThumbnail.setItemAnimator(new DefaultItemAnimator());
        thumbnailAdapter = new ThumbnailAdapter(null);
        rvThumbnail.setAdapter(thumbnailAdapter);
    }

    public void addData(HashSet<String> data) {
        thumbnailAdapter.changeData(new ArrayList<>(data));
    }


    public void saveProjects() {
        MyPrefManager.getInstance().setAllProjectNames(dataList);
        MyPrefManager.getInstance().setIsPersonDetailsDone(true);
    }
}
