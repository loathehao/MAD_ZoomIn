package mad.android.com.mad.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import mad.android.com.mad.R;
import mad.android.com.mad.bean.Movie;
import mad.android.com.mad.bean.MoviesBean;
import mad.android.com.mad.view.MovieInfo;
//最新热映电影展示适配器
public class ItemMovieOnAdapter extends RecyclerView.Adapter<ItemMovieOnAdapter.ViewHolder> {

    private List<MoviesBean.SubjectsBean> objects = new ArrayList<MoviesBean.SubjectsBean>();

    private Context context;

    public ItemMovieOnAdapter(Context context) {

        this.context = context;
    }
    public void setData(List<MoviesBean.SubjectsBean> objects){
        this.objects = objects;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_movie_on, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MoviesBean.SubjectsBean bean=objects.get(position);
        if (bean==null){
            return;
        }
        Glide.with(context)
                .load(bean.getImages().getSmall())
                .into(holder.ivMovieOn);
        holder.tvMovieOnTitle.setText(bean.getTitle());
        String directors="";
        for(int i=0;i<bean.getDirectors().size();i++){
            if (i==bean.getDirectors().size()-1){
                directors+=bean.getDirectors().get(i).getName();
            }else{
                directors+=bean.getDirectors().get(i).getName()+"/";
            }
        }
        holder.tvMovieOnDirectors.setText("导演："+directors);
        String casts="";

        if (bean.getCasts().size()!=0){
            for(int i=0;i<bean.getCasts().size();i++){
                if (i==bean.getCasts().size()-1){
                    casts+=bean.getCasts().get(i).getName();
                }else{
                    casts+=bean.getCasts().get(i).getName()+"/";
                }
            }
            holder.tvMovieOnCasts.setText("主演："+casts);
        }else {
            holder.tvMovieOnCasts.setText("主演：佚名");
        }

        String gen="";


                gen+=bean.getGenres().get(0);

        final Movie movieItem = new Movie();
        String objID=idQuery(bean,movieItem);
        movieItem.setTitle(bean.getTitle());
        movieItem.setGenres(gen);
        movieItem.setRating(bean.getRating().getAverage());
        movieItem.setBrief(bean.getAlt());
        movieItem.setCasts(casts);
        movieItem.setDate(bean.getYear());
        movieItem.setDirectors(directors);
        movieItem.setLength("130");
        movieItem.setPosterUrl(bean.getImages().getMedium());
        movieItem.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //Toast.makeText(ItemMovieOnAdapter.this,"添加数据成功，返回objectId为：" + objectId,Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(MainActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.tvMovieOnGenres.setText("类型："+gen);
        holder.tvMovieOnRating.setText("评分："+movieItem.getRating());
        holder.rlMovieOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieInfo.class);
                intent.putExtra("poster_url",bean.getImages().getMedium());
                intent.putExtra("title", holder.tvMovieOnTitle.getText());
                intent.putExtra("genres", holder.tvMovieOnGenres.getText());
                intent.putExtra("date", bean.getYear());
                intent.putExtra("rating", holder.tvMovieOnRating.getText());
                intent.putExtra("id", holder.tvMovieOnRating.getText());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMovieOn;
        private TextView tvMovieOnTitle;
        private TextView tvMovieOnDirectors;
        private TextView tvMovieOnCasts;
        private TextView tvMovieOnGenres;
        private TextView tvMovieOnRating;
        private RelativeLayout rlMovieOn;
        private CardView cv_movies;

        public ViewHolder(View view) {
            super(view);
            ivMovieOn = view.findViewById(R.id.iv_movie_on);
            tvMovieOnTitle = view.findViewById(R.id.tv_movie_on_title);
            tvMovieOnDirectors = view.findViewById(R.id.tv_movie_on_directors);
            tvMovieOnCasts = view.findViewById(R.id.tv_movie_on_casts);
            tvMovieOnGenres = view.findViewById(R.id.tv_movie_on_genres);
            tvMovieOnRating = view.findViewById(R.id.tv_movie_on_rating);
            rlMovieOn = view.findViewById(R.id.rl_movie_on);
            cv_movies = view.findViewById(R.id.cv_movies);
        }
    }

    public String idQuery(MoviesBean.SubjectsBean bean,final Movie movieItem) {
        final String objId="";
        BmobQuery<Movie> movieQuery = new BmobQuery<Movie>();
        movieQuery.addWhereEqualTo("title",bean.getTitle());
        movieQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> object, BmobException e) {
                Log.e("BMOB", object.get(0).getObjectId());
                if (e == null) {
                    final String objId = object.get(0).getObjectId();
                    Log.e("BMOB", "what:" + objId);
                    //Snackbar.make(FgMovieInfo.this, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return objId;
    }
}