import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class Tree<E> {
    //既然是二叉排序树 那么传过来的节点必须具有排序功能 用户可以自定义比较规则 传进来一个比较器 或则是我们默认实现一个排序其接口

    private int size;
    Comparator<E> comparator;
    Node<E> root;

    public Tree(Comparator<E> comparator){
        this.comparator = comparator;
    }
    public Tree(){}


    public int size(){
        return  this.size;
    }


    //如果用户没有传进来  那么我们就默认用户传进来的这个元素已经具有排序的特性 也就是说已经实现了Compartor接口
    private int compare(E element1,E element2){
        //如果用户自定义了这个比较器 就用用户的 否则就用元素本身默认的
        if (this.comparator != null){
            return comparator.compare(element1, element2);
        }
        return ((Comparable<E>)element1).compareTo(element2);

    }
    //先来遍历


    public void traverse1(){
        order1(root);
    }


    private void order1(Node<E> node){
        if (node == null) return;
        System.out.println(node.element);
        order1(node.left);
        order1(node.right);
    }
    public void traverse2(){
        order2(root);
    }
    private void order2(Node<E> node){
        if (node == null) return;
        order2(node.left);
        System.out.println(node.element);
        order2(node.right);
    }
    public void traverse3(){
        order3(root);
    }
    private void order3(Node<E> node){
        if (node == null) return;
        order3(node.left);
        order3(node.right);
        System.out.println(node.element);
    }
    //再来个层序遍历
    public void levelOrder(){
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() != 0) {
            //将其出队 然后如果左右子树不为空的话再将其左右子树入队
            Node<E> poll = queue.poll();
            System.out.println(poll.element);
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null){
                queue.offer(poll.right);
            }
        }
    }
    //添加  其实添加的时候用户只管添加值即可  真正的是我们在内部给他们创建了这样的一个结构 他们并不知道
    public void add(E element){
        Node<E> newNode = new Node<>();
        newNode.element = element;
        newNode.left = newNode.right = newNode.parent = null;
        //是否是首次添加
        if (root == null){
            root = newNode;
            this.size++;
            return;
        }
        //不是首次添加  那就要看看添加哪个位置合适了
        Node<E> node = root;
        int compare = 0;//比较结果用的
        Node<E> parent = null;// 待添加节点的父节点
        while (node != null){
            parent = node;
            //看看添加到左边还是右边了
             compare = compare(element, node.element);
            if (compare == 0){ //相等
                return;
            }else if (compare > 0){ //往右边插入
                node = node.right;
            }else { //往左边插入
                node = node.left;
            }
        }
        //已经找到了要插入在哪一个父节点下了 然后看看是插入在左边还是在右边
        if (compare(element, parent.element) > 0){
                parent.right = newNode;
                newNode.parent = parent;
        }else {
            parent.left = newNode;
            newNode.parent = parent;
        }
        this.size++;
    }

    //找前驱节点的值  这里的前驱和后继是指在中序遍历时的前驱和后继节点

    public E frontValue( E element){

        return null;
    }

    public boolean contain(E element){
        return getNodeByValue(element) != null ;
    }

    //根据传来的值找到相应的节点
    private Node<E> getNodeByValue(E element) {
        if (root == null) return null;
        //来吧 看看能不能找到
        Node<E> node = root;
//        Node<E> result = null;
        int compare = -1;
        while (node != null){
//            result = node;
            compare = compare(element, node.element);
            if (compare == 0) { //找到了
                break;
            } else if (compare < 0) {//往左继续找
                node = node.left;
            } else {//往右继续找
                node = node.right;
            }
        }
        return node;
    }


    //再来删除





    //添加 删除 找前驱 找后继 中序 前序 后续 中序


    @Override
    public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("size-->"+this.size);
         return sb.toString();
    }

    private static class Node<E> {

        Node<E> parent;
        E element;
        Node<E> left;
        Node<E> right;

        //为方便后续操作 定义一些判断方法

        private boolean hasTwoChildren() {
            if (left != null && right != null) return true;
            return false;
        }

        private boolean isLeft() {
            if (this.parent == null) return false;
            if (this.parent.left == this) return true;
            return false;
        }

        private boolean isRight() {
            if (this.parent == null) return false;
            if (this.parent.right == this) return true;
            return false;
        }

        private boolean isLeaf() {
            if (left == null && right == null) return true;
            return false;
        }


    }



}
