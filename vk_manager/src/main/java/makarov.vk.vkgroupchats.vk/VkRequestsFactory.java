package makarov.vk.vkgroupchats.vk;

import android.support.annotation.VisibleForTesting;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.vk.parsers.ChatJsonParser;
import makarov.vk.vkgroupchats.vk.parsers.MessageJsonParser;

public class VkRequestsFactory {

    private final Storage mStorage;
    private final ChatJsonParser mChatJsonParser;
    private final MessageJsonParser mMessageJsonParser;

    public VkRequestsFactory(Storage storage) {
        mStorage = storage;
        mChatJsonParser = new ChatJsonParser();
        mMessageJsonParser = new MessageJsonParser();
    }

    @VisibleForTesting
    VkRequestsFactory(Storage storage, ChatJsonParser chatJsonParser,
                      MessageJsonParser messageJsonParser) {
        mStorage = storage;
        mChatJsonParser = chatJsonParser;
        mMessageJsonParser = messageJsonParser;
    }

    public VkRequest getChats() {
        return new ChatsVkRequest(mStorage, mChatJsonParser);
    }

    public VkRequest getMessages(int chatId) {
        return new MessageVkRequest(mMessageJsonParser, chatId);
    }

}
