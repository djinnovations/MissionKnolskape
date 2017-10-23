package dj.missionknolskape.main.modulecore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.activities.MyApplication;
import dj.missionknolskape.main.uiutils.TypefaceHelper;

/**
 * Created by User on 23-10-2016.
 */
public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>{

    List<String> dataList = new ArrayList<>();
    public interface MenuSelectionListener{
        void onMenuSelected(String data);
    }

    protected MenuSelectionListener listener;
    public void changeData(List<String> thumbnailDataList){
        this.dataList = new ArrayList<>(thumbnailDataList);
        notifyDataSetChanged();
    }


    /*protected boolean showTitle(){
        return true;
    }*/

    List<Integer> colorList;
    public ThumbnailAdapter(MenuSelectionListener listener) {
        this.listener = listener;
        colorList = new ArrayList<>();
        //int[] colors = MyApplication.getInstance().getResources().getIntArray(R.array.imageBank);
        Integer[] temp = new Integer[]{R.drawable.img_tmb_1, R.drawable.img_tmb_2, R.drawable.img_tmb_3, R.drawable.img_tmb_4};
        /*int i = 0;
        for (int col : colors) {
            temp[i] = col;
            i++;
        }*/
        colorList.addAll(Arrays.asList(temp));
        Collections.shuffle(colorList);
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_thumbnail, parent, false);
        return new ThumbnailViewHolder(view);
    }

    //ThumbnailData previousSelection;
    int index = 0;

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.tvTitle.setText(data);
        if (index > 3)
            index = 0;
        //int rand = IDUtils.randInt(0, 2);
        if (true)
            Picasso.with(MyApplication.getInstance())
                    .load(colorList.get(index))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.ivThumbnail);
        index++;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected class ThumbnailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            TypefaceHelper.setFont(tvTitle);
        }

        @Override
        public void onClick(View v) {
            //// TODO: 25-10-2016
            if (listener != null)
            listener.onMenuSelected(dataList.get(getAdapterPosition()));
        }
    }
}
