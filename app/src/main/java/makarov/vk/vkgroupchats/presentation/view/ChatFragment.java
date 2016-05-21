package makarov.vk.vkgroupchats.presentation.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.ioc.chats.ChatsComponent;
import makarov.vk.vkgroupchats.mvp.MvpFragment;
import makarov.vk.vkgroupchats.presentation.BackPressedDispatcher;
import makarov.vk.vkgroupchats.presentation.adapters.MessagesAdapter;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;
import makarov.vk.vkgroupchats.presentation.presenters.PresenterFactory;

public class ChatFragment extends MvpFragment<ChatPresenter, ChatsComponent>
        implements ChatView {

    public static final String CHAT_ID_EXTRA = "CHAT_ID";

    @Inject PresenterFactory mPresenterFactory;
    @Inject BackPressedDispatcher mBackPressedDispatcher;

    @Bind(R.id.chat_list) SuperListview mMessagesList;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.coordinator) CoordinatorLayout mCoordinatorLayout;

    private MessagesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        ButterKnife.bind(this, view);
        prepareToolbar();

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

    @Override
    public void prepareChat(Chat chat) {
        mToolbar.setTitle(chat.getTitle());
        int countMembers = chat.getUsers().size();
        String subTitle = getResources().getQuantityString(R.plurals.members,
                countMembers, countMembers);
        mToolbar.setSubtitle(subTitle);
    }

    @Override
    public void showError() {
        Snackbar.make(mCoordinatorLayout, R.string.error, Snackbar.LENGTH_LONG).show();
    }

    private void prepareToolbar() {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity == null) {
            return;
        }

        activity.setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackPressedDispatcher.onBackPressed();
            }
        });

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

}
