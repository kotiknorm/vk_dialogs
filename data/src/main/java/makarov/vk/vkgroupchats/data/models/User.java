package makarov.vk.vkgroupchats.data.models;

import io.realm.RealmObject;

public class User extends RealmObject {

    private String id;
    private String photo;

    public User() {

    }

    public String getPhoto() {
        return photo;
    }

}
