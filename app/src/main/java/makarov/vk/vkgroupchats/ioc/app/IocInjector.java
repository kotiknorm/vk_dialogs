package makarov.vk.vkgroupchats.ioc.app;

import android.app.Application;

import makarov.vk.vkgroupchats.data.utils.Assert;
import makarov.vk.vkgroupchats.ioc.Injector;
import makarov.vk.vkgroupchats.ioc.app.modules.AppModule;

public class IocInjector implements Injector<AppComponent> {

    private AppComponent mComponent;
    private final Application mApplication;

    public IocInjector(Application application) {
        mApplication = application;
    }

    public void buildComponent() {
        Assert.assertNull(mComponent);

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(mApplication))
                .build();
    }

    public AppComponent get() {
        return mComponent;
    }

    @Override
    public void destroyComponent() {
        throw new RuntimeException("This component is static");
    }

    public static class StaticContext {

        private static IocInjector mIocInjector;

        public static void init(Application application) {
            Assert.assertNull(mIocInjector);
            mIocInjector = new IocInjector(application);
            mIocInjector.buildComponent();
        }

        public static AppComponent get() {
            return mIocInjector.get();
        }
    }
}
