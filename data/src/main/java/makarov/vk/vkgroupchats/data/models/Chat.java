package makarov.vk.vkgroupchats.data.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Chat extends RealmObject {

    @PrimaryKey
    @SerializedName("chat_id")
    private int chatId;
    private Long date;
    private String title;
    private String body;
    private RealmList<User> mUsers;

    @SerializedName("users_count")
    private int usersCount;

    @SerializedName("photo_50")
    private String photo50;

    @SerializedName("photo_100")
    private String photo100;

    @SerializedName("photo_200")
    private String photo200;

    public Chat(int chatId, Long date, String title, int usersCount) {
        this.chatId = chatId;
        this.date = date;
        this.title = title;
        this.usersCount = usersCount;
    }

    public Chat() {

    }

    public int getChatId() {
        return chatId;
    }

    public String getTitle() {
        return title;
    }

    public Long getDate() {
        return date;
    }

    public void setChatId(int chat) {
        this.chatId = chat;
    }

    public String getPhoto() {
        return photo100;
    }

    public RealmList<User> getUsers() {
        return mUsers;
    }


    public void setUsers(List<User> users) {
        mUsers = toRealmList(Realm.getDefaultInstance(), users);
    }

    public RealmList<User> toRealmList(Realm realm, List<User> arrayList) {
        RealmList realmList = new RealmList<>();
        for (int i = 0; i < arrayList.size(); i++){
            User user = arrayList.get(i);
            user = realm.copyToRealm(user);
            realmList.add(user);
        }
        return realmList;
    }

    public boolean hasUsers() {
        return mUsers != null && mUsers.size() > 0;
    }

    @Nullable
    public User getUser(String userId) {
        for (User user : mUsers) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        return null;
    }

    public String getBody() {
        return body;
    }


}
