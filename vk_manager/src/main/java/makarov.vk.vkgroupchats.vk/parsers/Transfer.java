package makarov.vk.vkgroupchats.vk.parsers;

public interface Transfer<T, E> {

    T to(E data);

    E from(T data);
}
