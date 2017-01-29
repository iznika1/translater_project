package hr.translater.networking;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.translater.BuildConfig;
import hr.translater.mvp.models.LoginData;
import hr.translater.mvp.models.LoginData_Table;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Igor on 24.1.2017..
 */
@Module
public class TranslatorModule {

    File cacheFile;

    public TranslatorModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = null;

                        String url = original.url().encodedPath();
                        if(url.equals("/login")){
                            request  = original.newBuilder()
                                    .header("Content-Type", "application/json")
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                    .build();
                        }
                        else{
                            LoginData loginData = SQLite.select().
                                    from(LoginData.class).querySingle();

                            request  = original.newBuilder()
                                    .header("Content-Type", "application/json")
                                    .header("Authorization",String.format("%s %s", loginData.getAccessToken().getGrantType(),loginData.getAccessToken().getAccessToken()))
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                    .build();
                        }

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)

                .build();


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public TranslatorService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(TranslatorService.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            TranslatorService networkService) {
        return new Service(networkService);
    }
}
