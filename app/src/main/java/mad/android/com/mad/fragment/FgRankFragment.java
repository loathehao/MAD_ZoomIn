package mad.android.com.mad.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Toast;

import mad.android.com.mad.adapter.ItemMovieTopAdapter;
import mad.android.com.mad.R;
import mad.android.com.mad.bean.MoviesBean;
import mad.android.com.mad.presenter.MoviesPresenter;
import mad.android.com.mad.view.IMoviesView;


//电影排行碎片
public class FgRankFragment extends Fragment implements IMoviesView {

    private MoviesPresenter moviesPresenter;
    private RecyclerView rv_rank;
    private SwipeRefreshLayout srl_rank;
    private ItemMovieTopAdapter itemMovieTopAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_rank, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesPresenter = new MoviesPresenter(this);
        srl_rank = view.findViewById(R.id.srl_rank);
        rv_rank = view.findViewById(R.id.rv_rank);
        itemMovieTopAdapter = new ItemMovieTopAdapter(getActivity());
        srl_rank.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        moviesPresenter.loadMovies("top250");
        srl_rank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesPresenter.loadMovies("top250");
            }
        });
    }


    @Override
    public void showNews(MoviesBean moviesBean) {
        itemMovieTopAdapter.setData(moviesBean.getSubjects());
        rv_rank.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_rank.setAdapter(itemMovieTopAdapter);

    }

    @Override
    public void hideDialog() {
        srl_rank.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srl_rank.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
