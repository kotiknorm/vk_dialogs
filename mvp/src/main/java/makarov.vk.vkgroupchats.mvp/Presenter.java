package makarov.vk.vkgroupchats.mvp;

public interface Presenter<ViewType extends View> {

    ViewType getView();

    boolean isAttachedToView();

    void onAttachView(ViewType view);

    void onDetachView();

    void onViewCreated();

    void onStart();

    void onStop();

    void onResume();

    void onPause();

}
