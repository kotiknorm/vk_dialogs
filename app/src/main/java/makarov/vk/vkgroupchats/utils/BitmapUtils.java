package makarov.vk.vkgroupchats.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BitmapUtils {

    public static final int LINE = 5;

    public static Bitmap joinBitmapsHorizontally(Bitmap c, Bitmap s) {
        Bitmap cs;
        int width, height;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }

        cs = Bitmap.createBitmap(width + LINE, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth() + LINE, 0f, null);
        return cs;
    }


    public static Bitmap joinBitmapsVertically(Bitmap c, Bitmap s) {
        Bitmap cs;
        int width, height;

        if(c.getHeight() > s.getHeight()) {
            height = c.getHeight() + s.getHeight();
            width = c.getWidth();
        } else {
            height = s.getHeight() + s.getHeight();
            width = c.getWidth();
        }

        cs = Bitmap.createBitmap(width, height + LINE, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, c.getHeight() + LINE, null);
        return cs;
    }
}
