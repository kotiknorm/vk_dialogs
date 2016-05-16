package makarov.vk.vkgroupchats.ioc.chats;

import javax.inject.Singleton;

import dagger.Subcomponent;
import makarov.vk.vkgroupchats.ioc.chats.modules.ChatsModule;
import makarov.vk.vkgroupchats.presentation.MainActivity;
import makarov.vk.vkgroupchats.presentation.view.ChatFragment;
import makarov.vk.vkgroupchats.presentation.view.ChatsListFragment;

@Singleton
@Subcomponent(modules = {
        ChatsModule.class
})
public interface ChatsComponent {

    void inject(ChatsListFragment chatsListFragment);
    void inject(ChatFragment chatFragment);
    void inject(MainActivity mainActivity);
}
