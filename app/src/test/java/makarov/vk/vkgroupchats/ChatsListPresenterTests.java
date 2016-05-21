package makarov.vk.vkgroupchats;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.User;
import makarov.vk.vkgroupchats.presentation.UiNavigator;
import makarov.vk.vkgroupchats.presentation.presenters.ChatsListPresenter;
import makarov.vk.vkgroupchats.presentation.view.ChatsListView;
import makarov.vk.vkgroupchats.vk.VkManager;
import makarov.vk.vkgroupchats.vk.VkRequest;
import makarov.vk.vkgroupchats.vk.VkRequestsFactory;
import makarov.vk.vkgroupchats.vk.common.Loader;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class ChatsListPresenterTests {

    private ChatsListPresenter mChatsListPresenter;

    @Mock VkManager mVkManager;
    @Mock VkRequestsFactory mVkRequestsFactory;
    @Mock UiNavigator mUiNavigator;
    @Mock ChatsListView mChatsListView;
    @Mock Chat mChat;
    @Mock User mUser;

    private List<Chat> mChats;
    private List<User> mUsers;
    private Map<Integer, List<User>> mUsersMap;

    private static final int COUNT_CHATS = 1;
    private static final int COUNT_USERS = 10;

    @Before
    public void setUp() {
        initMocks(this);

        mChats = new ArrayList<>();
        for (int i = 0; i < COUNT_CHATS; i++) {
            mChats.add(mChat);
        }

        mUsers = new ArrayList<>();
        for (int i = 0; i < COUNT_USERS; i++) {
            mUsers.add(mUser);
        }

        mUsersMap = new HashMap<>();
        mUsersMap.put(1, mUsers);

        mChatsListPresenter = new ChatsListPresenter(mVkManager, mVkRequestsFactory, mUiNavigator);
        mChatsListPresenter.onAttachView(mChatsListView);
    }

    @Test
    public void loadChatsWithError(){
        mChatsListPresenter.onViewCreated();
        when(mChat.hasUsers()).thenReturn(true);

        ArgumentCaptor<Loader> captor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(captor.capture(), any(VkRequest.class));
        captor.getValue().onLoaded(null, new Exception());
        verify(mChatsListView).showError();
    }

    @Test
    public void loadChatsWithUsers(){
        mChatsListPresenter.onViewCreated();
        when(mChat.hasUsers()).thenReturn(true);

        ArgumentCaptor<Loader> captor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(captor.capture(), any(VkRequest.class));
        captor.getValue().onLoaded(mChats, null);
        verify(mChatsListView).showChats(mChats);
    }

    @Test
    public void loadChatsWithoutUsers(){
        mChatsListPresenter.onViewCreated();
        when(mChat.hasUsers()).thenReturn(false);

        ArgumentCaptor<Loader> captor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(captor.capture(), any(VkRequest.class));
        captor.getValue().onLoaded(mChats, null);
        verify(mChatsListView, times(0)).showChats(mChats);
    }

    @Test
    public void loadChatsAndUsers(){
        mChatsListPresenter.onViewCreated();
        when(mChat.hasUsers()).thenReturn(false);

        ArgumentCaptor<Loader> chatsCaptor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(chatsCaptor.capture(), any(VkRequest.class));
        reset(mVkManager);
        chatsCaptor.getValue().onLoaded(mChats, null);

        ArgumentCaptor<Loader> usersCaptor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(usersCaptor.capture(), any(VkRequest.class));
        usersCaptor.getValue().onLoaded(mUsersMap, null);

        verify(mChatsListView).showChats(mChats);
    }

    @Test
    public void loadChatsAndUsersWithError(){
        mChatsListPresenter.onViewCreated();
        when(mChat.hasUsers()).thenReturn(false);

        ArgumentCaptor<Loader> chatsCaptor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(chatsCaptor.capture(), any(VkRequest.class));
        reset(mVkManager);
        chatsCaptor.getValue().onLoaded(mChats, null);

        ArgumentCaptor<Loader> usersCaptor =
                ArgumentCaptor.forClass(Loader.class);

        verify(mVkManager).executeRequest(usersCaptor.capture(), any(VkRequest.class));
        usersCaptor.getValue().onLoaded(null, new Exception());

        verify(mChatsListView).showError();
    }
}