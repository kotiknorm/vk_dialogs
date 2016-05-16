package makarov.vk.vkgroupchats.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class MvpFragment<PresenterType extends Presenter, ComponentType> extends Fragment
        implements View<PresenterType> {

    @Nullable private PresenterType mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onAttachView(this);
    }

    protected PresenterType getPresenter() {
        return mPresenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetachView();
    }

    @Override
    public void attachPresenter(PresenterType presenterType) {
        mPresenter = presenterType;
    }

    @Override
    public boolean isValidView() {
        return getActivity() != null && !getActivity().isFinishing() && isAdded();
    }

    @SuppressWarnings("unchecked")
    protected ComponentType getComponent() {
        return ((ComponentContainer<ComponentType>) getActivity()).getComponent();
    }

    protected abstract void inject();

}
