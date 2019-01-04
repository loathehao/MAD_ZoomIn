package mad.android.com.mad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mad.android.com.mad.R;
import mad.android.com.mad.bean.MoviesBean;
//电影评论适配器
public class ItemCommentAdapter extends RecyclerView.Adapter<ItemCommentAdapter.ViewHolder> {
    private List<MoviesBean.SubjectsBean> objects = new ArrayList<MoviesBean.SubjectsBean>();

    private Context context;

    public ItemCommentAdapter(Context context) {
        this.context=context;
    }
    public void setData(List<MoviesBean.SubjectsBean> objects){
        this.objects = objects;
    }

    @Override
    public ItemCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ItemCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemCommentAdapter.ViewHolder holder, int position) {
        final MoviesBean.SubjectsBean bean=objects.get(position);
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
        private ImageView ivCommentUser;
        private TextView tvCommentUserName;
        private TextView tvCommentDate;
        private TextView CommentRating;
        private TextView tvCommentContent;
        private TextView tvLikeCount;
        private Button commentLike;


        public ViewHolder(View view) {
            super(view);
            ivCommentUser = view.findViewById(R.id.iv_comment_user);
            tvCommentUserName = view.findViewById(R.id.tv_comment_user_name);
            tvCommentDate = view.findViewById(R.id.tv_comment_date);
            CommentRating = view.findViewById(R.id.comment_rating);
            tvCommentContent = view.findViewById(R.id.tv_comment_content);
            tvLikeCount = view.findViewById(R.id.tv_like_count);
            commentLike = view.findViewById(R.id.comment_like);

        }
    }
}
