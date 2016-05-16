package makarov.vk.vkgroupchats.presentation;

import javax.inject.Inject;

public class BackPressedDispatcher {

    @Inject
    public BackPressedDispatcher() {

    }

    public boolean onBackPressed() {
        return false;
    }
}
