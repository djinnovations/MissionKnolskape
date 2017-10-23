package dj.missionknolskape.main.modulecore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import dj.missionknolskape.main.activities.EachProjectPickerActivity;
import dj.missionknolskape.main.activities.ProjectDisplayActivity;
import dj.missionknolskape.main.modulecore.adapters.ThumbnailAdapter;
import dj.missionknolskape.main.utils.IntentKeys;

/**
 * Created by User on 27-10-2016.
 */
public class ProjectDisplayFragment extends CreateProjectFragment{


    protected ThumbnailAdapter.MenuSelectionListener listener;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener = new ThumbnailAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(String data) {
                //// TODO: 27-10-2016  Display popup here
                openEachProject(data);
            }
        };
        thumbnailAdapter = new ThumbnailAdapter(listener);
        rvThumbnail.setAdapter(thumbnailAdapter);
        displayProjects(((ProjectDisplayActivity) getActivity()).getTitles());
    }

    private void openEachProject(String data) {
        Intent intent = new Intent(getActivity(), EachProjectPickerActivity.class);
        intent.putExtra(IntentKeys.TITLE, data);
        startActivity(intent);
    }

    @Override
    protected boolean isShowAddLayout() {
        return false;
    }

    public void displayProjects(Set<String> projects){
        addData(new HashSet<>(projects));
    }

}
