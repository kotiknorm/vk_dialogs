package makarov.vk.vkgroupchats.vk;

import java.util.List;

import makarov.vk.vkgroupchats.data.models.Chat;

public class VkChatsResponse {

    private final int mCountChats;
    private final List<Chat> mChats;

    public VkChatsResponse(int countChats, List<Chat> chats) {
        mCountChats = countChats;
        mChats = chats;
    }

    public List<Chat> getChats() {
        return mChats;
    }

    public int getCountChats() {
        return mCountChats;
    }
}
