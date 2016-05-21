package makarov.vk.vkgroupchats.presentation.presenters;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.query.QueryFactory;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;

public class PresenterFactory {

    private final VkManager mVkManager;
    private final VkRequestsFactory mVkRequestsFactor;
    private final QueryFactory mQueryFactory;


    @Inject
    public PresenterFactory(VkManager vkManager, VkRequestsFactory vkRequestsFactory,
                            QueryFactory queryFactory) {
        mVkManager = vkManager;
        mVkRequestsFactor = vkRequestsFactory;
        mQueryFactory = queryFactory;
    }

    public ChatPresenter getChatPresenter(int chatId) {
        return new ChatPresenter(mVkManager, mVkRequestsFactor, mQueryFactory, chatId);
    }

}
