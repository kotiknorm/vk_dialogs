package makarov.vk.vkgroupchats.presentation.presenters;

import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.vk.PaginationVkRequest;
import makarov.vk.vkgroupchats.vk.common.Loader;
import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.query.ChatsQuery;
import makarov.vk.vkgroupchats.mvp.BasePresenter;
import makarov.vk.vkgroupchats.presentation.UiNavigator;
import makarov.vk.vkgroupchats.presentation.view.ChatView;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;

public class ChatPresenter extends BasePresenter<ChatView> {

    private final VkManager mVkManager;
    private final VkRequestsFactory mVkRequestsFactor;
    private final UiNavigator mUiNavigator;
    private final Chat mChat;
    private final PaginationVkRequest mRequest;

    private final Loader<List<Message>> mLoader = new Loader<List<Message>>() {
        @Override
        public void onLoaded(List<Message> result, Exception e) {
            if (e != null || !isAttachedToView()) {
                return;
            }
            if (result != null) {
                getView().addMessages(result);
            }

        }
    };

    @Inject
    public ChatPresenter(VkManager vkManager, VkRequestsFactory vkRequestsFactory,
                         UiNavigator uiNavigator, Storage storage, int chatId) {
        mChat = new ChatsQuery(storage).findById(chatId);
        mVkManager = vkManager;
        mVkRequestsFactor = vkRequestsFactory;
        mUiNavigator = uiNavigator;

        mRequest = mVkRequestsFactor.getMessages(mChat.getChatId());
    }

    @Override
    public void onStart() {
        super.onStart();
        mVkManager.executeRequest(mLoader, mRequest);
    }

    @Override
    public void onStop() {
        super.onStop();
        mVkManager.cancel(mLoader);
    }

    public void onLoadMore(int page, int totalItemsCount) {
        mRequest.executePage(totalItemsCount, mLoader);
    }

}