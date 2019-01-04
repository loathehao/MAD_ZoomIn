package mad.android.com.mad.model;

import android.util.Log;

import mad.android.com.mad.bean.MoviesBean;
import mad.android.com.mad.model.IMoviesModel;
import mad.android.com.mad.presenter.IMoviesLoadListener;
import mad.android.com.mad.network.RetrofitHelper;
import mad.android.com.mad.network.Api;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
//电影加载model
public class MoviesModel implements IMoviesModel {
    private static final String TAG = "MoviesModel";
    @Override
    public void loadMovies( String total, final IMoviesLoadListener iMoviesLoadListener) {
        RetrofitHelper retrofitHelper= new RetrofitHelper(Api.MOVIE_HOST);
        retrofitHelper.getMovies(total)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MoviesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iMoviesLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(MoviesBean moviesBean) {
                        iMoviesLoadListener.success(moviesBean);
                        Log.i(TAG, "onNext: "+moviesBean.getTotal());
                    }
                });
    }

}
