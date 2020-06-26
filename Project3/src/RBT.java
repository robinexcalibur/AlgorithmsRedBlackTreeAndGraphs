import java.util.HashSet;

public class RBT <Key extends Comparable<Key>, Value> {

    private Node root;

    private static boolean RED = true;
    private static boolean BLACK = false;

    //find node by its key
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        return get(root, key);
    }

    //recursive helper method for get
    private Value get(Node root, Key key) {
        if (root == null) {
            return null;
        }

        int compare = key.compareTo(root.key);

        if (compare < 0) {
            return get(root.left, key);
        } else if (compare > 0) {
            return get(root.right, key);
        } else {
            return root.value;
        }
    }

    //put a new node in the tree. If value is null, it's assumed the user wants to delete that key.
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (value == null) {
            //delete(key);
            return;
        }

        root = put(root, key, value);

        root.color = BLACK;
    }

    private Node put(Node root, Key key, Value value) {
        if (root == null) { //reached leaf node, create new node
            return new Node(key, value, RED);
        }

        int compare = key.compareTo(root.key);

        if (compare < 0) {
            root.left = put(root.left, key, value);
        } else if (compare > 0) {
            root.right = put(root.right, key, value);
        } else {
            root.value = value;
        }

        //make sure the tree is still balanced:
        //check 1: if there is a right leaning red link
        //  if yes, rotate left.
        if (!isRed(root.left) && isRed(root.right)) {
            root = rotateLeft(root);
        }

        //check 2: if a node has two red links
        //  if yes, rotate right.
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rotateRight(root);
        }

        //Check 3: if both right and left links are red
        //  if yes, flip the colors to black.
        if (isRed(root.left) && isRed(root.right)) {
            flipColors(root);
        }

        return root;
    }

    //the right child of root will be the new root
    private Node rotateLeft(Node root) {
        Node temp = root.right;
        root.right = temp.left;
        temp.left = root;
        temp.color = root.color;
        root.color = RED;
        return temp;
    }

    //the left child of root will be the new root
    private Node rotateRight(Node root) {
        Node temp = root.left;
        root.left = temp.right;
        temp.right = root;
        temp.color = root.color;
        root.color = RED;
        return temp;
    }

    //Case: both children are red; make root red and make children black.
    private void flipColors(Node root) {
        root.color = RED;
        root.right.color = BLACK;
        root.left.color = BLACK;
    }

    //helper method, check color
    private boolean isRed(Node root) {
        if (root == null) {
            return BLACK;
        } else {
            return root.color;
        }
    }

    //checks if tree is balanced.
    public boolean isBalanced() {
        int blackHeight = 0;

        Node temp = root;

        while (temp != null) {
            if (!isRed(temp)) {
                blackHeight++;
            }
            temp = temp.left;
        }

        return isBalanced(root, blackHeight);
    }

    //recursive helper method for isBalanced.
    private boolean isBalanced(Node root, int blackHeight) {
        if (root == null) {
            return blackHeight == 0;
        }
        if (!isRed(root)) {
            blackHeight--;
        }
        return isBalanced(root.left, blackHeight) && isBalanced(root.right, blackHeight);
    }

    //Tree node
    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private boolean color;

        public Node (Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }
}
