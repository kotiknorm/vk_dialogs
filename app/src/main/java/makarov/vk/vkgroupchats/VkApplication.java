package makarov.vk.vkgroupchats;

import android.app.Application;

import com.vk.sdk.VKSdk;

import makarov.vk.vkgroupchats.ioc.app.IocInjector;

public class VkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        IocInjector.buildComponent(this);
    }
}
