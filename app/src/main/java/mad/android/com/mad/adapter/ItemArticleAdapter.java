package mad.android.com.mad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mad.android.com.mad.R;
import mad.android.com.mad.bean.MoviesBean;
//电影展示适配器
public class ItemArticleAdapter extends RecyclerView.Adapter<ItemArticleAdapter.ViewHolder>  {
    private List<MoviesBean.SubjectsBean> objects = new ArrayList<MoviesBean.SubjectsBean>();

    private Context context;

    public ItemArticleAdapter(Context context) {
        this.context=context;
    }
    public void setData(List<MoviesBean.SubjectsBean> objects){
        this.objects = objects;
    }

    @Override
    public ItemArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ItemArticleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemArticleAdapter.ViewHolder holder, int position) {
        final MoviesBean.SubjectsBean bean=objects.get(position);
        if (bean==null){
            return;
        }
        Glide.with(context)
                .load(bean.getImages().getSmall())
                .into(holder.ivArticleMoviePoster);
        holder.tvArticleMovieTitle.setText(bean.getTitle());
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
        private ImageView ivArticleMoviePoster;
        private ImageView ivArticleUserPhoto;
        private TextView tvArticleUserName;
        private TextView tvArticleMovieTitle;
        private TextView tvArticleDate;
        private TextView articleRating;
        private TextView tvArticleTitle;
        private TextView tvArticleContent;
        private TextView tvArticleLikeCount;
        private Button articleLike;


        public ViewHolder(View view) {
            super(view);
            ivArticleMoviePoster = view.findViewById(R.id.iv_article_poster);
            ivArticleUserPhoto = view.findViewById(R.id.iv_article_user_photo);
            tvArticleUserName = view.findViewById(R.id.tv_article_user_name);
            tvArticleMovieTitle = view.findViewById(R.id.tv_article_movie_title);
            tvArticleDate = view.findViewById(R.id.tv_article_date);
            articleRating = view.findViewById(R.id.article_rating);
            tvArticleTitle = view.findViewById(R.id.tv_article_title);
            tvArticleContent = view.findViewById(R.id.tv_article_content);
            tvArticleLikeCount = view.findViewById(R.id.tv_article_like_count);
            articleLike = view.findViewById(R.id.article_like);

        }
    }
}
