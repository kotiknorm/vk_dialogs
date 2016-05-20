package makarov.vk.vkgroupchats.ioc.app;

import javax.inject.Singleton;

import dagger.Component;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.ioc.chats.modules.ChatsModule;
import makarov.vk.vkgroupchats.ioc.app.modules.AppModule;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {

    ChatsComponent plusChatsComponent(ChatsModule chatsModule);

}
