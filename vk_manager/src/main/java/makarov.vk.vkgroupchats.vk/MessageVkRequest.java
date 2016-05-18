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

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.vk.common.Loader;
import makarov.vk.vkgroupchats.vk.parsers.MessageJsonParser;

public class MessageVkRequest extends PaginationVkRequest<List<Message>> {

    private static final int COUNT_MESSAGES = 20;
    private static final int CHAT_PREFIX = 2000000000;
    private static final String FUNCTION_NAME = "getHistory";

    private final MessageJsonParser mParser;
    private final Storage mStorage;
    private final int mChatId;

    @Nullable VKRequest mRequest;

    public MessageVkRequest(Storage storage, MessageJsonParser parser, int chatId) {
        mStorage = storage;
        mParser = parser;
        mChatId = chatId;
    }

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

    @Override
    VKParameters getParameters() {
        VKParameters parameters = super.getParameters();
        parameters.put(Fields.COUNT, COUNT_MESSAGES);
        parameters.put(Fields.PEER_ID, CHAT_PREFIX + mChatId);
        return parameters;
    }

    @Override
    public void execute(final Loader<List<Message>> loader) {
        VKParameters parameters = getParameters();
        mRequest = new VkApiHistory().getHistory(parameters);
        loadFromNetwork(mRequest, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    VkMessagesResponse vkMessagesResponse = mParser.to(response.json);
                    loader.onLoaded(vkMessagesResponse.getMessages(), null);
                } catch (Exception e) {
                    loader.onLoaded(null, e);
                }
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
