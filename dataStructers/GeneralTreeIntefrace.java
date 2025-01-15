package dataStructers;
import java.util.List;

import models.TreeNode;

/**
 * Interface representing a general tree structure.
 * @param <T> The type of data stored in the tree nodes.
 */
public interface GeneralTreeIntefrace<T> {

    /**
     * Get the root node of the tree.
     * 
     * @return The root node.
     */
    TreeNode<T> getRoot();

    /**
     * Add a child node to a specified parent node.
     * 
     * @param parent The parent node.
     * @param child The child node to be added.
     */
    void addChild(TreeNode<T> parent, TreeNode<T> child);

    /**
     * Remove a child node from a specified parent node.
     * 
     * @param parent The parent node.
     * @param child The child node to be removed.
     */
    void removeChild(TreeNode<T> parent, TreeNode<T> child);

    /**
     * Find a node containing the specified value.
     * 
     * @param value The value to search for.
     * @return The node containing the value, or null if not found.
     */
    TreeNode<T> findNode(T value);

    /**
     * Get all nodes in the tree.
     * 
     * @return A list of all nodes in the tree.
     */
    List<TreeNode<T>> getAllNodes();

    /**
     * Get the depth of the tree.
     * 
     * @return The depth of the tree.
     */
    int getDepth();

    /**
     * Check if the tree is empty.
     * 
     * @return True if the tree is empty, false otherwise.
     */
    boolean isEmpty();
}
