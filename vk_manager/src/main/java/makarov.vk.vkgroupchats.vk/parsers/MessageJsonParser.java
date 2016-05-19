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

public class MessageJsonParser implements Transfer<VkMessagesResponse, JSONObject> {

    private final Gson mGson;

    public MessageJsonParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Message.class, new MessageDeserializer());

        mGson = builder.create();
    }

    @Override
    @Nullable
    public VkMessagesResponse to(JSONObject data) {

        try {
            data = data.getJSONObject(Fields.RESPONSE);
            return mGson.fromJson(data.toString(), VkMessagesResponse.class);
        } catch (JSONException e) {
            Assert.fail(e);
            return null;
        }
    }

    @Override
    @Nullable
    public JSONObject from(VkMessagesResponse data) {
        return null;
    }

    class MessageDeserializer implements JsonDeserializer<Message> {

        @Override
        public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            final int id = jsonObject.get(Fields.ID).getAsInt();
            final String body = jsonObject.get(Fields.BODY).getAsString();
            final Long date = jsonObject.get(Fields.DATE).getAsLong();
            final String userId = jsonObject.get(Fields.USER_ID).getAsString();
            final String fromId = jsonObject.get(Fields.FROM_ID).getAsString();

            Message message = new Message();
            message.setBody(body);
            message.setDate(date);
            message.setId(id);
            message.setUserId(userId);
            message.setFromId(fromId);

            List<Photo> photos = new ArrayList<>();

            if (jsonObject.has(Fields.ATTACHMENTS)) {
                final JsonArray attachments = jsonObject.get(Fields.ATTACHMENTS).getAsJsonArray();
                for (int i = 0; i < attachments.size(); i++) {
                    final JsonObject attachment = attachments.get(i).getAsJsonObject();
                    final String type = attachment.get(Fields.TYPE).getAsString();
                    if (type.equals(Fields.PHOTO)) {
                        Photo photo = mGson.fromJson(attachment.get(Fields.PHOTO), Photo.class);
                        photos.add(photo);
                    }

                }
            }

            message.setPhotos(photos);
            return message;
        }
    }
}
