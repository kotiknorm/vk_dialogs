package makarov.vk.vkgroupchats.presentation.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;

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

public class ChatFragment extends MvpFragment<ChatPresenter, ChatsComponent>
        implements ChatView {

    public static final String CHAT_ID_EXTRA = "CHAT_ID";

    @Inject PresenterFactory mPresenterFactory;

    @Bind(R.id.chat_list)
    SuperListview mMessagesList;

    private MessagesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new MessagesAdapter(getContext());
        mMessagesList.setAdapter(mAdapter);

        mMessagesList.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                getPresenter().onLoadMore(0, numberOfItems);
            }}, 40);

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

    @Override
    public void showProgressBar() {
        mMessagesList.showMoreProgress();
    }

    @Override
    public void hideProgressBar() {
        mMessagesList.hideMoreProgress();
    }

}
