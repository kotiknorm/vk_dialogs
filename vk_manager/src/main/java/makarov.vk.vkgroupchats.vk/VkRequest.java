package makarov.vk.vkgroupchats.vk;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import makarov.vk.vkgroupchats.vk.common.Loader;

public abstract class VkRequest<T> {

    static class Fields {

        static final String COUNT = "count";
        static final String PEER_ID = "peer_id";
        static final String OFFSET = "offset";
        static final String PREVIEW_LENGTH = "preview_length";
        static final String CHAT_IDS = "chat_ids";
        static final String FIELDS = "fields";

    }

    public abstract void execute(Loader<T> loader);

    abstract VKParameters getParameters();

    public abstract void cancel();

    protected void loadFromNetwork(VKRequest request,
                                   VKRequest.VKRequestListener listener) {
        request.executeWithListener(listener);
    }
}
