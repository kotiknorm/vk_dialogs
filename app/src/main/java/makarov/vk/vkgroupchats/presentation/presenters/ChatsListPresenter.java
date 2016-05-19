package makarov.vk.vkgroupchats.presentation.presenters;

import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.models.Chat;
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

    private final Loader<List<Chat>> mLoader = new Loader<List<Chat>>() {
        @Override
        public void onLoaded(List<Chat> result, Exception e) {
            if (e != null || !isAttachedToView()) {
                return;
            }
            getView().showChats(result);
        }
    };

    @Inject
    public ChatsListPresenter(VkManager vkManager, VkRequestsFactory vkRequestsFactory,
                              UiNavigator uiNavigator) {
        mVkManager = vkManager;
        mVkRequestsFactor = vkRequestsFactory;
        mUiNavigator = uiNavigator;
    }

    public void onClickChat(Chat chat) {
        mUiNavigator.showChat(chat);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        mVkManager.executeRequest(mLoader, mVkRequestsFactor.getChats());
    }

    @Override
    public void onStop() {
        super.onStop();
        mVkManager.cancel(mLoader);
    }
}
