package makarov.vk.vkgroupchats.presentation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.api.VKError;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.presentation.view.ChatFragment;
import makarov.vk.vkgroupchats.presentation.view.ChatView;
import makarov.vk.vkgroupchats.presentation.view.ChatsListFragment;
import makarov.vk.vkgroupchats.presentation.view.ChatsListView;
import makarov.vk.vkgroupchats.vk.VkManager;

public class UiNavigator {

    private final AppCompatActivity mActivity;
    private final VkManager mVkManager;

    @Inject
    public UiNavigator(AppCompatActivity activity, VkManager vkManager) {
        mActivity = activity;
        mVkManager = vkManager;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mVkManager.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

            }

            @Override
            public void onError(VKError error) {

            }
        });
    }

    public void onStart() {
        mVkManager.login(mActivity);
    }

    public ChatsListView showChatsList() {
        ChatsListFragment fragment = new ChatsListFragment();
        addView(fragment);
        return fragment;
    }

    public ChatView showChat() {
        ChatFragment fragment = new ChatFragment();
        addView(fragment);
        return fragment;
    }

    private void addView(Fragment fragment) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.parent_container, fragment);
        transaction.commitAllowingStateLoss();
    }
}
