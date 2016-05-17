package makarov.vk.vkgroupchats.presentation.presenters;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.presentation.UiNavigator;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;

public class PresenterFactory {

    private final VkManager mVkManager;
    private final VkRequestsFactory mVkRequestsFactor;
    private final UiNavigator mUiNavigator;
    private final Storage mStorage;


    @Inject
    public PresenterFactory(VkManager vkManager, VkRequestsFactory vkRequestsFactory,
                            UiNavigator uiNavigator, Storage storage) {
        mVkManager = vkManager;
        mVkRequestsFactor = vkRequestsFactory;
        mUiNavigator = uiNavigator;
        mStorage = storage;
    }

    public ChatPresenter getChatPresenter(int chatId) {
        return new ChatPresenter(mVkManager, mVkRequestsFactor, mUiNavigator,
                mStorage, chatId);
    }

}
