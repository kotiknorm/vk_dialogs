package makarov.vk.vkgroupchats.ioc;

public interface Injector<COMPONENT_TYPE> {

    COMPONENT_TYPE get();

    void buildComponent();

    void destroyComponent();

}
