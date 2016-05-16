package makarov.vk.vkgroupchats.data;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import makarov.vk.vkgroupchats.data.query.RealmBaseQuery;

public class StorageImpl implements Storage<RealmObject, RealmBaseQuery> {

    private static boolean sInitializedRealm = false;
    private final Context mContext;

    @Inject
    public StorageImpl(Context context) {
        mContext = context;
    }

    @Override
    public RealmBaseQuery initQuery(RealmBaseQuery query) {
        query.setQuery(getRealm().where(query.getModelType()));
        return query;
    }

    @Override
    public void saveAll(List<? extends RealmObject> objects) throws StorageException {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
    }

    @Override
    public void deleteAll(List<? extends RealmObject> objects) throws StorageException {
        Realm realm = getRealm();
        synchronized (this) {
            realm.beginTransaction();
            for (int i = 0; i < objects.size(); i++) {
                RealmObject object = objects.get(i);

                if (!object.isValid()) {
                    continue;
                }
                object.removeFromRealm();
            }
            realm.commitTransaction();
        }
    }

    private Realm getRealm() {
        initIfNeeded(mContext);
        return Realm.getDefaultInstance();
    }

    public static void initIfNeeded(Context context) {
        if (!sInitializedRealm) {
            sInitializedRealm = true;
            Realm.setDefaultConfiguration(
                    new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build());
        }
    }

}