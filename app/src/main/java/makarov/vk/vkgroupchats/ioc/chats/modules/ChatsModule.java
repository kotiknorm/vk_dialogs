package makarov.vk.vkgroupchats.ioc.chats.modules;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import makarov.vk.vkgroupchats.presentation.UiNavigator;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;
import makarov.vk.vkgroupchats.presentation.presenters.ChatsListPresenter;
import makarov.vk.vkgroupchats.vk.VkManager;

@Module
public class ChatsModule {

    protected final AppCompatActivity mActivity;

    public ChatsModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @Singleton
    AppCompatActivity provideAppCompatActivity() {
        return mActivity;
    }

    @Provides
    @Singleton
    UiNavigator provideUiNavigator(VkManager vkManager) {
        return new UiNavigator(mActivity, vkManager);
    }

    @Provides
    ChatPresenter provideChatPresenter() {
        return new ChatPresenter();
    }

    @Provides
    ChatsListPresenter provideChatsListPresenter(VkManager vkManager) {
        return new ChatsListPresenter(vkManager);
    }

}
