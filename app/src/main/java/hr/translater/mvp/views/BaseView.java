package hr.translater.mvp.views;

/**
 * Created by Igor on 26.1.2017..
 */

public interface BaseView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);
}
