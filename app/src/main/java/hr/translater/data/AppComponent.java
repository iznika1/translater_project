package hr.translater.data;

import javax.inject.Singleton;

import dagger.Component;
import hr.translater.MainActivity;
import hr.translater.networking.TranslatorModule;

/**
 * Created by Igor on 24.1.2017..
 */

@Singleton
@Component(modules = {TranslatorModule.class,})
public interface AppComponent {
   void inject(MainActivity croWordsActivity);
}
