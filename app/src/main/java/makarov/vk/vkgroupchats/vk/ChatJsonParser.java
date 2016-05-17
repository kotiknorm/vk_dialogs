package makarov.vk.vkgroupchats.vk;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.GsonCreator;
import makarov.vk.vkgroupchats.common.Transfer;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.utils.Assert;

public class ChatJsonParser implements Transfer<VkChatsResponse, JSONObject> {

    private final GsonCreator mGsonCreator;

    @Inject
    public ChatJsonParser(GsonCreator gsonCreator) {
        mGsonCreator = gsonCreator;
    }

    @Override
    public VkChatsResponse to(JSONObject data) {
        List<Chat> chats = new ArrayList<>();
        int fullCount = 0;

        try {
            data = data.getJSONObject("response");
            fullCount = data.getInt("count");

            JSONArray dialogs = data.getJSONArray("items");
            for(int i = 0; i < dialogs.length(); i++) {
                JSONObject dialog = dialogs.getJSONObject(i).getJSONObject("message");
                if (isGroupChat(dialog)) {
                    Chat chat = parseChat(dialog);
                    if (chat != null) {
                        chats.add(chat);
                    }
                }
            }

        } catch (JSONException e) {
            Assert.fail(e);
        }

        return new VkChatsResponse(fullCount, chats);

    }

    private boolean isGroupChat(@Nullable JSONObject data) {
        try {
            return data != null && data.has("users_count") && data.getInt("users_count") > 1;
        } catch (JSONException e) {
            return false;
        }
    }

    @Nullable
    private Chat parseChat(JSONObject data) {
        return mGsonCreator.getGson().fromJson(data.toString(), Chat.class);
    }

    @Override
    public JSONObject from(VkChatsResponse data) {
        return null;
    }
}
