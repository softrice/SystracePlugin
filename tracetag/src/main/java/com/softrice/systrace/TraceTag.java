package com.softrice.systrace;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Trace;

/**
 * 用链表实现栈的功能
 * 对栈匹配异常的情况做了容错处理
 *
 * @author huangzhangshuai
 * @time 2020/5/19 6:21 PM
 */
public class TraceTag {

    private static final String TAG = "TraceTag";
    private static final ThreadLocal<Stack> mStackThreadLocal = new ThreadLocal<>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void i(String name) {
        Trace.beginSection(name);
        if (mStackThreadLocal.get() == null) {
            mStackThreadLocal.set(new Stack());
        }
        mStackThreadLocal.get().addNode(name);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void o(String name) {
        Stack stack = mStackThreadLocal.get();
        if (name.equals(stack.getTopName())) {
            Trace.endSection();
            stack.popNode();
        } else {
            tryMakeCorrect(name, stack);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static void tryMakeCorrect(String name, Stack stack) {
        int tempS = stack.getmStackSize();
        Node tmpNode = stack.getmTopNode();
        while (!name.equals(tmpNode.name)) {
            if (tempS <= 1) {
                return;
            }
            tmpNode = tmpNode.next;
            tempS--;
        }
        int offset = stack.getmStackSize() - tempS + 1;
        while (offset > 0) {
            stack.popNode();
            Trace.endSection();
            offset--;
        }
    }
}
