package com.softrice.systrace;

/**
 * Created by caichongyang on 2017/6/20.
 */
public class TraceBuildConstants {

    public final static int MAX_SECTION_NAME_LEN = 127;

    public final static String MATRIX_TRACE_METHOD_BEAT_CLASS = "com/softrice/systrace/TraceTag";
    public static final String[] UN_TRACE_CLASS = {"R.class", "R$", "Manifest", "BuildConfig"};
    public final static String DEFAULT_BLACK_TRACE =
                    "[package]\n"
                    + "-keeppackage com/softrice/systrace\n";
}
