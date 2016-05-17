package makarov.vk.vkgroupchats;

import android.app.Application;

import com.vk.sdk.VKSdk;

import makarov.vk.vkgroupchats.ioc.app.IocInjector;

public class VkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.customInitialize(this, getResources().getInteger(R.integer.com_vk_sdk_AppId), "5.52");
        IocInjector.buildComponent(this);
    }
}
