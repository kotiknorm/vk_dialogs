package makarov.vk.vkgroupchats.vk;

import com.vk.sdk.api.VKRequest;

import makarov.vk.vkgroupchats.vk.common.Loader;

public abstract class VkRequest<T> {

    public abstract void execute(Loader<T> loader);

    public abstract void cancel();

    protected void loadFromNetwork(VKRequest request,
                                   VKRequest.VKRequestListener listener) {
        request.executeWithListener(listener);
    }
}
