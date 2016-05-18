package makarov.vk.vkgroupchats;

import android.app.Application;

import makarov.vk.vkgroupchats.ioc.app.IocInjector;
import makarov.vk.vkgroupchats.vk.VkManager;

public class VkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VkManager.init(this);
        IocInjector.buildComponent(this);
    }
}
