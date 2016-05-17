package makarov.vk.vkgroupchats.vk;

import android.support.annotation.Nullable;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiMessages;
import com.vk.sdk.api.model.VKApiGetMessagesResponse;

import org.json.JSONObject;

import java.util.List;

import makarov.vk.vkgroupchats.common.Loader;

public class MessageVkRequest extends VkRequest<List> {

    private static final int COUNT_MESSAGES = 20;
    private static final int CHAT_PREFIX = 2000000000;
    private static final String FUNCTION_NAME = "getHistory";

    @Nullable VKRequest mRequest;

    private final int mChatId;

    class VkApiHistory extends VKApiMessages {

        public VKRequest getHistory(VKParameters params) {
            return prepareRequest(FUNCTION_NAME, params, new VKParser() {
                @Override
                public Object createModel(JSONObject object) {
                    return new VKApiGetMessagesResponse(object);
                }
            });
        }
    }

    public MessageVkRequest(int chatId) {
        mChatId = chatId;
    }

    @Override
    public void execute(final Loader<List> loader) {
        VKParameters parameters = new VKParameters();
        parameters.put("count", COUNT_MESSAGES);
        parameters.put("peer_id", CHAT_PREFIX + mChatId);
        mRequest = new VkApiHistory().getHistory(parameters);
        loadFromNetwork(mRequest, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                loader.onLoaded(null, null);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                loader.onLoaded(null, error.httpError);
            }
        });
    }

    @Override
    public void cancel() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }
}
