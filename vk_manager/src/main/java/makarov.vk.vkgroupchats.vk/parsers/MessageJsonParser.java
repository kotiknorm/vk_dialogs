package makarov.vk.vkgroupchats.vk.parsers;

import org.json.JSONObject;

import makarov.vk.vkgroupchats.vk.VkMessagesResponse;

public class MessageJsonParser implements Transfer<VkMessagesResponse, JSONObject> {

    @Override
    public VkMessagesResponse to(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject from(VkMessagesResponse data) {
        return null;
    }
}
