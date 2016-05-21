package makarov.vk.vkgroupchats.data.query;

import javax.inject.Inject;

import makarov.vk.vkgroupchats.data.Storage;

public class QueryFactory {

    private final Storage mStorage;

    @Inject
    public QueryFactory(Storage storage) {
        mStorage = storage;
    }

    public ChatsQuery getChatsQuery() {
        return new ChatsQuery(mStorage);
    }
}
