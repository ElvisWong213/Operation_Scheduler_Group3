package backend.dataStructure;
import java.util.NoSuchElementException;

import backend.dataStructure.exception.DuplicateElementException;

/**
 * Represents a binary search tree data structure.
 * @param <T> the type of elements stored in the binary search tree, must implement the Comparable interface
 */
public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;
    
    /**
     * Represents a node in the binary search tree.
     * @param <T> the type of data stored in the node, must implement the Comparable interface
     */
    private static class Node<T extends Comparable<T>> implements Comparable<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        
        /**
         * Constructs a new node with the specified data.
         * @param data the data to be stored in the node
         */
        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(T other) {
            return this.data.compareTo(other);
        }   
    }

    /**
     * Constructs an empty binary search tree.
     */
    public BinaryTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Adds an element to the binary search tree.
     * @param data the element to be added
     * @throws DuplicateElementException if the element is already present in the tree
     */
    public void add(T data) throws DuplicateElementException {
        root = addPerform(root, data);
        size++;
    }

    private Node<T> addPerform(Node<T> current, T data) throws DuplicateElementException {
        if (current == null) {
            return new Node(data);
        }
        if (data.compareTo(current.data) > 0) {
            current.right = addPerform(current.right, data);
        } else if (data.compareTo(current.data) < 0) {
            current.left = addPerform(current.left, data);
        } else {
            throw new DuplicateElementException("This item is already added to the shop, please check the id");
        }
        
        int balanceFactor = getHeight(current.left) - getHeight(current.right);
        if (balanceFactor < -1) {
            if (data.compareTo(current.right.data) > 0) {
                current = leftRotation(current);
            } else if (data.compareTo(current.right.data) < 0) {
                current.right = rightRotation(current.right);
                current = leftRotation(current);
            }
        }
        if (balanceFactor > 1) {
            if (data.compareTo(current.left.data) < 0) {
                current = rightRotation(current);
            } else if (data.compareTo(current.left.data) > 0) {
                current.left = leftRotation(current.left);
                current = rightRotation(current);
            }
        }
        return current;
    }

    /**
     * Updates an element in the binary search tree.
     * @param data the element to be updated
     */
    public void update(T data) {
        root = updatePerform(root, data);    
    }

    private Node<T> updatePerform(Node<T> current, T data) {
        if (data.compareTo(current.data) == 0) {
            Node<T> newNode = new Node(data);
            newNode.left = current.left;
            newNode.right = current.right;
            return newNode;
        }
        if (data.compareTo(current.data) > 0) {
            current.right = updatePerform(current.right, data);
        } else if (data.compareTo(current.data) < 0) {
            current.left = updatePerform(current.left, data);
        }
        return current;
    }

    /**
     * Removes an element from the binary search tree.
     * @param data the element to be removed
     */
    public void remove(T data) {
        root = removePerform(root, data);
        size--;
    }

    private Node<T> removePerform(Node<T> current, T data) {
        if (current.data.compareTo(data) == 0) {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right != null) {
                Node<T> successorNode = successorRightSmallest(current.right);
                successorNode.left = current.left;
                return successorNode;
            }
            if (current.left != null) {
                Node<T> successorNode = successorLeftLargerset(current.left) ;
                successorNode.right = current.right;
                return successorNode;
            }
        }
        if (data.compareTo(current.data) > 0) {
            current.right = removePerform(current.right, data);
        } else if (data.compareTo(current.data) < 0) {
            current.left = removePerform(current.left, data);
        }
        
        int balanceFactor = getHeight(current.left) - getHeight(current.right);
        if (balanceFactor < -1) {
            if (data.compareTo(current.right.data) > 0) {
                current = leftRotation(current);
            } else if (data.compareTo(current.right.data) < 0) {
                current.right = rightRotation(current.right);
                current = leftRotation(current);
            }
        }
        if (balanceFactor > 1) {
            if (data.compareTo(current.left.data) < 0) {
                current = rightRotation(current);
            } else if (data.compareTo(current.left.data) > 0) {
                current.left = leftRotation(current.left);
                current = rightRotation(current);
            }
        }
        return current;
    }

    private Node<T> successorRightSmallest(Node<T> current) {
        if (current.left == null) {
            return current;
        }
        return successorRightSmallest(current.left);
    }

    private Node successorLeftLargerset(Node<T> current) {
        if (current.right == null) {
            return current;
        }
        return successorLeftLargerset(current.right);
    }

    private int getHeight(Node<T> current) {
        if (current == null) {
            return 0;
        }
        int leftHeight = getHeight(current.left);
        int rightHeight = getHeight(current.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private Node<T> leftRotation(Node<T> current) {
        Node<T> rightSubTree = current.right;
        if (rightSubTree.left != null) {
            current.right = rightSubTree.left;
        } else {
            current.right = null;
        }
        rightSubTree.left = current;
        return rightSubTree;
    }

    private Node<T> rightRotation(Node<T> current) {
        Node<T> leftSubTree = current.left;
        if (leftSubTree.right != null) {
            current.left = leftSubTree.right;
        } else {
            current.left = null;
        }
        leftSubTree.right = current;
        return leftSubTree;
    }
    
    /**
     * Searches for an element in the binary search tree.
     * @param data the element to search for
     * @return the found element
     * @throws NoSuchElementException if the element is not found
     */
    public T search(T data) throws NoSuchElementException {
        return searchPerform(root, data).data;
    }

    private Node<T> searchPerform(Node<T> current, T data) throws NoSuchElementException {
        if (current == null) {
            throw new NoSuchElementException();
        }
        if (data.compareTo(current.data) > 0) {
            return searchPerform(current.right, data);
        } else if (data.compareTo(current.data) < 0) {
            return searchPerform(current.left, data);
        }
        return current;
    }

    /**
     * Performs a pre-order traversal of the binary search tree.
     * @return a linked list containing the elements in pre-order
     */
    public MyLinkedList<T> preOrder() {
        MyLinkedList<T> array = new MyLinkedList<>();
        preOrderPerform(root, array);
        return array;
    }

    private void preOrderPerform(Node<T> current, MyLinkedList<T> array) {
        if (current == null) {
            return;
        }
        array.add(current.data);
        preOrderPerform(current.left, array);
        preOrderPerform(current.right, array);
    }

    /**
     * Performs a post-order traversal of the binary search tree.
     * @return a linked list containing the elements in post-order
     */
    public MyLinkedList<T> postOrder() {
        MyLinkedList<T> array = new MyLinkedList<>();
        postOrderPerform(root, array);
        return array;
    }

    private void postOrderPerform(Node<T> current, MyLinkedList<T> array) {
        if (current == null) {
            return;
        }
        postOrderPerform(current.left, array);
        postOrderPerform(current.right, array);
        array.add(current.data);
    }

    /**
     * Performs an in-order traversal of the binary search tree.
     * @return a linked list containing the elements in in-order
     */
    public MyLinkedList<T> inOrder() {
        MyLinkedList<T> array = new MyLinkedList<>();
        inOrderPerform(root, array);
        return array;
    }

    private void inOrderPerform(Node<T> current, MyLinkedList<T> array) {
        if (current == null) {
            return;
        }
        inOrderPerform(current.left, array);
        array.add(current.data);
        inOrderPerform(current.right, array);
    }

    /**
     * Returns the number of elements in the binary search tree.
     * @return the number of elements
     */
    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }
}
