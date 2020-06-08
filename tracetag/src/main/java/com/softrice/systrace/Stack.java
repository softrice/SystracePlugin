package com.softrice.systrace;

/**
 * Created by huangzhangshuai on 2020/5/24.
 */
public class Stack {

    private int mStackSize = 0;
    private Node mTopNode;

    public void addNode(String name) {
        if (mTopNode == null) {
            mTopNode = Node.obtain(name);
        } else {
            Node node = Node.obtain(name);
            node.next = mTopNode;
            mTopNode = node;
        }
        mStackSize++;
    }

    public void popNode() {
        if (mTopNode == null || mStackSize == 0) {
            throw new RuntimeException("stack is empty, cannot remove node.");
        } else {
            Node tmp = mTopNode;
            mTopNode = mTopNode.next;
            tmp.recycle();
            mStackSize--;
        }
    }

    public String getTopName() {
        return mTopNode.name;
    }

    public int getmStackSize() {
        return mStackSize;
    }

    public Node getmTopNode() {
        return mTopNode;
    }
}
