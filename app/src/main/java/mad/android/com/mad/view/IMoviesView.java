package mad.android.com.mad.view;

//电影视图
import mad.android.com.mad.bean.MoviesBean;
public interface IMoviesView {
    void showNews(MoviesBean moviesBean);
    void hideDialog();
    void showDialog();
    void showErrorMsg(Throwable throwable);
}
