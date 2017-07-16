package com.example.i5.boards;

import android.util.Log;

/**
 * Advanced logging class
 *
 * Use Object... in methods to avoid unnecessary calls to Object.toString()
 *
 * <h1>Filters</h1>
 * Pass bits representing wanted filters when logging a message.
 * In debug screen (to be updated), choose which filters should be displayed.
 *
 * Just to checkout:
 * https://zerocredibility.wordpress.com/2012/03/08/android-advanced-logger/
 */
public class ALog {
    /** Enable v logs. Keep final until adding to debug screen */
    private final static boolean VERBOSE_LOGGING = true;

    // Filters
    public final static int UI = 1 << 0;
    public final static int DATA = 1 << 1;

    /**
     * Filter that logs that don't specify a filter will have.
     */
    private static final int DEFAULT_FILTER = UI | DATA;
    private static final int ALL_FILTERS = UI | DATA;

    /**
     * Filter set in the debug screen.
     * If no filter is set all should be allowed through.
     */
    private static int mFilter = ALL_FILTERS;

    public static void setFilter(int filter) {
        mFilter = filter;
    };

    public static void v(String tag, String s, Object... args) {
        v(tag, DEFAULT_FILTER, s, args);
    }

    public static void v(String tag, int filters, String s, Object... args) {
        if (VERBOSE_LOGGING && allowThrough(filters)) {
            Log.v(tag, String.format(s, args));
        }
    }

    public static void d(String tag, String s, Object... args) {
        d(tag, DEFAULT_FILTER, s, args);
    }

    public static void d(String tag, int filters, String s, Object... args) {
        if (allowThrough(filters)) {
            Log.d(tag, String.format(s, args));
        }
    }

    public static void i(String tag, String s, Object... args) {
        i(tag, DEFAULT_FILTER, s, args);
    }

    public static void i(String tag, int filters, String s, Object... args) {
        if (allowThrough(filters)) {
            Log.i(tag, String.format(s, args));
        }
    }

    public static void w(String tag, String s, Object... args) {
        w(tag, DEFAULT_FILTER, s, args);
    }

    public static void w(String tag, int filters, String s, Object... args) {
        if (allowThrough(filters)) {
            Log.w(tag, String.format(s, args));
        }
    }

    public static void e(String tag, String s, Object... args) {
        e(tag, DEFAULT_FILTER, s, args);
    }

    public static void e(String tag, int filters, String s, Object... args) {
        if (allowThrough(filters)) {
            Log.e(tag, String.format(s, args));
        }
    }

    public static void wtf(String tag, String s, Object... args) {
        wtf(tag, DEFAULT_FILTER, s, args);
    }

    public static void wtf(String tag, int filters, String s, Object... args) {
        if (allowThrough(filters)) {
            Log.wtf(tag, String.format(s, args));
        }
    }

    /**
     * Compare given filters with allowed filters,
     * and decide whether the log should go through
     */
    private static boolean allowThrough(int filters) {
        return (filters & mFilter) != 0;
    }
}