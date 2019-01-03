package mad.android.com.mad;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import mad.android.com.mad.beans.Movie;

public class MyfavoriteActivity extends Activity {

    private RecyclerView recyclerView;

    List<Movie> bankCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfavorite_activity);

        Bmob.initialize(this, "acbc897f711009c562745ec99aacfd49");



        ImageButton titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyfavoriteActivity.this.finish();
            }
        });
        TextView titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText("喜爱电影");

        recyclerView = findViewById(R.id.myfavorite_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FavoriteAdapter(this));

        loadData();


    }

    private void loadData(){

    }


    public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

        Context context;

        public FavoriteAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Movie bean = bankCards.get(position);
            BmobQuery<Movie> movieBmobQuery = new BmobQuery<>();
            movieBmobQuery.order("-createdAt");
            movieBmobQuery.addWhereEqualTo("like","true");
            movieBmobQuery.findObjects(new FindListener<Movie>() {
                @Override
                public void done(List<Movie> list, BmobException e) {
                    if(e==null){
                        for(int i = 0;i<list.size();i++){
                            holder.item_movie_name.setText(list.get(i).getTitle());
                            holder.item_movie_introduce.setText(list.get(i).getBrief());
                            Glide.with(context).load(list.get(i).getPosterUrl()).into(((ViewHolder) holder).item_movie_pic);
                        }
                    }
                    else{
                        Toast.makeText(MyfavoriteActivity.this, "连接失败 " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return bankCards.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
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

}
