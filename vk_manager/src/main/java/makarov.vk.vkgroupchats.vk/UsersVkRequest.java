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
import java.util.Map;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.models.User;
import makarov.vk.vkgroupchats.vk.common.Loader;
import makarov.vk.vkgroupchats.vk.parsers.UsersJsonParser;

public class UsersVkRequest extends PaginationVkRequest<Map<Integer, List<User>>> {

    private static final String FUNCTION_NAME = "getChatUsers";

    private final UsersJsonParser mParser;
    private final Storage mStorage;
    private final int[] mChatIds;

    @Nullable
    VKRequest mRequest;

    public UsersVkRequest(Storage storage, UsersJsonParser parser, int[] chatIds) {
        mStorage = storage;
        mParser = parser;
        mChatIds = chatIds;
    }

    class VkApiDialogUsers extends VKApiMessages {

        public VKRequest getUsers(VKParameters params) {
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
        parameters.put(Fields.CHAT_IDS, getStringIds(mChatIds));
        parameters.put(Fields.FIELDS, "photo");
        return parameters;
    }

    @Override
    public void execute(final Loader<Map<Integer, List<User>>> loader) {
        VKParameters parameters = getParameters();
        mRequest = new VkApiDialogUsers().getUsers(parameters);
        loadFromNetwork(mRequest, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    VkUsersResponse vkMessagesResponse = mParser.to(response.json);
                    loader.onLoaded(vkMessagesResponse.mUsers, null);
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

    private String getStringIds(int[] ids) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < ids.length; i++) {
            builder.append(String.valueOf(ids[i]) + ", ");
        }
        return builder.substring(0, builder.length() - 2);
    }
}