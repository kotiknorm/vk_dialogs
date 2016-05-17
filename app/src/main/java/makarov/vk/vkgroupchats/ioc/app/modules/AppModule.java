package makarov.vk.vkgroupchats.ioc.app.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import makarov.vk.vkgroupchats.GsonCreator;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.StorageImpl;
import makarov.vk.vkgroupchats.vk.ChatJsonParser;
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
    VkManager provideVkManager(Storage storage, ChatJsonParser jsonParser) {
        return new VkManager(mApplication, storage, jsonParser);
    }

    @Provides
    @Singleton
    GsonCreator provideGsonCreator() {
        return new GsonCreator();
    }

    @Provides
    @Singleton
    ChatJsonParser provideChatJsonParser(GsonCreator gsonCreator) {
        return new ChatJsonParser(gsonCreator);
    }


}
