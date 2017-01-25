package hr.translater.networking;

import hr.translater.mvp.models.WordResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Igor on 24.1.2017..
 */

public interface TranslatorService {

    @GET("words/croatian")
    Observable<WordResponse> getCroatianWordsList();

    @GET("words/slovenian")
    Observable<WordResponse> getSlovenianWordsList();
}
