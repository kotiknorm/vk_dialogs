package makarov.vk.vkgroupchats.presentation.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.adapters.MessagesAdapter;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;
import makarov.vk.vkgroupchats.presentation.presenters.PresenterFactory;
import makarov.vk.vkgroupchats.presentation.widget.EndlessScrollListener;

public class ChatFragment extends MvpFragment<ChatPresenter, ChatsComponent>
        implements ChatView {

    public static final String CHAT_ID_EXTRA = "CHAT_ID";

    @Inject PresenterFactory mPresenterFactory;

    @Bind(R.id.chat_list)
    RecyclerView mMessagesList;

    private MessagesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mMessagesList.setLayoutManager(linearLayoutManager);

        mAdapter = new MessagesAdapter(getContext());
        mMessagesList.setAdapter(mAdapter);

        mMessagesList.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getPresenter().onLoadMore(page, totalItemsCount);
            }
        });

        return view;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mPresenterFactory.getChatPresenter(
                getArguments() != null ? getArguments().getInt(CHAT_ID_EXTRA) : null));
    }

    @Override
    public void addMessages(List<Message> list) {
        mAdapter.addItems(list);
    }

}
