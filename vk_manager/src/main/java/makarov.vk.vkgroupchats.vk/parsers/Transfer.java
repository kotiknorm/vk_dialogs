package makarov.vk.vkgroupchats.vk.parsers;

import android.support.annotation.Nullable;

public interface Transfer<T, E> {

    @Nullable
    T to(E data);

    @Nullable
    E from(T data);
}
