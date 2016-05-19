package makarov.vk.vkgroupchats.vk.parsers;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.data.models.Photo;
import makarov.vk.vkgroupchats.data.utils.Assert;
import makarov.vk.vkgroupchats.vk.VkMessagesResponse;
import makarov.vk.vkgroupchats.vk.VkUsersResponse;

public class UsersJsonParser implements Transfer<VkUsersResponse, JSONObject> {

    private final Gson mGson;

    public UsersJsonParser() {
        GsonBuilder builder = new GsonBuilder();
        mGson = builder.create();
    }

    @Override
    @Nullable
    public VkUsersResponse to(JSONObject data) {

        try {
//            data = data.getJSONObject(Fields.RESPONSE);
            return mGson.fromJson(data.toString(), VkUsersResponse.class);
        } catch (Exception e) {
            Assert.fail(e);
            return null;
        }
    }

    @Override
    @Nullable
    public JSONObject from(VkUsersResponse data) {
        return null;
    }

}