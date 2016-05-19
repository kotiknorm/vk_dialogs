package makarov.vk.vkgroupchats.presentation.image;

import android.widget.ImageView;

import makarov.vk.vkgroupchats.data.models.Chat;

public interface ImageLoaderStrategy {

    void loadImage(Chat chat, ImageView imageView);

}
