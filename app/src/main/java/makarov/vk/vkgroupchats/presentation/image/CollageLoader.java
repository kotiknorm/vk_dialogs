package makarov.vk.vkgroupchats.presentation.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.utils.BitmapUtils;

public class CollageLoader {

    private final ImageView mImageView;
    private final List<String> mUrls;
    private final Context mContext;

    private final List<Bitmap> mBitmaps;
    private int mLoadedBitmaps = 0;
    private boolean mCanceled = false;

    private final Target mLoadtarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mLoadedBitmaps--;
            mBitmaps.add(bitmap);
            handleLoadedBitmap();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mLoadedBitmaps--;
            handleLoadedBitmap();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    };

    public CollageLoader(List<String> urls, ImageView imageView, Context context) {
        mUrls = urls;
        mImageView = imageView;
        mContext = context;

        mBitmaps = new ArrayList<>(mUrls.size());
        mLoadedBitmaps = mUrls.size();

    }

    public void execute() {
        Picasso.with(mContext).load(mUrls.get(mLoadedBitmaps - 1)).into(mLoadtarget);
    }

    private void handleLoadedBitmap() {
        if (mCanceled) {
            return;
        }

        if (mLoadedBitmaps == 0) {
            setCollage(mBitmaps);
        } else {
            Picasso.with(mContext).load(mUrls.get(mLoadedBitmaps - 1)).into(mLoadtarget);
        }
    }

    public void cancel() {
        mBitmaps.clear();
        mLoadedBitmaps = 0;
        mCanceled = true;
    }

    private void setCollage(List<Bitmap> bitmaps) {
        int size = ImageLoaderStrategyImpl.MAX_COUNT_IMAGES - bitmaps.size();

        BitmapDrawable bitmap = (BitmapDrawable) mContext.getResources().getDrawable(R.mipmap.icon);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmap.getBitmap(), bitmaps.get(0).getHeight(), bitmaps.get(0).getWidth(), false);
        for (int i = 0; i < size; i++) {
            mBitmaps.add(resizedBitmap);
        }

        Bitmap one = BitmapUtils.joinBitmapsHorizontally(mBitmaps.get(0), mBitmaps.get(1));
        Bitmap two = BitmapUtils.joinBitmapsHorizontally(mBitmaps.get(2), mBitmaps.get(3));
        Bitmap result = BitmapUtils.joinBitmapsVertically(one, two);
        mImageView.setImageBitmap(result);

        recycle();
    }

    private void recycle() {
        mBitmaps.clear();
    }

}
