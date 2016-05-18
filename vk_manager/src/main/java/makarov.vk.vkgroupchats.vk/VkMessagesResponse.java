package makarov.vk.vkgroupchats.vk;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import makarov.vk.vkgroupchats.data.models.Message;

public class VkMessagesResponse {

    @SerializedName("count")
    private int mCountMessages;

    @SerializedName("items")
    private List<Message> mMessages;

    public List<Message> getMessages() {
        return mMessages;
    }

    public int getCountMessages() {
        return mCountMessages;
    }


}
