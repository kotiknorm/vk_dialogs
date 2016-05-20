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

import makarov.vk.vkgroupchats.vk.common.Loader;

public class VkManager {

    public static final String API_VERSION = "5.52";

    private static final String[] SCOPE = new String[]{
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

    public static void init(Context context) {
        VKSdk.customInitialize(context,
                context.getResources().getInteger(R.integer.com_vk_sdk_AppId), API_VERSION);
    }

    public static String getUserId() {
        return VKAccessToken.currentToken().userId;
    }

    public <T>void executeRequest(final Loader<T> loader, VkRequest<T> request) {
        mRunningRequests.add(new RequestEntry(loader, request));
        request.execute(new ResultHandler<>(loader));
    }

    public <T>void forceExecuteRequest(final Loader<T> loader, VkRequest<T> request) {
        mRunningRequests.add(new RequestEntry(loader, request));
        request.forceExecute(new ResultHandler<>(loader));
    }

    public boolean login(Activity activity) {
        if (VKSdk.isLoggedIn()) {
            return true;
        }

        VKSdk.login(activity, SCOPE);
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

    private class ResultHandler<T> implements Loader<T> {

        private final Loader<T> mLoader;

        ResultHandler(Loader<T> loader) {
            mLoader = loader;
        }

        @Override
        public void onLoaded(T result, Exception e) {
            mLoader.onLoaded(result, e);
            cancel(mLoader);
        }
    }

}
