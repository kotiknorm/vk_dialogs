package makarov.vk.vkgroupchats.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Chat extends RealmObject {

    @PrimaryKey
    @SerializedName("chat_id")
    private int chatId;
    private Long date;
    private String title;

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

}
