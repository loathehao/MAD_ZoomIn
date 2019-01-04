package mad.android.com.mad.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import mad.android.com.mad.R;
import mad.android.com.mad.bean.Movie;


//我的浏览历史界面
public class MyHistory extends Activity {
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
                MyHistory.this.finish();
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
        titleCenterTv.setText("浏览历史");
    }

    private void queryData() {
        BmobQuery<Movie> movieBmobQuery = new BmobQuery<>();
        movieBmobQuery.addWhereEqualTo("history",true);
        movieBmobQuery.order("-updatedAt");
        movieBmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    like_movie_name1.setText(list.get(0).getTitle());
                    Glide.with(MyHistory.this).load(list.get(0).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic1);
                    like_movie_introduce1.setText(list.get(0).getCasts());

                    like_movie_name2.setText(list.get(1).getTitle());
                    Glide.with(MyHistory.this).load(list.get(1).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic2);
                    like_movie_introduce2.setText(list.get(1).getCasts());

                    like_movie_name3.setText(list.get(2).getTitle());
                    Glide.with(MyHistory.this).load(list.get(2).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic3);
                    like_movie_introduce3.setText(list.get(2).getCasts());

                    like_movie_name4.setText(list.get(3).getTitle());
                    Glide.with(MyHistory.this).load(list.get(3).getPosterUrl()).error(R.drawable.my)
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(like_movie_pic4);
                    like_movie_introduce4.setText(list.get(3).getCasts());

                } else {
                    Log.e("BMOB", e.toString());

                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
