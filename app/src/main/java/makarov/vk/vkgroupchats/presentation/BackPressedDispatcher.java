package makarov.vk.vkgroupchats.presentation;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public class BackPressedDispatcher {

    private final AppCompatActivity mActivity;

    @Inject
    public BackPressedDispatcher(AppCompatActivity activity) {
        mActivity = activity;
    }

    public boolean onBackPressed() {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            mActivity.finish();
        }

        return true;
    }

}
