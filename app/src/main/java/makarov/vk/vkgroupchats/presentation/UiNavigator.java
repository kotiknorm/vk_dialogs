package makarov.vk.vkgroupchats.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.api.VKError;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Chat;
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
                showChatsList();
            }

            @Override
            public void onError(VKError error) {

            }
        });
    }

    public void onStart() {
        if (mVkManager.login(mActivity)) {
            showChatsList();
        }
    }

    public ChatsListView showChatsList() {
        ChatsListFragment fragment = new ChatsListFragment();
        addView(fragment, true);
        return fragment;
    }

    public ChatView showChat(Chat chat) {
        ChatFragment fragment = new ChatFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ChatFragment.CHAT_ID_EXTRA, chat.getChatId());

        fragment.setArguments(bundle);
        addView(fragment, false);
        return fragment;
    }

    private void addView(Fragment fragment, boolean withReplace) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        if (withReplace) {
            transaction.replace(R.id.parent_container, fragment);
        } else {
            transaction.add(R.id.parent_container, fragment);
        }

        transaction.addToBackStack("stack");
        transaction.commitAllowingStateLoss();
    }
}
