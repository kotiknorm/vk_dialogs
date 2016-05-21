package makarov.vk.vkgroupchats.presentation.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.adapters.ChatsAdapter;
import makarov.vk.vkgroupchats.presentation.presenters.ChatsListPresenter;

public class ChatsListFragment extends MvpFragment<ChatsListPresenter, ChatsComponent>
        implements ChatsListView {

    @Inject ChatsListPresenter mChatsListPresenter;

    @Bind(R.id.chats_list) RecyclerView mChatsList;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.logout) Button mLogoutBtn;
    @Bind(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.coordinator) CoordinatorLayout mCoordinatorLayout;

    SwipeRefreshLayout.OnRefreshListener monRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getPresenter().updateChatsList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chats_list, container, false);
        ButterKnife.bind(this, view);

        mRefreshLayout.setOnRefreshListener(monRefreshListener);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mChatsList.setLayoutManager(linearLayoutManager);

        return view;
    }

    @OnClick(R.id.logout)
    void onClickLogout() {
        mChatsListPresenter.logout();
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mChatsListPresenter);
    }

    @Override
    public void showChats(List<Chat> list) {
        mRefreshLayout.setRefreshing(false);
        ChatsAdapter adapter = new ChatsAdapter(getContext(), list, mChatsListPresenter);
        mChatsList.setAdapter(adapter);
    }

    @Override
    public void showError() {
        Snackbar.make(mCoordinatorLayout, R.string.error, Snackbar.LENGTH_LONG).show();
        mRefreshLayout.setRefreshing(false);
    }

}
