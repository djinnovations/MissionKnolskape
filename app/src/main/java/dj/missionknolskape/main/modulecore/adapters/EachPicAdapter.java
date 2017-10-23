package dj.missionknolskape.main.modulecore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dj.missionknolskape.main.R;
import dj.missionknolskape.main.activities.MyApplication;

/**
 * Created by User on 28-10-2016.
 */
public class EachPicAdapter extends ThumbnailAdapter{

    List<File> uriList = new ArrayList<>();

    public EachPicAdapter(MenuSelectionListener listener) {
        super(listener);
    }

    public void changeDataFiles(Set<File> uriList) {
        this.uriList = new ArrayList<>(uriList);
        this.notifyDataSetChanged();
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_thumbnail, parent, false));
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    /*@Override
    protected boolean showTitle() {
        return false;
    }*/

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position);
        Picasso.with(MyApplication.getInstance())
                .load(uriList.get(position))
                .placeholder(R.drawable.vector_icon_progress_animation)
                .into(holder.ivThumbnail);
    }


    class PicHolder extends ThumbnailViewHolder implements View.OnClickListener{

        public PicHolder(View itemView) {
            super(itemView);
            tvTitle.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            //super.onClick(v);
            listener.onMenuSelected(uriList.get(getAdapterPosition()).getAbsolutePath());
        }
    }
}
