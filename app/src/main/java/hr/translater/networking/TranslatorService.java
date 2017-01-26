package hr.translater.networking;

import hr.translater.mvp.models.TranslateData;
import hr.translater.mvp.models.WordResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Igor on 24.1.2017..
 */

public interface TranslatorService {

    @GET("words/croatian")
    Observable<WordResponse> getCroatianWordsList();

    @GET("words/slovenian")
    Observable<WordResponse> getSlovenianWordsList();

    @POST("/translate")
    Observable<ResponseBody> postTranslation(@Body TranslateData translateData);
}
