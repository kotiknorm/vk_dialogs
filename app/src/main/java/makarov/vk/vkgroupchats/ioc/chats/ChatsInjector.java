package makarov.vk.vkgroupchats.ioc.chats;

import android.support.v7.app.AppCompatActivity;

import makarov.vk.vkgroupchats.ioc.app.IocInjector;
import makarov.vk.vkgroupchats.ioc.chats.modules.ChatsModule;
import makarov.vk.vkgroupchats.utils.Assert;

public class ChatsInjector {

    private AppCompatActivity mActivity;
    private ChatsComponent mComponent;

    public ChatsInjector(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void buildComponent() {
        Assert.assertNull(mComponent);

        mComponent = IocInjector.get().plusChatsComponent(new ChatsModule(mActivity));
    }

    public void destroyComponent() {
        mComponent = null;
    }

    public ChatsComponent get() {
        return mComponent;
    }
}
