package makarov.vk.vkgroupchats.ioc.app;

import android.app.Application;

import makarov.vk.vkgroupchats.data.utils.Assert;
import makarov.vk.vkgroupchats.ioc.app.modules.AppModule;

public class IocInjector {

    private static AppComponent mComponent;

    public static void buildComponent(Application application) {
        Assert.assertNull(mComponent);

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    public static AppComponent get() {
        return mComponent;
    }
}
