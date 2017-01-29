package hr.translater.networking;

import hr.translater.mvp.models.AccessToken;
import hr.translater.mvp.models.LoginData;
import hr.translater.mvp.models.TranslateData;
import hr.translater.mvp.models.TranslateResponse;
import hr.translater.mvp.models.WordResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Igor on 24.1.2017..
 */

public interface TranslatorService {

    @GET("words/{lang}")
    Observable<WordResponse> getWordsListForLang(@Path("lang") String lang);

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json"
    })
    @GET("translate/{langFrom}/to/{langTo}")
    Observable<TranslateResponse> getTranslate(@Path("langFrom") String langFrom, @Path("langTo") String langTo, @Query(value = "word", encoded = true) String word);

    @POST("/translate")
    Observable<ResponseBody> postTranslation(@Body TranslateData translateData);

    @POST("/login")
    Observable<AccessToken> login(@Body LoginData LoginData);

    @POST("/register")
    Observable<ResponseBody> register(@Body LoginData LoginData);
}
