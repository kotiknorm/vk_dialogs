package makarov.vk.vkgroupchats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

public class GsonCreator {

    private final Gson mGson;

    @Inject
    public GsonCreator() {
        mGson = new GsonBuilder().create();
    }

    public Gson getGson() {
        return mGson;
    }
}
