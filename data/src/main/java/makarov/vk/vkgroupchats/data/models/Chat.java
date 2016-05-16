package makarov.vk.vkgroupchats.data.models;

import io.realm.RealmObject;

public class Chat extends RealmObject {

    public Chat() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}
