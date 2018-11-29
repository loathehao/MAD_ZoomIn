package mad.android.com.mad.view;


import mad.android.com.mad.MoviesBean;
public interface IMoviesView {
    void showNews(MoviesBean moviesBean);
    void hideDialog();
    void showDialog();
    void showErrorMsg(Throwable throwable);
}
