package mad.android.com.mad.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mad.android.com.mad.adapter.ItemArticleAdapter;
import mad.android.com.mad.R;
import mad.android.com.mad.bean.MoviesBean;
import mad.android.com.mad.presenter.MoviesPresenter;
import mad.android.com.mad.view.IMoviesView;

//电影文章碎片

public class FgMovieArticle extends Fragment implements IMoviesView {
    private MoviesPresenter moviesPresenter;
    private RecyclerView rv_comment;
    private SwipeRefreshLayout srl_comment;
    private ItemArticleAdapter itemArticleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_movie_comment_article, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moviesPresenter = new MoviesPresenter(this);
        srl_comment = view.findViewById(R.id.srl_comment_article);
        rv_comment = view.findViewById(R.id.rv_comment_article);
        itemArticleAdapter = new ItemArticleAdapter(getActivity());
        srl_comment.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        moviesPresenter.loadMovies("in_theaters");
        srl_comment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesPresenter.loadMovies("in_theaters");
            }
        });
    }

    @Override
    public void showNews(MoviesBean moviesBean) {
        itemArticleAdapter.setData(moviesBean.getSubjects());
        rv_comment.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_comment.setAdapter(itemArticleAdapter);

    }

    @Override
    public void hideDialog() { srl_comment.setRefreshing(false); }

    @Override
    public void showDialog() {
        srl_comment.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
