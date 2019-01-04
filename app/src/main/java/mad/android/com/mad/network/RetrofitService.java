package mad.android.com.mad.network;

import mad.android.com.mad.bean.MoviesBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

//实现retrofi+okhttp网络功能

public interface RetrofitService {
    @GET("/v2/movie/{total}")
    Observable<MoviesBean> getMovie(@Path("total") String total);

}
