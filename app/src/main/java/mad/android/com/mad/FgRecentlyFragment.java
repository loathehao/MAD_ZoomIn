package mad.android.com.mad;

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

import mad.android.com.mad.presenter.MoviesPresenter;
import mad.android.com.mad.view.IMoviesView;


public class FgRecentlyFragment extends Fragment implements IMoviesView {

    private MoviesPresenter moviesPresenter;
    private RecyclerView rv_movie_on;
    private SwipeRefreshLayout srl_movie;
    private ItemMovieOnAdapter movieOnAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_recently, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesPresenter = new MoviesPresenter(this);
        srl_movie = view.findViewById(R.id.srl_movie);
        rv_movie_on = view.findViewById(R.id.rv_movie_on);
        movieOnAdapter = new ItemMovieOnAdapter(getActivity());
        srl_movie.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        moviesPresenter.loadMovies("in_theaters");
        srl_movie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesPresenter.loadMovies("in_theaters");
            }
        });
    }


    @Override
    public void showNews(MoviesBean moviesBean) {
        movieOnAdapter.setData(moviesBean.getSubjects());
        rv_movie_on.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_movie_on.setAdapter(movieOnAdapter);

    }

    @Override
    public void hideDialog() {
        srl_movie.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srl_movie.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(),"Error"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
