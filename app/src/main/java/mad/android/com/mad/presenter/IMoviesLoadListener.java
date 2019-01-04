package mad.android.com.mad.presenter;


import mad.android.com.mad.bean.MoviesBean;

//电影加载监听接口
public interface IMoviesLoadListener {
    void success(MoviesBean moviesBean);
    void fail(Throwable throwable);
}
