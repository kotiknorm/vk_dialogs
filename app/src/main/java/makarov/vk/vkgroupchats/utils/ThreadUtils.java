package makarov.vk.vkgroupchats.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadUtils {

    private static final int COUNT_THREADS = 8;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            final Thread lowPriorityThread = new Thread(r);
            lowPriorityThread.setPriority(Thread.NORM_PRIORITY);
            return lowPriorityThread;
        }
    };

    public static final Executor APP_EXECUTOR =
            Executors.newFixedThreadPool(COUNT_THREADS, sThreadFactory);
}
