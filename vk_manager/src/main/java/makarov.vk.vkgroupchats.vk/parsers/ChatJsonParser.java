package makarov.vk.vkgroupchats.vk.parsers;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.data.utils.Assert;
import makarov.vk.vkgroupchats.vk.VkChatsResponse;
import makarov.vk.vkgroupchats.data.models.Chat;

public class ChatJsonParser implements Transfer<VkChatsResponse, JSONObject> {

    private final Gson mGson;

    public ChatJsonParser() {
        mGson = new GsonBuilder().create();
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
        return mGson.fromJson(data.toString(), Chat.class);
    }

    @Override
    public JSONObject from(VkChatsResponse data) {
        return null;
    }
}
