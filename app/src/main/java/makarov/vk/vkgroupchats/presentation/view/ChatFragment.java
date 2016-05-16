package makarov.vk.vkgroupchats.presentation.view;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;

public class ChatFragment extends MvpFragment<ChatPresenter, ChatsComponent>
        implements ChatView {

    @Inject ChatPresenter mChatPresenter;

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mChatPresenter);
    }
}
