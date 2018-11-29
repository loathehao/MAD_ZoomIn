package mad.android.com.mad.network;

import mad.android.com.mad.MoviesBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface RetrofitService {
    /*
    * https://api.douban.com/v2/movie/in_theaters
    * */
    @GET("/v2/movie/{total}")
    Observable<MoviesBean> getMovie(@Path("total") String total);

}
