package makarov.vk.vkgroupchats.vk;

import android.support.annotation.VisibleForTesting;

import java.util.List;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.vk.parsers.ChatJsonParser;
import makarov.vk.vkgroupchats.vk.parsers.MessageJsonParser;
import makarov.vk.vkgroupchats.vk.parsers.UsersJsonParser;

public class VkRequestsFactory {

    private final Storage mStorage;
    private final ChatJsonParser mChatJsonParser;
    private final MessageJsonParser mMessageJsonParser;
    private final UsersJsonParser mUsersJsonParser;

    public VkRequestsFactory(Storage storage) {
        mStorage = storage;
        mChatJsonParser = new ChatJsonParser();
        mMessageJsonParser = new MessageJsonParser();
        mUsersJsonParser = new UsersJsonParser();
    }

    @VisibleForTesting
    VkRequestsFactory(Storage storage, ChatJsonParser chatJsonParser,
                      MessageJsonParser messageJsonParser,
                      UsersJsonParser usersJsonParser) {
        mStorage = storage;
        mChatJsonParser = chatJsonParser;
        mMessageJsonParser = messageJsonParser;
        mUsersJsonParser = usersJsonParser;
    }

    public VkRequest getChats() {
        return new ChatsVkRequest(mStorage, mChatJsonParser);
    }

    public VkRequest getUsers(List<Integer> ids) {
        return new UsersVkRequest(mStorage, mUsersJsonParser, ids);
    }

    public PaginationVkRequest getMessages(int chatId) {
        return new MessageVkRequest(mStorage, mMessageJsonParser, chatId);
    }

}
