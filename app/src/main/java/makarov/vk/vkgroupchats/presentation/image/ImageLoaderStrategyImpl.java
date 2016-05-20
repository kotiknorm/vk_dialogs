package makarov.vk.vkgroupchats.presentation.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.User;

public class ImageLoaderStrategyImpl implements ImageLoaderStrategy {

    public static final int MAX_COUNT_IMAGES = 4;

    private final Context mContext;

    public ImageLoaderStrategyImpl(Context context) {
        mContext = context;
    }

    @Override
    public void loadImage(Chat chat, ImageView imageView) {
        cancelLoaderIfNeeded(imageView);

        if (!TextUtils.isEmpty(chat.getPhoto())) {
            Picasso.with(mContext).load(chat.getPhoto()).into(imageView);
            return;
        }

        List<String> urls = new ArrayList<>();
        for (User user : chat.getUsers()) {
            if (!TextUtils.isEmpty(user.getPhoto())) {
                urls.add(user.getPhoto());
            }

            if (urls.size() >= MAX_COUNT_IMAGES) {
                break;
            }
        }

        CollageLoader loader = new CollageLoader(urls, imageView, mContext);
        loader.execute();
        imageView.setTag(R.id.collage_tag, loader);
    }

    private void cancelLoaderIfNeeded(ImageView imageView) {
        imageView.setImageBitmap(null);
        CollageLoader loader = (CollageLoader) imageView.getTag(R.id.collage_tag);
        if (loader != null) {
            loader.cancel();
            imageView.setTag(R.id.collage_tag, null);
        }
    }

}
