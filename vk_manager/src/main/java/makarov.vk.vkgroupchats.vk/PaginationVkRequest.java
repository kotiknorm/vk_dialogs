package makarov.vk.vkgroupchats.vk;

import com.vk.sdk.api.VKParameters;

import makarov.vk.vkgroupchats.vk.common.Loader;

public abstract class PaginationVkRequest<T> extends VkRequest<T> {

    private int mOffset = 0;

    public void executePage(int offset, final Loader<T> loader) {
        mOffset = offset;
        execute(loader);
    }

    @Override
    VKParameters getParameters() {
        VKParameters parameters = new VKParameters();
        parameters.put(Fields.OFFSET, mOffset);
        return parameters;
    }
}
