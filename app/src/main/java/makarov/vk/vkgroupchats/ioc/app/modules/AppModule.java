package makarov.vk.vkgroupchats.ioc.app.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.StorageImpl;
import makarov.vk.vkgroupchats.vk.VkManager;

@Module
public class AppModule {

    protected final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Storage provideStorage() {
        return new StorageImpl(mApplication);
    }

    @Provides
    @Singleton
    VkManager provideVkManager(Storage storage) {
        return new VkManager(mApplication, storage);
    }


}
