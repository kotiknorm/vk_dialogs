package makarov.vk.vkgroupchats.presentation.view;

import java.util.List;

import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.mvp.View;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;

public interface ChatView extends View<ChatPresenter> {

    void addMessages(List<Message> list);

    void showProgressBar();

    void hideProgressBar();

    void prepareChat(Chat chat);

    void showError();
}
