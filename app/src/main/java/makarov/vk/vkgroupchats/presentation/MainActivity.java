package makarov.vk.vkgroupchats.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.ioc.chats.ChatsInjector;
import makarov.vk.vkgroupchats.mvp.ComponentContainer;

public class MainActivity extends AppCompatActivity implements ComponentContainer<ChatsComponent> {

    @Inject UiNavigator mUiNavigator;
    @Inject BackPressedDispatcher mBackPressedDispatcher;

    private ChatsInjector mChatsInjector;

    private void initComponent() {
        mChatsInjector = new ChatsInjector(this);
        mChatsInjector.buildComponent();
        mChatsInjector.get().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initComponent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatsInjector.destroyComponent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiNavigator.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUiNavigator.onStart();
    }

    public void onBackPressed() {
        if (!mBackPressedDispatcher.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public ChatsComponent getComponent() {
        return mChatsInjector.get();
    }
}
