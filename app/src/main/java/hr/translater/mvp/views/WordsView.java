package hr.translater.mvp.views;

import hr.translater.mvp.models.WordResponse;

/**
 * Created by Igor on 24.1.2017..
 */

public interface WordsView extends BaseView {
    void getWordsListSuccess(WordResponse wordResponse);
}
