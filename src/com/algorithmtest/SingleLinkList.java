package com.algorithmtest;


/**
 * @decription: 单链表
 * @author: 180449
 * @date 2021/3/25 15:04
 */
public class SingleLinkList {


    static class Node {
        private Integer value;
        private Node next;

        public Node(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    static class DoubleNode{
        private Integer value;
        private DoubleNode last;
        private DoubleNode next;

        public DoubleNode(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public DoubleNode getPre() {
            return last;
        }

        public void setPre(DoubleNode last) {
            this.last = last;
        }

        public DoubleNode getNext() {
            return next;
        }

        public void setNext(DoubleNode next) {
            this.next = next;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    /**
     * 单链表反转
     *
     * @param head 链表的头结点
     * @return 翻转后的头结点
     */
    private static Node reverseLink(Node head) {
        // 表示当前节点的上一个节点
        Node pre = null;
        // 表示当前节点的下一个节点
        Node next = null;
        while (head != null) {
            next = head.next;
            // 这步是反转的核心，把当前节点的上一个节点放到当前节点尾头
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 双向链表反转
     *
     * @param head 链表的头结点
     * @return 翻转后的头结点
     */
    private static DoubleNode reverseDoubleLink(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;

        while (head !=null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        node1.next = node2;
        node2.next = node3;

        Node node = reverseLink(node1);
        System.out.println(node);



        // 双向链表测试
        DoubleNode doubleNode1 = new DoubleNode(1);
        DoubleNode doubleNode2 = new DoubleNode(2);
        DoubleNode doubleNode3 = new DoubleNode(3);
        DoubleNode doubleNode4 = new DoubleNode(4);

        doubleNode1.next = doubleNode2;
        doubleNode2.next = doubleNode3;
        doubleNode3.next = doubleNode4;

        doubleNode4.last = doubleNode3;
        doubleNode3.last = doubleNode2;
        doubleNode2.last = doubleNode1;

        DoubleNode doubleNode = reverseDoubleLink(doubleNode1);
        System.out.println(doubleNode);

        String s = "";
        System.out.println("空格的长度：" + s.length());
    }
}
