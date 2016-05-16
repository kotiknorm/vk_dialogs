package makarov.vk.vkgroupchats.data.query;

import makarov.vk.vkgroupchats.data.Storage;
import makarov.vk.vkgroupchats.data.models.Chat;

public class ChatsQuery extends RealmBaseQuery<Chat> {

    public ChatsQuery(Storage realmStorage, String kidUuid) {
        super(realmStorage, Chat.class);
        mRealmQuery.contains("kidUuid", kidUuid);
    }

}