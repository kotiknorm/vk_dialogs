package makarov.vk.vkgroupchats.common;

public interface Loader<T> {

    void onLoaded(T result, Exception e);
}
