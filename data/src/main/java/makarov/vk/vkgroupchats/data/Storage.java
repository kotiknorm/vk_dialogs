package makarov.vk.vkgroupchats.data;

import java.util.List;

public interface Storage<ModelType, QueryType> {

    QueryType initQuery(QueryType query);

    void saveAll(List<? extends ModelType> objects) throws StorageException;

    void deleteAll(List<? extends ModelType> object) throws StorageException;

}
