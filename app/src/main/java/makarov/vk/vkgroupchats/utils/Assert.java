package makarov.vk.vkgroupchats.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import makarov.vk.vkgroupchats.BuildConfig;

/**
 * Wrapper class for assertion. In debug it works as ordinary Assert;
 */
public class Assert {

    private static final String TAG = Assert.class.getCanonicalName();

    public static void fail() {
        fail((String) null);
    }

    public static void fail(@Nullable String message) {
        Log.e(TAG, message);
        if (BuildConfig.DEBUG) {
            AssertionError error = new AssertionError(message);
            throw error;
        }
    }

    public static void fail(@Nullable Throwable cause) {
        Log.e(TAG, "assert", cause);
        if (BuildConfig.DEBUG) {
            AssertionError error = new AssertionError(cause);
            throw error;
        }
    }

    public static void assertTrue(@Nullable String message, boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    public static void assertFalse(@Nullable String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    public static void assertEquals(@Nullable String message,
                                    @Nullable Object expected, @Nullable Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        } else {
            failNotEquals(message, expected, actual);
        }
    }

    public static void assertEquals(@Nullable Object expected, @Nullable Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(@Nullable String message, long expected, long actual) {
        if (expected != actual) {
            failNotEquals(message, expected, actual);
        }
    }

    public static void assertNotNull(@Nullable String message, @Nullable Object object) {
        assertTrue(message, object != null);
    }

    public static void assertNotNull(@Nullable Object object) {
        assertNotNull(null, object);
    }

    public static void assertNull(@Nullable String message, @Nullable Object object) {
        assertTrue(message, object == null);
    }

    public static void assertNull(@Nullable Object object) {
        assertNull(null, object);
    }

    public static void assertSame(@Nullable String message,
                                     @Nullable Object unexpected, @Nullable Object actual) {
        if (unexpected != actual) {
            failSame(message);
        }
    }

    public static void assertSame(@Nullable Object unexpected, @Nullable Object actual) {
        assertSame(null, unexpected, actual);
    }

    private static void failSame(@Nullable String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected not same");
    }

    private static void failNotEquals(@Nullable String message,
                                      @Nullable Object expected, @Nullable Object actual) {
        fail(format(message, expected, actual));
    }

    private static String format(@Nullable String message,
                                 @Nullable Object expected, @Nullable Object actual) {
        String formatted = "";
        if (message != null && !message.equals("")) {
            formatted = message + " ";
        }
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        if (expectedString.equals(actualString)) {
            return formatted + "expected: " + formatClassAndValue(expected, expectedString)
                    + " but was: " + formatClassAndValue(actual, actualString);
        } else {
            return formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
        }
    }

    private static String formatClassAndValue(@Nullable Object value,
                                              @Nullable String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }
}

