package makarov.vk.vkgroupchats.presentation.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.utils.BitmapUtils;
import makarov.vk.vkgroupchats.utils.ThreadUtils;

public class CollageLoader {

    private final ImageView mImageView;
    private final List<String> mUrls;
    private final Context mContext;

    private final List<Bitmap> mBitmaps;
    private int mLoadedBitmaps = 0;
    private boolean mCanceled = false;

    @Nullable private CollageAsyncTask mCollageAsyncTask;

    private final Target mLoadTarget = new Target() {
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
        if (mLoadedBitmaps > 0) {
            Picasso.with(mContext).load(mUrls.get(mLoadedBitmaps - 1)).into(mLoadTarget);
        }
    }

    private void handleLoadedBitmap() {
        if (mCanceled) {
            return;
        }

        if (mLoadedBitmaps == 0) {
            mCollageAsyncTask = new CollageAsyncTask(mBitmaps, mImageView);
            mCollageAsyncTask.executeOnExecutor(ThreadUtils.APP_EXECUTOR);
        } else {
            Picasso.with(mContext).load(mUrls.get(mLoadedBitmaps - 1)).into(mLoadTarget);
        }
    }

    public void cancel() {
        mBitmaps.clear();
        mLoadedBitmaps = 0;
        mCanceled = true;

        if (mCollageAsyncTask != null)
            mCollageAsyncTask.cancel(true);
    }

    private static class CollageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        private List<Bitmap> mBitmaps;
        private ImageView mImageView;

        CollageAsyncTask(List<Bitmap> bitmaps, ImageView imageView) {
            mBitmaps = bitmaps;
            mImageView = imageView;
        }


        @Override
        protected Bitmap doInBackground(Void[] objects) {
            return createCollage();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            mBitmaps.clear();
            if (result != null) {
                mImageView.setImageBitmap(result);
            }
        }

        @WorkerThread
        private Bitmap createCollage() {
            Bitmap result = null;

            switch (mBitmaps.size()) {
                case 0:
                    break;
                case 1:
                    result = mBitmaps.get(0);
                    break;
                case 2:
                    result = BitmapUtils.joinBitmapsHorizontally(mBitmaps.get(0), mBitmaps.get(1));
                    break;
                case 3:
                    Bitmap horizontally = BitmapUtils.joinBitmapsVertically(mBitmaps.get(0), mBitmaps.get(1));

                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(mBitmaps.get(2),
                            horizontally.getHeight(), horizontally.getHeight(), false);
                    resizedBitmap = BitmapUtils.getVkAvatarBitmap(resizedBitmap);

                    result = BitmapUtils.joinBitmapsHorizontally(resizedBitmap, horizontally);
                    break;
                case 4:
                    Bitmap horizontallyFirst = BitmapUtils.joinBitmapsHorizontally(mBitmaps.get(0), mBitmaps.get(1));
                    Bitmap horizontallySecond = BitmapUtils.joinBitmapsHorizontally(mBitmaps.get(2), mBitmaps.get(3));
                    result = BitmapUtils.joinBitmapsVertically(horizontallyFirst, horizontallySecond);
                    break;
            }

            return result;
        }
    }

}
