package hr.translater.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Igor on 28.1.2017..
 */

public class TranslaterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        //FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}
