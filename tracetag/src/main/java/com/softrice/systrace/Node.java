package com.softrice.systrace;

/**
 * 提供回收池
 *
 * @author huangzhangshuai
 * @time 2020/5/19 6:21 PM
 */
public class Node {

    public String name;
    public Node next;

    private static final Object sPoolSync = new Object();
    private static final int MAX_POOL_SIZE = 50;
    private static Node sPool;
    private static int sPoolSize = 0;

    public void recycle() {
        name = null;
        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    public static Node obtain(String name) {
        Node node = obtain();
        node.name = name;
        return node;
    }

    public static Node obtain() {
        synchronized (sPoolSync) {
            if (sPool == null) {
                return new Node();
            } else {
                Node tmp = sPool;
                sPool = sPool.next;
                tmp.next = null;
                sPoolSize--;
                return tmp;
            }
        }
    }

}
