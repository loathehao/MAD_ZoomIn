package mad.android.com.mad.model;


import mad.android.com.mad.MoviesBean;

public interface IMoviesLoadListener {
    void success(MoviesBean moviesBean);
    void fail(Throwable throwable);
}
