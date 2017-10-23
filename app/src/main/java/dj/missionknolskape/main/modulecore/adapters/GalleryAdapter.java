package dj.missionknolskape.main.modulecore.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.activities.MyApplication;
import dj.missionknolskape.main.model.GalleryDataObject;
import dj.missionknolskape.main.uiutils.TypefaceHelper;

/**
 * Created by User on 30-10-2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    public interface ProjectSelectionListener {
        void onProjectSelected(GalleryDataObject dataObject);
    }

    private ProjectSelectionListener listener;

    public GalleryAdapter(ProjectSelectionListener listener) {
        this.listener = listener;
    }

    List<GalleryDataObject> dataObjectList = new ArrayList<>();

    public void changeData(List<GalleryDataObject> dataObjectList) {
        this.dataObjectList = new ArrayList<>(dataObjectList);
        notifyDataSetChanged();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photo_stack, parent, false));
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        List<String> paths = dataObjectList.get(position).getPaths();
        if (paths.size() >= 3) {
            holder.card_view1.setVisibility(View.VISIBLE);
            holder.card_view2.setVisibility(View.VISIBLE);
            holder.card_view3.setVisibility(View.VISIBLE);
            Picasso.with(MyApplication.getInstance())
                    .load(new File(paths.get(2)))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.iv3);
            Picasso.with(MyApplication.getInstance())
                    .load(new File(paths.get(1)))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.iv2);
            Picasso.with(MyApplication.getInstance())
                    .load(new File(paths.get(0)))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.iv1);
        } else if (paths.size() == 2) {
            holder.card_view1.setVisibility(View.VISIBLE);
            holder.card_view2.setVisibility(View.VISIBLE);
            holder.card_view3.setVisibility(View.GONE);
            Picasso.with(MyApplication.getInstance())
                    .load(new File(paths.get(1)))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.iv2);
            Picasso.with(MyApplication.getInstance())
                    .load(new File(paths.get(0)))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.iv1);
        } else if (paths.size() == 1) {
            holder.card_view1.setVisibility(View.VISIBLE);
            holder.card_view2.setVisibility(View.GONE);
            holder.card_view3.setVisibility(View.GONE);
            Picasso.with(MyApplication.getInstance())
                    .load(new File(paths.get(0)))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.iv1);
        } else {
            holder.card_view1.setVisibility(View.GONE);
            holder.card_view2.setVisibility(View.GONE);
            holder.card_view3.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(dataObjectList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataObjectList.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.card_view1)
        CardView card_view1;
        @Bind(R.id.card_view2)
        CardView card_view2;
        @Bind(R.id.card_view3)
        CardView card_view3;
        @Bind(R.id.iv1)
        ImageView iv1;
        @Bind(R.id.iv2)
        ImageView iv2;
        @Bind(R.id.iv3)
        ImageView iv3;
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            TypefaceHelper.setFont(tvTitle);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onProjectSelected(dataObjectList.get(getAdapterPosition()));
        }
    }
}
