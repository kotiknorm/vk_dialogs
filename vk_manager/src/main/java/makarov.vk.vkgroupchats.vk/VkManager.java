package makarov.vk.vkgroupchats.vk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.vk.common.Loader;

public class VkManager {

    private static final String[] mScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    private final List<RequestEntry> mRunningRequests = new ArrayList<>();

    private final VKAccessTokenTracker mVkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {

            }
        }
    };

    public VkManager(Context context) {
        mVkAccessTokenTracker.startTracking();
    }

    public <T>void executeRequest(final Loader<T> loader, VkRequest<T> request) {
        mRunningRequests.add(new RequestEntry(loader, request));
        request.execute(new Loader<T>() {
            @Override
            public void onLoaded(T result, Exception e) {
                loader.onLoaded(result, e);
                cancel(loader);
            }
        });
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
        private final VkRequest mRequest;

        RequestEntry(Loader loader, VkRequest request) {
            mLoader = loader;
            mRequest = request;
        }
    }

}
