package mad.android.com.mad.presenter;


import mad.android.com.mad.bean.MoviesBean;
import mad.android.com.mad.model.IMoviesModel;
import mad.android.com.mad.model.MoviesModel;
import mad.android.com.mad.view.IMoviesView;

//电影presenter层
public class MoviesPresenter implements IMoviesPresenter,IMoviesLoadListener {

    private IMoviesModel iMoviesModel;
    private IMoviesView iMoviesView;

    public MoviesPresenter(IMoviesView iMoviesView) {
        this.iMoviesView = iMoviesView;
        this.iMoviesModel =new MoviesModel();
    }


    @Override
    public void success(MoviesBean moviesBean) {
        iMoviesView.hideDialog();
        iMoviesView.showNews(moviesBean);
    }

    @Override
    public void fail(Throwable throwable) {
        iMoviesView.hideDialog();
        iMoviesView.showErrorMsg(throwable);
    }


    @Override
    public void loadMovies(String total) {
        iMoviesView.showDialog();
        iMoviesModel.loadMovies(total,this);
    }
}
