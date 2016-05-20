package makarov.vk.vkgroupchats.presentation.presenters;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.User;
import makarov.vk.vkgroupchats.mvp.BasePresenter;
import makarov.vk.vkgroupchats.presentation.UiNavigator;
import makarov.vk.vkgroupchats.presentation.view.ChatsListView;
import makarov.vk.vkgroupchats.vk.common.Loader;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;

public class ChatsListPresenter extends BasePresenter<ChatsListView> {

    private final VkManager mVkManager;
    private final VkRequestsFactory mVkRequestsFactor;
    private final UiNavigator mUiNavigator;
    private final Storage mStorage;

    @Nullable private List<Chat> mChats;

    private final Loader<List<Chat>> mChatsLoader = new Loader<List<Chat>>() {
        @Override
        public void onLoaded(List<Chat> result, Exception e) {
            if (e != null || !isAttachedToView()) {
                return;
            }

            mChats = result;
            List<Integer> chatsWithoutLoadedUsers = getIdsChats(result);
            if (!chatsWithoutLoadedUsers.isEmpty()) {

                mVkManager.executeRequest(mUsersLoader,
                        mVkRequestsFactor.getUsers(chatsWithoutLoadedUsers));
            } else {
                getView().showChats(mChats);
            }

        }
    };

    private final Loader<Map<Integer, List<User>>> mUsersLoader = new Loader<Map<Integer, List<User>>>() {
        @Override
        public void onLoaded(final Map<Integer, List<User>> result, Exception e) {
            if (e != null || !isAttachedToView()) {
                return;
            }

            if (mChats == null || result == null) {
                return;
            }

            getView().showChats(mChats);

        }
    };

    @Inject
    public ChatsListPresenter(VkManager vkManager, VkRequestsFactory vkRequestsFactory,
                              UiNavigator uiNavigator, Storage storage) {
        mVkManager = vkManager;
        mVkRequestsFactor = vkRequestsFactory;
        mUiNavigator = uiNavigator;
        mStorage = storage;
    }

    public void onClickChat(Chat chat) {
        mUiNavigator.showChat(chat);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        mVkManager.executeRequest(mChatsLoader, mVkRequestsFactor.getChats());
    }

    @Override
    public void onStop() {
        super.onStop();
        mVkManager.cancel(mChatsLoader);
    }

    private static List<Integer> getIdsChats(List<Chat> chats) {
        List<Integer> ids = new ArrayList<>();
        for (int i  = 0; i < chats.size(); i++) {
            if (!chats.get(i).hasUsers())
                ids.add(chats.get(i).getChatId());
        }

        return ids;
    }
}
