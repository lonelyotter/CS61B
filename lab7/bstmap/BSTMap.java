package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;


    public BSTMap() {
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(key, root);

    }

    private boolean containsKey(K key, Node node) {
        if (node == null) {
            return false;
        } else if (key.compareTo(node.key) == 0) {

            return true;
        } else if (key.compareTo(node.key) < 0) {
            return containsKey(key, node.left);
        } else {
            return containsKey(key, node.right);
        }
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            Node node = findKey(key, root);
            return node.value;
        }
    }

    private Node findKey(K key, Node node) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return findKey(key, node.left);
        } else {
            return findKey(key, node.right);
        }
    }

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            root = put(key, value, root);
            size++;
        }
    }

    /**
     * add new node to the tree
     *
     * @param key   add key
     * @param value add value
     * @param node  the tree to be modified
     * @return the modified tree
     */
    private Node put(K key, V value, Node node) {
        if (node == null) {
            return new Node(key, value);
        } else if (key.compareTo(node.key) < 0) {
            node.left = put(key, value, node.left);
        } else {
            node.right = put(key, value, node.right);
        }
        return node;
    }

    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print("{" + node.key + ", " + node.value + "} ");
            printInOrder(node.right);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        addKey(set, root);
        return set;
    }

    private void addKey(Set<K> set, Node node) {
        if (node != null) {
            addKey(set, node.left);
            set.add(node.key);
            addKey(set, node.right);
        }

    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V returnValue = get(key);
            root = remove(key, root);
            size--;
            return returnValue;
        } else {
            return null;
        }
    }

    private Node remove(K key, Node node) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(key, node.right);
        } else if (key.compareTo(node.key) < 0) {
            node.left = remove(key, node.left);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node minNode = getMinChild(node.right);
                node.key = minNode.key;
                node.value = minNode.value;
                node.right = remove(minNode.key, node.right);
            }
        }
        return node;
    }

    private Node getMinChild(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return getMinChild(node.left);
        }
    }


    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        } else if (!get(key).equals(value)) {
            return null;
        } else {
            return remove(key);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
