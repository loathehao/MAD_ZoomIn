package mad.android.com.mad.view;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import mad.android.com.mad.adapter.MyFragmentAdapter;
import mad.android.com.mad.bean.AppUser;
import mad.android.com.mad.bean.Movie;
import mad.android.com.mad.R;
import mad.android.com.mad.fragment.FgMovieArticle;
import mad.android.com.mad.fragment.FgMovieComment;
import mad.android.com.mad.fragment.FgMovieInfo;


import java.util.ArrayList;
import java.util.List;

//电影详情
public class MovieInfo extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView tab_movie_info;
    private TextView tab_movie_comment;
    private TextView tab_movie_article;
    private String objId = "idid";
    private String title;
    private String brief;
    private String posterUrl;
    private String genres;
    private String date;
    private String rating;
    private ViewPager movie_content;
    private Toolbar bottom_toolbar;
    Bundle bundle = new Bundle();
    AppUser user = BmobUser.getCurrentUser(AppUser.class);
    Movie movieItem = new Movie();
    public static int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        title = getIntent().getStringExtra("title");
        idQuery();
        posterUrl = getIntent().getStringExtra("poster_url");
        genres = getIntent().getStringExtra("genres");
        date = getIntent().getStringExtra("date");
        rating = getIntent().getStringExtra("rating");
        initView();
        initContentFragment();
    }

    private void initView() {
        ImageButton titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieInfo.this.finish();
            }
        });
        TextView titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText(title);
        tab_movie_info = findViewById(R.id.tab_movie_info_content);
        tab_movie_comment = findViewById(R.id.tab_movie_comment_content);
        tab_movie_article = findViewById(R.id.tab_movie_article_content);

        movie_content = findViewById(R.id.movie_info_content);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        tab_movie_info.setOnClickListener(this);
        tab_movie_comment.setOnClickListener(this);
        tab_movie_article.setOnClickListener(this);

    }

    private void initContentFragment() {

        bundle.putString("title", title);
        bundle.putString("poster_url", posterUrl);
        bundle.putString("brief", brief);
        bundle.putString("genres", genres);
        bundle.putString("date", date);
        bundle.putString("rating", rating);

        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new FgMovieInfo());
        mFragmentList.add(new FgMovieComment());
        mFragmentList.add(new FgMovieArticle());

        Fragment fgMovieInfo;
        fgMovieInfo = mFragmentList.get(0);
        fgMovieInfo.setArguments(bundle);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        movie_content.setAdapter(adapter);
        movie_content.setOffscreenPageLimit(1);
        movie_content.addOnPageChangeListener(this);

        setSupportActionBar(bottom_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setCurrentItem(0);

    }


    private void setCurrentItem(int i) {
        movie_content.setCurrentItem(i);
        tab_movie_article.setSelected(false);
        tab_movie_comment.setSelected(false);
        tab_movie_info.setSelected(false);
        switch (i) {
            case 0:
                tab_movie_info.setSelected(true);
                break;
            case 1:
                tab_movie_comment.setSelected(true);
            case 2:
                tab_movie_article.setSelected(true);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tab_movie_info_content:
                if (movie_content.getCurrentItem() != 0) {
                    setCurrentItem(0);
                }
                break;
            case R.id.tab_movie_comment_content:
                if (movie_content.getCurrentItem() != 1) {
                    setCurrentItem(1);
                }
                break;
            case R.id.tab_movie_article_content:
                if (movie_content.getCurrentItem() != 2) {
                    setCurrentItem(2);
                }
                break;
        }


    }

    public void idQuery() {
        BmobQuery<Movie> movieQuery = new BmobQuery<Movie>();
        movieQuery.addWhereEqualTo("title", title);
        movieQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> object, BmobException e) {
                Log.e("BMOB", title);
                if (e == null) {
                    objId = object.get(0).getObjectId();
                    Log.e("BMOB", "WHAT:" + object.size());
                    Log.e("BMOB", "currentId:" + objId);
                    movieItem=object.get(0);
                    movieItem.setHistory();
                    //Log.e("AAAAA",""+movieItem.getLike());
                    movieItem.update(objId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("BMOB", "历史:" + movieItem.getUpdatedAt());
                            } else {
                                Log.e("BMOB", "历史：" + e.getMessage());
                            }
                        }
                    });
                    movieItem.setHistory();
                    movieItem.update(objId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("BMOB", "历史:" + movieItem.getUpdatedAt());
                            } else {
                                Log.e("BMOB", "历史：" + e.getMessage());
                            }
                        }
                    });
                    //Snackbar.make(FgMovieInfo.this, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
