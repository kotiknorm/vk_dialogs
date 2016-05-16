package makarov.vk.vkgroupchats.vk;

import org.json.JSONObject;

import java.util.List;

import makarov.vk.vkgroupchats.common.Transfer;
import makarov.vk.vkgroupchats.data.models.Chat;

public class ChatJsonParser implements Transfer<List<Chat>, JSONObject> {

    @Override
    public List<Chat> to(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject from(List<Chat> data) {
        return null;
    }
}
