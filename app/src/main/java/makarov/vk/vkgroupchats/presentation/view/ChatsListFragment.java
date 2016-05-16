package makarov.vk.vkgroupchats.presentation.view;

import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.presenters.ChatsListPresenter;

public class ChatsListFragment extends MvpFragment<ChatsListPresenter, ChatsComponent>
        implements ChatsListView {

    @Inject ChatsListPresenter mChatsListPresenter;

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mChatsListPresenter);
    }

    @Override
    public void showChats(List<Chat> list) {

    }
}
