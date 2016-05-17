package makarov.vk.vkgroupchats.presentation.view;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;
import makarov.vk.vkgroupchats.presentation.presenters.PresenterFactory;

public class ChatFragment extends MvpFragment<ChatPresenter, ChatsComponent>
        implements ChatView {

    public static final String CHAT_ID_EXTRA = "CHAT_ID";

    @Inject
    PresenterFactory mPresenterFactory;

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mPresenterFactory.getChatPresenter(
                getArguments() != null ? getArguments().getInt(CHAT_ID_EXTRA) : null));
    }
}
