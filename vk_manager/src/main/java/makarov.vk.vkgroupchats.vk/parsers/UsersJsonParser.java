package makarov.vk.vkgroupchats.vk.parsers;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import makarov.vk.vkgroupchats.data.utils.Assert;
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