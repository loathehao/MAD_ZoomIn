package mad.android.com.mad.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import mad.android.com.mad.R;
import mad.android.com.mad.bean.Movie;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//我的喜爱界面
public class MyFavorite extends Activity {
    private CardView like_recommend1;
    private ImageView like_movie_pic1;
    private TextView like_movie_name1;
    private TextView like_movie_introduce1;
    private CardView like_recommend2;
    private ImageView like_movie_pic2;
    private TextView like_movie_name2;
    private TextView like_movie_introduce2;
    private CardView like_recommend3;
    private ImageView like_movie_pic3;
    private TextView like_movie_name3;
    private TextView like_movie_introduce3;
    private CardView like_recommend4;
    private ImageView like_movie_pic4;
    private TextView like_movie_name4;
    private TextView like_movie_introduce4;
    private TextView title_right_text_tv;
    Movie movieItem = new Movie();
    BmobQuery<Movie> movieBmobQuery = new BmobQuery<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initViews();
        queryData();
    }

    private void initViews() {
        title_right_text_tv = findViewById(R.id.title_right_text_tv);
        like_recommend1 = findViewById(R.id.like_recommend1);
        like_movie_pic1 = findViewById(R.id.like_movie_pic1);
        like_movie_name1 = findViewById(R.id.like_movie_name1);
        like_movie_introduce1 = findViewById(R.id.like_movie_introduce1);

        like_recommend2 = findViewById(R.id.like_recommend2);
        like_movie_pic2 = findViewById(R.id.like_movie_pic2);
        like_movie_name2 = findViewById(R.id.like_movie_name2);
        like_movie_introduce2 = findViewById(R.id.like_movie_introduce2);

        like_recommend3 = findViewById(R.id.like_recommend3);
        like_movie_pic3 = findViewById(R.id.like_movie_pic3);
        like_movie_name3 = findViewById(R.id.like_movie_name3);
        like_movie_introduce3 = findViewById(R.id.like_movie_introduce3);

        like_recommend4 = findViewById(R.id.like_recommend4);
        like_movie_pic4 = findViewById(R.id.like_movie_pic4);
        like_movie_name4 = findViewById(R.id.like_movie_name4);
        like_movie_introduce4 = findViewById(R.id.like_movie_introduce4);

        ImageButton titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFavorite.this.finish();
            }
        });

        title_right_text_tv.setText("刷新");
        title_right_text_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
            }
        });
        TextView titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText("我的喜欢");
    }

    private void queryData() {
        Log.e("BMOB", "hello");
        movieBmobQuery.addWhereEqualTo("like", true);
        movieBmobQuery.order("-updatedAt");
        movieBmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(final List<Movie> list, BmobException e) {
                if (e == null) {
                    /*like_movie_name1.setText(list.get(0).getTitle());
                    Glide.with(MyFavorite.this).load(list.get(0).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic1);
                    like_movie_introduce1.setText(list.get(0).getCasts());

                    like_movie_name2.setText(list.get(1).getTitle());
                    Glide.with(MyFavorite.this).load(list.get(1).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic2);
                    like_movie_introduce2.setText(list.get(1).getCasts());

                    like_movie_name3.setText(list.get(2).getTitle());
                    Glide.with(MyFavorite.this).load(list.get(2).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic3);
                    like_movie_introduce3.setText(list.get(2).getCasts());

                    like_movie_name4.setText(list.get(3).getTitle());
                    Glide.with(MyFavorite.this).load(list.get(3).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic4);
                    like_movie_introduce4.setText(list.get(3).getCasts());*/
                    idQuery(like_recommend1,list,1);
                    idQuery(like_recommend2,list,2);
                    idQuery(like_recommend3,list,3);
                    idQuery(like_recommend4,list,4);

                } else {
                    Log.e("BMOB", e.toString());

                }
            }
        });
        Log.e("BMOB", "bye");
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }

    public void idQuery(CardView item, final List<Movie> movieList, final int i) {
        BmobQuery<Movie> movieQuery = new BmobQuery<Movie>();
        movieQuery.addWhereEqualTo("title", movieList.get(i-1).getTitle());
        movieQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> object, BmobException e) {
                Log.e("BMOB", object.get(0).getTitle());
                if (e == null) {
                    Log.e("BMOB", "WHAT:" + object.size());
                    Log.e("BMOB", "currentId:" + object.get(0).getObjectId());
                    movieItem = object.get(0);
                    updateItem(movieItem, i);
                    //Snackbar.make(FgMovieInfo.this, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateItem(final Movie movieItem, int i) {
        CardView card=like_recommend1;
        TextView name=like_movie_name1;
        TextView casts=like_movie_introduce1;
        ImageView poster=like_movie_pic1;
        switch(i){
            case 1:
                card=like_recommend1;
                name=like_movie_name1;
                casts=like_movie_introduce1;
                poster=like_movie_pic1;
                break;
            case 2:
                card=like_recommend2;
                name=like_movie_name2;
                casts=like_movie_introduce2;
                poster=like_movie_pic2;
                break;
            case 3:
                card=like_recommend3;
                name=like_movie_name3;
                casts=like_movie_introduce3;
                poster=like_movie_pic3;
                break;
            case 4:
                card=like_recommend4;
                name=like_movie_name4;
                casts=like_movie_introduce4;
                poster=like_movie_pic4;
                break;

        }
        name.setText(movieItem.getTitle());
        Glide.with(MyFavorite.this).load(movieItem.getPosterUrl()).error(R.drawable.my)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);
        casts.setText(movieItem.getCasts());
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyFavorite.this, MovieInfo.class);
                intent.putExtra("poster_url", movieItem.getPosterUrl());
                intent.putExtra("title", movieItem.getTitle());
                intent.putExtra("genres", movieItem.getGenres());
                intent.putExtra("date", movieItem.getDate());
                intent.putExtra("rating", movieItem.getRating());
                startActivity(intent);
            }
        });
    }
}
