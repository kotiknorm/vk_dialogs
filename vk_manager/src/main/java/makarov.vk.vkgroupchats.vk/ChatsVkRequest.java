package makarov.vk.vkgroupchats.vk;

import android.support.annotation.Nullable;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.List;

import makarov.vk.vkgroupchats.vk.common.Loader;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.query.ChatsQuery;
import makarov.vk.vkgroupchats.vk.parsers.ChatJsonParser;

public class ChatsVkRequest extends VkRequest<List<Chat>> {

    private static final int COUNT_CHATS = 20;
    private static final int CHATS_LIMIT = 30;

    private final Storage mStorage;
    private final ChatJsonParser mParser;

    @Nullable VKRequest mRequest;

    public ChatsVkRequest(Storage storage, ChatJsonParser chatJsonParser) {
        mStorage = storage;
        mParser = chatJsonParser;
    }

    public void execute(final Loader<List<Chat>> loader) {
        ChatsQuery chatsQuery = new ChatsQuery(mStorage);
        List<Chat> chats = chatsQuery.findWithSort("date");
        if (chats.size() >= COUNT_CHATS) {
            loader.onLoaded(chats, null);
            return;
        }

        loadChats(loader, COUNT_CHATS, 0);
    }

    @Override
    public void forceExecute(Loader<List<Chat>> loader) {
        loadChats(loader, COUNT_CHATS, 0);
    }

    @Override
    VKParameters getParameters() {
        VKParameters parameters = new VKParameters();
        parameters.put(Fields.COUNT, CHATS_LIMIT);
        parameters.put(Fields.PREVIEW_LENGTH, 100);
        return parameters;
    }

    @Override
    public void cancel() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }

    private void loadChats(final Loader<List<Chat>> loader, final int countChats, final int page) {
        VKParameters parameters = getParameters();
        parameters.put(Fields.OFFSET, CHATS_LIMIT * page);
        mRequest = VKApi.messages().getDialogs(parameters);
        loadFromNetwork(mRequest, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    VkChatsResponse vkChatsResponse = mParser.to(response.json);
                    List<Chat> chats = vkChatsResponse.getChats();
                    int fullCount = vkChatsResponse.getCountChats();
                    mStorage.saveAll(chats);

                    if (chats.size() >= countChats || CHATS_LIMIT * page >= fullCount) {
                        ChatsQuery chatsQuery = new ChatsQuery(mStorage);
                        loader.onLoaded(chatsQuery.findWithSort("date"), null);
                        return;
                    }

                    loadChats(loader, countChats - chats.size(), page + 1);
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

}
