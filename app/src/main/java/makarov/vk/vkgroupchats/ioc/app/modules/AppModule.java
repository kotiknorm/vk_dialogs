package makarov.vk.vkgroupchats.ioc.app.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.StorageImpl;
import makarov.vk.vkgroupchats.data.query.QueryFactory;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;

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
    QueryFactory provideQueryFactory(Storage storage) {
        return new QueryFactory(storage);
    }

    @Provides
    @Singleton
    VkManager provideVkManager() {
        return new VkManager();
    }

    @Provides
    @Singleton
    VkRequestsFactory provideVkRequestsFactory(Storage storage) {
        return new VkRequestsFactory(storage);
    }

}
