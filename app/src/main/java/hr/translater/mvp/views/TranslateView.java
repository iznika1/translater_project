package hr.translater.mvp.views;

import hr.translater.mvp.models.TranslateResponse;

/**
 * Created by Igor on 27.1.2017..
 */

public interface TranslateView extends BaseView {

    void getTranslationSucess(TranslateResponse translateResponse);
}
