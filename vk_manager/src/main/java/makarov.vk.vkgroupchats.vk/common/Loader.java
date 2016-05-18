package makarov.vk.vkgroupchats.vk.common;

public interface Loader<T> {

    void onLoaded(T result, Exception e);
}
