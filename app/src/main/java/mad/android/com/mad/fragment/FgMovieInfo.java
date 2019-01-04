package mad.android.com.mad.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import mad.android.com.mad.adapter.FilmmakersAdapter;
import mad.android.com.mad.R;
import mad.android.com.mad.adapter.PhotosVideosAdapter;
import mad.android.com.mad.bean.AppUser;
import mad.android.com.mad.bean.Movie;
import mad.android.com.mad.bean.MoviesBean;
import mad.android.com.mad.presenter.MoviesPresenter;
import mad.android.com.mad.view.IMoviesView;


//电影信息碎片
public class FgMovieInfo extends Fragment implements IMoviesView {
    private MoviesPresenter moviesPresenter;
    private SwipeRefreshLayout srl_movie_info;
    private RecyclerView rv_filmmakers;
    private RecyclerView rv_photos;
    private RecyclerView rv_videos;
    private FilmmakersAdapter filmmakersAdapter;
    private PhotosVideosAdapter photosVideosAdapter;
    private ImageView movieInfoPoster;
    private TextView movieInfoTitle;
    private TextView movieInfoBrief;
    private TextView movieInfoGenres;
    private TextView movieInfoTime;
    private ImageView btnLike;
    private TextView btnCollect;
    private TextView movieInfoLength;
    private SimpleRatingBar ratingBar;
    private String currentId;
    private String currentTitle;
    AppUser user = BmobUser.getCurrentUser(AppUser.class);
    Movie m = new Movie();
    BmobRelation relation = new BmobRelation();
    private boolean isLike;
    private boolean isFavorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_movie_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isAdded()) {
            currentId = getArguments().getString("id");
            movieInfoLength.setText(currentId);
            String url = getArguments().getString("poster_url");
            Glide.with(this)
                    .load(url)
                    .into(movieInfoPoster);
            Log.e("bmob", "onstart:" + currentId);
            movieInfoTitle.setText(getArguments().getString("title"));
            currentTitle = (String) movieInfoTitle.getText();
            query();
            movieInfoGenres.setText(getArguments().getString("genres"));
            movieInfoTime.setText("上映年份：" + getArguments().getString("date"));
            movieInfoBrief.setText(m.getBrief());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesPresenter = new MoviesPresenter(this);
        srl_movie_info = view.findViewById(R.id.srl_movie_info);
        rv_filmmakers = view.findViewById(R.id.movie_info_filmmakers);
        rv_photos = view.findViewById(R.id.movie_info_photos);
        rv_videos = view.findViewById(R.id.movie_info_videos);
        movieInfoPoster = view.findViewById(R.id.iv_movie_info_poster);
        movieInfoTitle = view.findViewById(R.id.tv_movie_info_title);
        movieInfoBrief = view.findViewById(R.id.tv_movie_info_brief);
        movieInfoGenres = view.findViewById(R.id.tv_movie_info_genres);
        movieInfoTime = view.findViewById(R.id.tv_movie_info_time);
        btnLike = view.findViewById(R.id.like);
        btnCollect = view.findViewById(R.id.collect);
        //movieInfoRating = view.findViewById(R.id.tv_movie_info_rating);
        movieInfoLength = view.findViewById(R.id.tv_movie_info_length);
        ratingBar = view.findViewById(R.id.ratingbar);
        filmmakersAdapter = new FilmmakersAdapter(getActivity());
        photosVideosAdapter = new PhotosVideosAdapter(getActivity());
        srl_movie_info.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        moviesPresenter.loadMovies("in_theaters");
        srl_movie_info.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesPresenter.loadMovies("in_theaters");
            }
        });
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.setRating(ratingBar.getRating()*2);
                m.update(m.getObjectId(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.e("info","更新成功:"+m.getUpdatedAt());
                        }else{
                            Log.e("info","更新失败：" + e.getMessage());
                        }
                    }
                });
            }
            });
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.setLike(!m.getLike());
                m.update(m.getObjectId(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.e("info","更新成功:"+m.getUpdatedAt());
                        }else{
                            Log.e("info","更新失败：" + e.getMessage());
                        }
                    }
                });
                if (m.getLike())
                    btnLike.setImageDrawable(getResources().getDrawable((R.drawable.like)));
                else
                    btnLike.setImageDrawable(getResources().getDrawable((R.drawable.dislike)));
            }
        });
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.setFavorite(!m.getFavorite());
                m.update(m.getObjectId(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.e("info","更新成功:"+m.getUpdatedAt());
                        }else{
                            Log.e("info","更新失败：" + e.getMessage());
                        }
                    }
                });
                if (m.getFavorite())
                    btnCollect.setText("取消收藏");
                else
                    btnCollect.setText("收藏");
            }
        });

    }

    @Override
    public void showNews(MoviesBean moviesBean) {
        filmmakersAdapter.setData(moviesBean.getSubjects());
        photosVideosAdapter.setData(moviesBean.getSubjects());
        LinearLayoutManager llm_filmmakers = new LinearLayoutManager(getActivity());
        llm_filmmakers.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager llm_photos = new LinearLayoutManager(getActivity());
        llm_photos.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager llm_videos = new LinearLayoutManager(getActivity());
        llm_videos.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_filmmakers.setLayoutManager(llm_filmmakers);
        rv_filmmakers.setAdapter(filmmakersAdapter);
        rv_photos.setLayoutManager(llm_photos);
        rv_photos.setAdapter(photosVideosAdapter);
        rv_videos.setLayoutManager(llm_videos);
        rv_videos.setAdapter(photosVideosAdapter);

    }

    public void query() {
        BmobQuery<Movie> movieQuery = new BmobQuery<Movie>();
        movieQuery.addWhereEqualTo("title", currentTitle);
        movieQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> object, BmobException e) {
                Log.e("BMOB", currentTitle);
                if (e == null) {
                    m = object.get(0);
                    movieInfoBrief.setText(m.getBrief());
                    Log.e("BMOB", "what:" + object.size());
                    if (m.getLike())
                        btnLike.setImageDrawable(getResources().getDrawable((R.drawable.like)));
                    else
                        btnLike.setImageDrawable(getResources().getDrawable((R.drawable.dislike)));
                    if (m.getFavorite())
                        btnCollect.setText("取消收藏");
                    else
                        btnCollect.setText("收藏");
                    //Snackbar.make(FgMovieInfo.this, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void hideDialog() {
        srl_movie_info.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srl_movie_info.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
