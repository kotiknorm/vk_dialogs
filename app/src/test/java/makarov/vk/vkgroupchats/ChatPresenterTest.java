package makarov.vk.vkgroupchats;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.data.query.ChatsQuery;
import makarov.vk.vkgroupchats.data.query.QueryFactory;
import makarov.vk.vkgroupchats.presentation.presenters.ChatPresenter;
import makarov.vk.vkgroupchats.presentation.view.ChatView;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequest;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;
import makarov.vk.vkgroupchats.vk.common.Loader;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ChatPresenterTest {

    private ChatPresenter mChatPresenter;

    @Mock VkManager mVkManager;
    @Mock VkRequestsFactory mVkRequestsFactory;
    @Mock QueryFactory mQueryFactory;
    @Mock ChatView mChatView;
    @Mock ChatsQuery mChatsQuery;
    @Mock Chat mChat;
    @Mock Message mMessage;

    private List<Message> mMessages;

    private static final int COUNT_MESSAGES = 10;

    @Before
    public void setUp() {
        initMocks(this);

        when(mQueryFactory.getChatsQuery()).thenReturn(mChatsQuery);
        when(mChatsQuery.findById(1)).thenReturn(mChat);

        mMessages = new ArrayList<>();
        for (int i = 0; i < COUNT_MESSAGES; i++) {
            mMessages.add(mMessage);
        }

        mChatPresenter = new ChatPresenter(mVkManager, mVkRequestsFactory,
                mQueryFactory, 1);
        mChatPresenter.onAttachView(mChatView);
    }

    @Test
    public void loadMessages(){
        mChatPresenter.onStart();
        ArgumentCaptor<Loader> chatsCaptor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(chatsCaptor.capture(), any(VkRequest.class));
        chatsCaptor.getValue().onLoaded(mMessages, null);
        verify(mChatView).addMessages(mMessages);
    }

    @Test
    public void loadMessagesWithError(){
        mChatPresenter.onStart();
        ArgumentCaptor<Loader> chatsCaptor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(chatsCaptor.capture(), any(VkRequest.class));
        chatsCaptor.getValue().onLoaded(null, new Exception());
        verify(mChatView, times(0)).addMessages(mMessages);
        verify(mChatView).hideProgressBar();
    }

}
