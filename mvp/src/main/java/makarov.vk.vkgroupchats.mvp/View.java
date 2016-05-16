package makarov.vk.vkgroupchats.mvp;

public interface View<PresenterType extends Presenter> {

    void onStart();

    void onStop();

    boolean isValidView();

    void attachPresenter(PresenterType presenterType);
}
