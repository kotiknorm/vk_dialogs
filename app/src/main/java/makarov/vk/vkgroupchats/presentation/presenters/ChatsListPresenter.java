package makarov.vk.vkgroupchats.presentation.presenters;

import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.mvp.BasePresenter;
import makarov.vk.vkgroupchats.presentation.view.ChatsListView;
import makarov.vk.vkgroupchats.common.Loader;
import makarov.vk.vkgroupchats.vk.VkManager;

public class ChatsListPresenter extends BasePresenter<ChatsListView> {

    private final VkManager mVkManager;

    private final Loader<List<Chat>> mLoader = new Loader<List<Chat>>() {
        @Override
        public void onLoaded(List<Chat> result, Exception e) {
            if (e != null) {
                return;
            }

            getView().showChats(result);
        }
    };

    @Inject
    public ChatsListPresenter(VkManager vkManager) {
        mVkManager = vkManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        mVkManager.loadChats(mLoader);
    }

    @Override
    public void onStop() {
        super.onStop();
        mVkManager.cancel(mLoader);
    }
}
