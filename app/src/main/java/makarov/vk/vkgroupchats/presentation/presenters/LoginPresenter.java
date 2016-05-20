package makarov.vk.vkgroupchats.presentation.presenters;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.mvp.BasePresenter;
import makarov.vk.vkgroupchats.presentation.UiNavigator;
import makarov.vk.vkgroupchats.presentation.view.LoginView;
import makarov.vk.vkgroupchats.vk.VkManager;

public class LoginPresenter extends BasePresenter<LoginView> {

    private final UiNavigator mUiNavigator;

    @Inject
    public LoginPresenter(VkManager vkManager, UiNavigator uiNavigator, Storage storage) {
        mUiNavigator = uiNavigator;
    }

    public void login() {
        mUiNavigator.login();
    }
}
