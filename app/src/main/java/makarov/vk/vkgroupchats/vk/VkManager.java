package makarov.vk.vkgroupchats.vk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.common.Loader;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.StorageException;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.query.ChatsQuery;

public class VkManager {

    private static final String[] mScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    private static final int COUNT_CHATS = 20;
    private static final int LIMIT = 30;

    private final List<RequestEntry> mRunningRequests = new ArrayList<>();
    private final Storage mStorage;
    private final ChatJsonParser mParser;

    private final VKAccessTokenTracker mVkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {

            }
        }
    };

    @Inject
    public VkManager(Context context, Storage storage, ChatJsonParser chatJsonParser) {
        mStorage = storage;
        mVkAccessTokenTracker.startTracking();

        mParser = chatJsonParser;
    }

    public boolean login(Activity activity) {
        if (VKSdk.isLoggedIn()) {
            return true;
        }

        VKSdk.login(activity, mScope);
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data,
                                 VKCallback<VKAccessToken> callback) {
        VKSdk.onActivityResult(requestCode, resultCode, data, callback);
    }

    public void loadChats(final Loader<List<Chat>> loader) {
        ChatsQuery chatsQuery = new ChatsQuery(mStorage);
        List<Chat> chats = chatsQuery.find();
        if (chats.size() >= LIMIT) {
            loader.onLoaded(chats, null);
        }

        loadChats(loader, COUNT_CHATS, 0);
    }

    private void loadChats(final Loader<List<Chat>> loader, final int countChats, final int page) {
        VKParameters parameters = new VKParameters();
        parameters.put("count", LIMIT);
        parameters.put("offset", LIMIT * page);
        final VKRequest request = VKApi.messages().getDialogs(parameters);
        loadFromNetwork(loader, request, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VkChatsResponse vkChatsResponse = mParser.to(response.json);
                List<Chat> chats = vkChatsResponse.getChats();
                int fullCount = vkChatsResponse.getCountChats();

                try {
                    mStorage.saveAll(chats);

                    if (chats.size() >= countChats || LIMIT * page >= fullCount) {
                        ChatsQuery chatsQuery = new ChatsQuery(mStorage);
                        loader.onLoaded(chatsQuery.find(), null);
                        return;
                    }

                    loadChats(loader, countChats - chats.size(), page + 1);
                } catch (StorageException e) {
                    loader.onLoaded(null, e);
                    cancel(loader);
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                loader.onLoaded(null, error.httpError);
            }
        });
    }

    private void loadFromNetwork(Loader loader, VKRequest request,
                                 VKRequest.VKRequestListener listener) {
        mRunningRequests.add(new RequestEntry(loader, request));
        request.executeWithListener(listener);
    }

    public synchronized void cancel(Loader loader) {
        Iterator<RequestEntry> iterator = mRunningRequests.iterator();
        while (iterator.hasNext()) {
            RequestEntry entry = iterator.next();
            if (entry.mLoader.equals(loader)) {
                entry.mRequest.cancel();
                iterator.remove();
            }
        }
    }

    private static class RequestEntry {

        private final Loader mLoader;
        private final VKRequest mRequest;

        RequestEntry(Loader loader, VKRequest request) {
            mLoader = loader;
            mRequest = request;
        }
    }

}
