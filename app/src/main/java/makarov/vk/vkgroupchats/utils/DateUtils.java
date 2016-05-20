package makarov.vk.vkgroupchats.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static String chatLastMessage(Long second) {
        Date date = new Date(TimeUnit.SECONDS.toMillis(second));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
