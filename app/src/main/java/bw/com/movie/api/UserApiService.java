package bw.com.movie.api;



import java.util.Map;

import bw.com.movie.bean.LoginBean;
import bw.com.movie.bean.RegBean;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface UserApiService {
    //
    @POST(ApiService.ZHUCE)
    Observable<RegBean> getReg(@FieldMap Map<Object, Object> map);

}
