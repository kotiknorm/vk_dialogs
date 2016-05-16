package makarov.vk.vkgroupchats.presentation.view;

import java.util.List;

import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.mvp.View;
import makarov.vk.vkgroupchats.presentation.presenters.ChatsListPresenter;

public interface ChatsListView extends View<ChatsListPresenter> {

    void showChats(List<Chat> list);
}
