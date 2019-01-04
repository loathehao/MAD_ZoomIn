package mad.android.com.mad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mad.android.com.mad.R;
import mad.android.com.mad.bean.MoviesBean;
//电影图片视频适配器
public class PhotosVideosAdapter extends RecyclerView.Adapter<PhotosVideosAdapter.ViewHolder> {

    private List<MoviesBean.SubjectsBean> objects = new ArrayList<MoviesBean.SubjectsBean>();
    private Context context;

    public PhotosVideosAdapter(Context context) {
        this.context=context;
    }
    public void setData(List<MoviesBean.SubjectsBean> objects){
        this.objects = objects;
    }

    @Override
    public PhotosVideosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_photos_videos, parent, false);
        return new PhotosVideosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosVideosAdapter.ViewHolder holder, int position) {
        final MoviesBean.SubjectsBean bean=objects.get(position);
        if (bean==null){
            return;
        }
        holder.tvPhotosVideos.setText("照片/预告片");

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPhotosVideos;
        private TextView tvPhotosVideos;

        public ViewHolder(View view) {
            super(view);
            ivPhotosVideos = view.findViewById(R.id.iv_photos_videos);
            tvPhotosVideos = view.findViewById(R.id.tv_photos_videos);

        }
    }
}

