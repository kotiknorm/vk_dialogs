package makarov.vk.vkgroupchats.vk;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import makarov.vk.vkgroupchats.data.models.User;

public class VkUsersResponse {

    @SerializedName("response")
    public Map<Integer, List<User>> mUsers;
}
