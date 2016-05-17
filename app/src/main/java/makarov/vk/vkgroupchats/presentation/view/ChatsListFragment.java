package makarov.vk.vkgroupchats.presentation.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chats_list, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mChatsList.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
        attachPresenter(mChatsListPresenter);
    }

    @Override
    public void showChats(List<Chat> list) {
        ChatsAdapter adapter = new ChatsAdapter(getContext(), list);
        mChatsList.setAdapter(adapter);
    }
}
