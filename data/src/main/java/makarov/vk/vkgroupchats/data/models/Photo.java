package makarov.vk.vkgroupchats.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Photo extends RealmObject {

    @PrimaryKey
    private int id;

    @SerializedName("photo_604")
    private String photo604;

    @SerializedName("photo_807")
    private String photo807;

    @SerializedName("photo_1280")
    private String photo1280;
    private String width;
    private String height;

    public Photo() {

    }

    public String getPhotoUrl() {
        return photo807;
    }
}
