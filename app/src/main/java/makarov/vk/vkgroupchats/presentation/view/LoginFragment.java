package makarov.vk.vkgroupchats.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.presenters.LoginPresenter;

public class LoginFragment extends MvpFragment<LoginPresenter, ChatsComponent>
        implements LoginView {

    @Inject LoginPresenter mLoginPresenter;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.login_btn) Button mLoginButton;

    @OnClick(R.id.login_btn)
    public void onClick() {
        mLoginPresenter.login();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        prepareToolbar();

        return view;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mLoginPresenter);
    }

    private void prepareToolbar() {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity == null) {
            return;
        }

        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }

}
