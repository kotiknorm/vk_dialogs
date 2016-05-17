package makarov.vk.vkgroupchats.vk;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.Storage;

public class VkRequestsFactory {

    private final Storage mStorage;
    private final ChatJsonParser mParser;

    @Inject
    public VkRequestsFactory(Storage storage, ChatJsonParser chatJsonParser) {
        mStorage = storage;
        mParser = chatJsonParser;
    }

    public VkRequest getChats() {
        return new ChatsVkRequest(mStorage, mParser);
    }

    public VkRequest getMessages(int chatId) {
        return new MessageVkRequest(chatId);
    }


}
