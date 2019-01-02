package mad.android.com.mad.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import mad.android.com.mad.R;
import mad.android.com.mad.beans.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> objects = new ArrayList<>();

    public MovieAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Movie> objects){
        this.objects = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Movie bean = objects.get(position);

        final BmobQuery<Movie> movieBmobQuery = new BmobQuery<>();
        movieBmobQuery.findObjects(new FindListener<Movie>() {

            @Override
            public void done(List<Movie> list, BmobException e) {
                holder.item_movie_name.setText(bean.getTitle());
                holder.item_movie_introduce.setText(bean.getBrief());
                Glide.with(context).load(objects.get(position).getPosterUrl()).into(((ViewHolder) holder).item_movie_pic);

            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView info_recommend1;
        ImageView item_movie_pic;
        TextView item_movie_name;
        TextView item_movie_introduce;

        public ViewHolder(View view){
            super(view);
            info_recommend1 = view.findViewById(R.id.info_recommend1);
            item_movie_pic = view.findViewById(R.id.item_movie_pic);
            item_movie_name = view.findViewById(R.id.item_movie_name);
            item_movie_introduce = view.findViewById(R.id.item_movie_introduce);
        }

    }
}
