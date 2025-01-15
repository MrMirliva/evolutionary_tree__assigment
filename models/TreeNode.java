package models;
import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
    //ATTRIBUTES
    private TreeNode<T> parent;
    private T data;
    private List<TreeNode<T>> children;
    private int depth;

    ///CONSTRUCTORS START
    public TreeNode(T data) {
        this.parent = null;
        this.data = data;
        this.children = new ArrayList<>();
        this.depth = 0;
    }

    public TreeNode(T data, TreeNode<T> parent) {
        this.parent = parent;
        this.data = data;
        this.children = new ArrayList<>();
        this.depth = parent.getDepth() + 1;
    }
    ///CONSTRUCTORS END


    //METHODS START
    //Parent
    public TreeNode<T> getParent() {
        return parent;
    }
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
    }

    //Data 
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    //Children
    public List<TreeNode<T>> getChildren() {
        return children;
    }
    public void addChild(TreeNode<T> child) {
        children.add(child);
    }
    public void removeChild(TreeNode<T> child) {
        children.remove(child);
    }

    //Depth
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
    //METHODS END
}