package makarov.vk.vkgroupchats.mvp;

import android.support.annotation.Nullable;

public abstract class BasePresenter<ViewType extends View> implements Presenter<ViewType> {

    @Nullable private ViewType mView;

    @Override
    public ViewType getView() {
        return mView;
    }

    @Override
    public boolean isAttachedToView() {
        return mView != null;
    }

    @Override
    public void onAttachView(ViewType view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onViewCreated() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

}
