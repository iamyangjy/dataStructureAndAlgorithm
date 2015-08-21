package com.qiudao.dataStructure.tree;

/**
 * Splay Tree,伸展树，平衡二叉数一种。
 * 基于Mark Allen Weiss的数据结构与算法java实现的书本。
 * 参考代码：https://github.com/lsissoko/SplayTree/blob/master/SplayTreeImpl.java
 *         http://algs4.cs.princeton.edu/33balanced/SplayBST.java.html
 * @Author: yangjy
 */
public class SplayTree<T extends Comparable<? super T>> {

    //Tree Node
    private class SplayNode{
        /**
         * 节点的键
         */
        private T element;
        /**
         * 左儿子节点
         */
        private SplayNode left;
        /**
         * 右儿子节点
         */
        private SplayNode right;

        /**
         * 构造函数 1
         * @param element
         */
        private SplayNode(T element){
            this(element, null, null);
        }

        /**
         * 构造函数2
         * @param element
         * @param left
         * @param right
         */
        private SplayNode(T element, SplayNode left, SplayNode right){
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private SplayNode root;

    public SplayTree(){
        root = null;
    }

    /**
     * splay Tree是否为空
     * @return
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * 数的节点个数
     * @return
     */
    public int size(){
        return isEmpty() ? 0 : size(root);
    }

    /**
     * 返回node下的节点数
     * @param t
     * @return
     */
    public int size(SplayNode t){
        int count = 0;
        if(t != null){
            count ++;
            count += size(t.left);
            count += size(t.right);
        }
        return count;
    }

    /**
     * 插入函数,并进行splay操作
     * @param x
     */
    public void insert(T x){
        if(x != null){
            root = insert(x, root);
            root = splay(root, x);
        }
    }

    /**
     * 插入的实际实现函数，使用递归方法，插入后没有进行splay操作。
     * @param x
     * @param t
     * @return
     */
    private SplayNode insert(T x, SplayNode t){
        if(t == null)
            return new SplayNode(x);
        else {
            if(x.compareTo(t.element) < 0)
                t.left = insert(x, t.left);
            else if(x.compareTo(t.element) > 0)
                t.right = insert(x, t.right);
            return t;
        }
    }

    /**
     * 删除节点,详见要删除的节点伸展到root，然后删除root。
     * @param x
     * @return
     */
    public boolean remove(T x){
        if(!isEmpty() && x != null){
            root = splay(root, x);
            if(root != null && x.compareTo(root.element) == 0){
                if(root.left != null){
                    SplayNode temp = root.right;
                    root = root.left;
                    //将左子树的最大的节点提到root节点上
                    root = splay(root, x);
                    root.right = temp;
                }else {
                    root = root.right;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 最大值，返回之前，对最大值做伸展操作
     * @param t
     * @return
     */
    public T findMax(SplayNode t){
        if(t == null){
            return null;
        }

        while (t.right != null){
            t = t.right;
        }
        root = splay(root, t.element);
        return root.element;
    }

    /**
     *  最小值，返回之前，对最小值做伸展操作
     * @param t
     * @return
     */
    public T findMin(SplayNode t){
        if(t == null){
            return null;
        }
        while (t.left != null){
            t = t.left;
        }
        root = splay(root, t.element);
        return root.element;
    }

    /**
     * 判断是否包含x
     * @param x
     * @return
     */
    public boolean contains(T x){
        if(isEmpty())
            return false;
        root = splay(root, x);
        return x.compareTo(root.element) == 0;
    }


    /**
     * 单右旋转
     * @param t
     * @return
     */
    public SplayNode rotateWithLeftChild(SplayNode t){
        SplayNode tLeft = t.left;
        t.left = tLeft.right;
        tLeft.right = t;
        return tLeft;
    }

    /**
     * 单左旋转
     * @param t
     * @return
     */
    public SplayNode rotateWithRightChild(SplayNode t){
        SplayNode tRight = t.right;
        t.right = tRight.left;
        tRight.left = t;
        return tRight;
    }


    /**
     * splayTree伸展函数,使用while循环操作
     * @param node
     * @param x
     * @return
     */
    private SplayNode splay(SplayNode node, T x){
        SplayNode t = node;
        SplayNode gg = null;
        boolean checkGG = true;
        while (true) {
            if (t == null || x.compareTo(t.element) == 0)
                break;
            else if (t.left != null && x.compareTo(t.element) < 0) {
                // zig case
                if (x.compareTo(t.left.element) == 0)
                    t = rotateWithLeftChild(t);
                    //zig-zig case
                else if (t.left.left != null && x.compareTo(t.left.left.element) == 0) {
                    //gramps = t;
                    //pops = t.left;
                    t = rotateWithLeftChild(t);
                    t = rotateWithLeftChild(t);
                    checkGG = true;
                }
                //zig-zag case
                else if (t.left.right != null && x.compareTo(t.left.right.element) == 0) {
                    t.left = rotateWithRightChild(t.left);
                    t = rotateWithLeftChild(t);
                    checkGG = true;
                }
                //继续下探
                else if (x.compareTo(t.element) < 0) {
                    gg = t;
                    t = t.left;
                }
            } else if (t.right != null && x.compareTo(t.element) > 0) {
                //zig case
                if(x.compareTo(t.right.element) == 0){
                    t = rotateWithRightChild(t);
                }
                // zig-zig case
                else if(t.right.right != null && x.compareTo(t.right.right.element)==0){
                    t = rotateWithRightChild(t);
                    t = rotateWithRightChild(t);
                    checkGG = true;
                }
                //zig-zag case
                else if(t.right.left != null && x.compareTo(t.right.left.element) == 0){
                    t.right = rotateWithLeftChild(t.right);
                    t = rotateWithRightChild(t);
                    checkGG = true;
                }
                //继续下探
                else{
                    gg = t;
                    t = t.right;
                }
            }
            //如果没有找到(x不存在)， 那么将最后的一个值做伸展
            else if((t.left == null && x.compareTo(t.element) <0)
            ||(t.right == null && x.compareTo(t.element) > 0)){
                x = t.element;
                t = node;
                gg = null;
            }
            //zig-zig和zig-zag后，需要将t重新定向到node。
            if(checkGG && gg != null){
                if(t.element.compareTo(gg.element) < 0){
                    gg.left = t;
                }else if(t.element.compareTo(gg.element) > 0){
                    gg.right = t;
                }
                t = node;
                checkGG = false;
            }
        }
        return t;
    }







}
