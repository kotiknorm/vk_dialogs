package makarov.vk.vkgroupchats.vk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.common.Loader;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.StorageException;
import makarov.vk.vkgroupchats.data.models.Chat;

public class VkManager {

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
    public VkManager(Context context, Storage storage) {
        mStorage = storage;
        mVkAccessTokenTracker.startTracking();

        mParser = new ChatJsonParser();
    }

    public void login(Activity activity) {
        VKSdk.login(activity, null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data,
                                 VKCallback<VKAccessToken> callback) {
        VKSdk.onActivityResult(requestCode, resultCode, data, callback);
    }

    public void loadChats(final Loader<List<Chat>> loader) {
        final VKRequest request = VKApi.messages().getDialogs();
        loadFromNetwork(loader, request, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                List<Chat> chats = mParser.to(response.json);
                try {
                    mStorage.saveAll(chats);
                    loader.onLoaded(null, null);
                } catch (StorageException e) {
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

    private void loadFromNetwork(Loader loader, VKRequest request,
                                 VKRequest.VKRequestListener listener) {
        mRunningRequests.add(new RequestEntry(loader, request));
        request.executeWithListener(listener);
    }

    public void cancel(Loader loader) {
        for (RequestEntry entry : mRunningRequests) {
            if (entry.mLoader.equals(loader)) {
                entry.mRequest.cancel();
                mRunningRequests.remove(entry);
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
