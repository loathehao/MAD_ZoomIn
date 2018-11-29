package mad.android.com.mad;

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

import mad.android.com.mad.presenter.MoviesPresenter;
import java.util.List;



public class FgRankFragment extends Fragment {


    private MoviesPresenter moviesPresenter;
    private RecyclerView rv_item_movie;
    private MovieTopAdapter movieTopAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_rank,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
