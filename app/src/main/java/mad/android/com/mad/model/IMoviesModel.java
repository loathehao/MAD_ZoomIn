package mad.android.com.mad.model;

import mad.android.com.mad.presenter.IMoviesLoadListener;
//电影加载接口
public interface IMoviesModel {
    void loadMovies(String total, IMoviesLoadListener iMoviesLoadListener);
}
