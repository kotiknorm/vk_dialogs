package makarov.vk.vkgroupchats.data.models;

import android.text.TextUtils;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @PrimaryKey
    private int id;
    private String body;
    private Long date;
    private String userId;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String fromId;

    public RealmList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        RealmList<Photo> list = new RealmList<>();

        for (Photo photo : photos) {
            list.add(photo);
        }

        this.photos = list;
    }

    private RealmList<Photo> photos;

    public Message() {

    }

    public boolean hasBody() {
        return !TextUtils.isEmpty(body);
    }

    public boolean hasPhotos() {
        return photos != null && !photos.isEmpty();
    }
}
