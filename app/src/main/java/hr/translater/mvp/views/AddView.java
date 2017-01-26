package hr.translater.mvp.views;

import hr.translater.mvp.models.TranslateResponse;
import okhttp3.ResponseBody;

/**
 * Created by Igor on 26.1.2017..
 */

public interface AddView extends  BaseView {

    void addTranslateSuccess(ResponseBody translateResponse);

}
