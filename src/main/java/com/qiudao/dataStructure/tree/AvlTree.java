package com.qiudao.dataStructure.tree;

/**
 * AVL Tree, 平衡二叉树的一种。
 * 基于Mark Allen Weiss的数据结构与算法java实现的书本。
 * 参考代码：https://github.com/justinethier/AVL-Tree/blob/master/AvlTree.java
 *         https://github.com/CherylRuo/BST-AVL-and-Splay-Trees/blob/master/AvlTree.java
 * @Author yangjy
 */
public class AvlTree<T extends Comparable<? super T>> {

    /**
     * AVLNode AvlTree的节点，保存AvlTree的element
     * @param
     */
    private class AvlNode{

        /**
         * node的data
         */
        private T element;

        /**
         * node的左儿子
         */
        private AvlNode left;

        /**
         * node的右儿子
         */
        private AvlNode right;

        /**
         * node的高度
         */
        private int height;

        public AvlNode(T element){
            this(element, null, null);
        }

        public AvlNode(T element, AvlNode left, AvlNode right){
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private AvlNode root;

    /**
     * AvlTree 构造函数
     */
    public AvlTree(){
        root = null;
    }

    /**
     * avlNode的高度
     * @param t
     * @return
     */
    public int height(AvlNode t){
        return t == null ? -1:t.height;
    }

    /**
     * 获取最大值，在计算node的高度时用到
     * @param a
     * @param b
     * @return
     */
    public int max(int a, int b){
        if(a>b){
            return a;
        }else {
            return b;
        }
    }

    /**
     * node插入函数
     * @param x
     * @return
     */
    public boolean insert(T x){
        try{
            root = insert(x, root);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 插入node的内部实现函数
     * @param x
     * @param t
     * @return
     * @throws Exception
     */
    private AvlNode insert(T x, AvlNode t) throws Exception{
        if(t==null){
            t = new AvlNode(x);
        } else if(x.compareTo(t.element)<0){
            t.left = insert(x, t.left);
            if(height(t.left) - height(t.right) == 2){
                if(x.compareTo(t.left.element) < 0){
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }
        } else if (x.compareTo(t.element)>0){
            t.right = insert(x, t.right);
            if(height(t.right) - height(t.left) == 2){
                if(x.compareTo(t.right.element) > 0){
                    t = rotateWithRightChild(t);
                }else{
                    t = doubleWithRightChild(t);
                }
            }
        }else{
            throw new Exception("数据已经存在，插入失败");
        }

        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * 单右旋转。
     * @param t
     * @return
     */
    private AvlNode rotateWithLeftChild(AvlNode t){
        AvlNode tLeft = t.left;
        t.left = tLeft.right;
        tLeft.right = t;
        t.height = max(height(t.left), height(t.right)) + 1;
        tLeft.height = max(height(tLeft.left), t.height) + 1;
        return tLeft;
    }

    /**
     * 单左旋转
     * @param t
     * @return
     */
    private AvlNode rotateWithRightChild(AvlNode t){
        AvlNode tRight = t.right;
        t.right = tRight.left;
        tRight.left = t;

        t.height = max(height(t.left), height(t.right)) + 1;
        tRight.height = max(height(tRight.right), t.height) + 1;
        return tRight;
    }

    /**
     * 双右旋转，通过一次单左旋转和一次单右旋转完成
     * @param t
     * @return
     */
    private AvlNode doubleWithLeftChild(AvlNode t){
        t.left = rotateWithRightChild(t.left);
        return rotateWithLeftChild(t);
    }

    /**
     * 双左旋转，通过一次单右旋转和一次单左旋转完成
     * @param t
     * @return
     */
    private AvlNode doubleWithRightChild(AvlNode t){
        t.right = rotateWithLeftChild(t.right);
        return rotateWithRightChild(t);
    }

    /**
     * 删除AvlTree的所有节点
     */
    public void makeEmpty(){
        root = null;
    }

    /**
     * 判断AvlTree是否无节点
     * @return
     */
    public boolean isEmpty(){
        return (root == null);
    }

    /**
     * 查找AvlTree中最小值
     * @return
     */
    public T findMin(){
        if(isEmpty()){
            return null;
        }else{
            return findMin(root).element;
        }
    }

    /**
     * 查找AvlTree中最小的节点
     * @param t
     * @return
     */
    private AvlNode findMin(AvlNode t){
        if(t == null){
            return t;
        }
        while (t.left != null){
            t = t.left;
        }
        return t;
    }

    /**
     * 查找AvlTree中的最大值
     * @return
     */
    public T findMax(){
        if(root == null){
            return null;
        }
        return findMax(root).element;
    }

    /**
     * 查找AvlTree中的最大节点
     * @param t
     * @return
     */
    private AvlNode findMax(AvlNode t){
        if(t == null){
            return null;
        }

        while (t.right != null){
            t = t.right;
        }
        return t;
    }

    /**
     * 判断 x 是否在Tree中
     * @param x
     * @return
     */
    public boolean contains(T x){
        return contains(x, root);
    }

    /**
     * 判断 x 是否在 tree中内部实现函数
     * @param x
     * @param t
     * @return
     */
    private boolean contains(T x, AvlNode t){
        if(t == null){
            return false;
        }else if(x.compareTo(t.element)<0){
            return contains(x, t.left);
        }else if(x.compareTo(t.element)>0){
            return contains(x, t.right);
        }

        return true;
    }


    /**
     * 中序遍历
     * @return
     */
    public String serializeInfix(){
        StringBuilder str = new StringBuilder();
        serializeInfix(root, str, ",");
        return str.toString();
    }

    /**
     * 递归实现中序遍历
     * @param t
     * @param str
     * @param sep
     */
    public void serializeInfix(AvlNode t, StringBuilder str, String sep){
        if(t != null){
            serializeInfix(t.left, str, sep);
            str.append(t.element.toString());
            str.append(sep);
            serializeInfix(t.right, str, sep);
        }
    }

    /**
     * 删除node
     * @param x
     */
    public void remove(T x){
        root = remove(x, root);
    }

    /**
     * 删除node的实现函数
     * @param x
     * @param t
     * @return
     */
    private AvlNode remove(T x, AvlNode t){
        if(t == null){
            return t;
        }
        if(x.compareTo(t.element) < 0){
            t.left = remove(x, t.left);
        }else if(x.compareTo(t.element) > 0){
            t.right = remove(x, t.right);
        }else if(t.left != null && t.right != null){ //two child
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        }else{// one child
            t = (t.left != null) ? t.left : t.right;
        }
        return balance(t);
    }

    /**
     * 删除node后，位置AvlTree平衡
     * @param t
     * @return
     */
    private AvlNode balance(AvlNode t) {
        if (t == null) {
            return t;
        }
        if (height(t.left) - height(t.right) > 1) {
            if (height(t.left.left) >= height(t.left.right))
                t = rotateWithLeftChild(t);
            else
                t = doubleWithLeftChild(t);
        } else if (height(t.right) - height(t.left) > 1) {
            if (height(t.right.right) >= height(t.right.left))
                t = rotateWithRightChild(t);
            else
                t = doubleWithRightChild(t);
        }
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * 前序遍历
     * @return
     */
    public String serializePrefix(){
        StringBuilder str = new StringBuilder();
        serializePrefix(root, str, ",");
        return str.toString();
    }

    /**
     * 递归实现前序遍历
     * @param t
     * @param str
     * @param sep
     */
    public void serializePrefix(AvlNode t, StringBuilder str, String sep){
        if(t != null){
            str.append(t.element.toString());
            str.append(sep);
            serializePrefix(t.left, str, sep);
            serializePrefix(t.right, str, sep);
        }
    }


    public static void main(String[] args){
        AvlTree<Integer> t = new AvlTree<Integer>();
        t.insert(2);
        t.insert(1);
        t.insert(4);
        t.insert(5);
        t.insert(9);
        t.insert(3);
        t.insert(6);
        t.insert(7);
        System.out.println();

        System.out.println("Prefix Traversal:");
        System.out.println(t.serializePrefix());
        System.out.println("Infix Traversal");
        System.out.println(t.serializeInfix());

        System.out.println("contains:" + 9);
        System.out.println(t.contains(9));
        System.out.println("contains:" + 10);
        System.out.println(t.contains(10));

        System.out.println("max:" + t.findMax());
        System.out.println("min:" + t.findMin());

        t.remove(6);
        System.out.println("Prefix Traversal:");
        System.out.println(t.serializePrefix());
        System.out.println("Infix Traversal");
        System.out.println(t.serializeInfix());

    }

}
