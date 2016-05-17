package makarov.vk.vkgroupchats.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String chatLastMessage(Long second) {
        Date date = new Date(second * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
