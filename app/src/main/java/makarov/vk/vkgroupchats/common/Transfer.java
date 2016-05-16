package makarov.vk.vkgroupchats.common;

public interface Transfer<T, E> {

    T to(E data);

    E from(T data);
}
