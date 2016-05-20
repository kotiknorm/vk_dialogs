package makarov.vk.vkgroupchats.data.query;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.Sort;
import makarov.vk.vkgroupchats.data.Storage;

public abstract class RealmBaseQuery<T extends RealmObject> {

    protected RealmQuery<T> mRealmQuery;
    private Class<T> mType;

    public RealmBaseQuery(Storage realmStorage, Class<T> type) {
        mType = type;
        realmStorage.initQuery(this);
    }

    public void setQuery(RealmQuery<T> realmQuery) {
        mRealmQuery = realmQuery;
    }

    public T findFirst() {
        return mRealmQuery.findFirst();
    }

    public List<T> find() {
        return mRealmQuery.findAll();
    }

    public List<T> findWithSort(String fieldName) {
        return mRealmQuery.findAll().sort(fieldName, Sort.DESCENDING);
    }

    public Class getModelType() {
        return mType;
    }

}
