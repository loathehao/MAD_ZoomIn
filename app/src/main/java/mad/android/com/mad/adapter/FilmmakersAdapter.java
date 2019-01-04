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
//电影演员适配器
public class FilmmakersAdapter extends RecyclerView.Adapter<FilmmakersAdapter.ViewHolder> {
    private List<MoviesBean.SubjectsBean> objects = new ArrayList<MoviesBean.SubjectsBean>(10);
    private Context context;

    public FilmmakersAdapter(Context context) {
        this.context=context;
    }
    public void setData(List<MoviesBean.SubjectsBean> objects){
        this.objects = objects;
    }

    @Override
    public FilmmakersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_filmmakers, parent, false);
        return new FilmmakersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmmakersAdapter.ViewHolder holder, int position) {
        final MoviesBean.SubjectsBean bean=objects.get(position);
        if (bean==null){
            return;
        }
        holder.tvFilmmakersName.setText("某某某");
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
        private ImageView ivFilmmakersPhoto;
        private TextView tvFilmmakersName;
        private TextView tvFilmmakersPosition;


        public ViewHolder(View view) {
            super(view);
            ivFilmmakersPhoto = view.findViewById(R.id.iv_filmmakers_photo);
            tvFilmmakersName = view.findViewById(R.id.tv_filmmakers_name);
            tvFilmmakersPosition = view.findViewById(R.id.tv_filmmakers_position);

        }
    }
}
