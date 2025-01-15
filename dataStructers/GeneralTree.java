package dataStructers;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import models.TreeNode;

public class GeneralTree<T> implements GeneralTreeIntefrace<T> {

    //Instance variables
    private TreeNode<T> root;

    //Constructors
    public GeneralTree(T rootData) {
        this.root = new TreeNode<>(rootData);
    }

    /** 
     * Get the root node of the tree.
     * @return The root node.
     */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * Add a child node to a specified parent node.
     * 
     * @param parent The parent node.
     * @param child The child node to be added.
     */
    public void addChild(TreeNode<T> parent, TreeNode<T> child) {
        if (parent != null) {
            parent.addChild(child);
            child.setParent(parent);
        }
    }

    /**
     * Remove a child node from a specified parent node.
     * @param parent The parent node.
     * @param child The child node to be removed.
     */
    public void removeChild(TreeNode<T> parent, TreeNode<T> child) {
        if (parent != null) {
            parent.removeChild(child);
        }
    }

    /**
     * Find a node containing the specified value.
     * 
     * @param value The value to search for.
     * @return The node containing the value, or null if not found.
     */
    public TreeNode<T> findNode(T value) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            if (current.getData().equals(value)) {
                return current;
            }
            queue.addAll(current.getChildren());
        }

        return null;
    }

    /**
     * Get all nodes in the tree.
     * 
     * @return A list of all nodes in the tree.
     */
    public List<TreeNode<T>> getAllNodes() {
        List<TreeNode<T>> nodeList = new ArrayList<>();
        if (root == null) {
            return nodeList;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            nodeList.add(current);
            queue.addAll(current.getChildren());
        }

        return nodeList;
    }

    /**
     * Get the depth of the tree.
     * 
     * @return The depth of the tree.
     */
    public int getDepth() {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode<T> current = queue.poll();
                queue.addAll(current.getChildren());
            }
            depth++;
        }

        return depth;
    }

    /**
     * Check if the tree is empty.
     * 
     * @return True if the tree is empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }
}