import java.util.*;

class TreeNode {
    int val;
    List<TreeNode> neighbors;

    public TreeNode(int val) {
        this.val = val;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(TreeNode neighbor) {
        neighbors.add(neighbor);
    }
}

class DFSTreeIterator implements Iterator<Integer> {
    Stack<TreeNode> stack;

    public DFSTreeIterator(TreeNode root) {
        stack = new Stack<>();
        stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the tree.");
        }

        TreeNode current = stack.pop();
        for (int i = current.neighbors.size() - 1; i >= 0; i--) {
            stack.push(current.neighbors.get(i));
        }

        return current.val;
    }
}

public class iterator {
    TreeNode root;

    public iterator(int val) {
        this.root = new TreeNode(val);
    }

    public void addEdge(TreeNode from, TreeNode to) {
        from.addNeighbor(to);
    }

    public Iterator<Integer> dfsIterator() {
        return new DFSTreeIterator(root);
    }

    public static void main(String[] args) {
       iterator tree = new iterator(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);

        tree.addEdge(tree.root, node2);
        tree.addEdge(tree.root, node3);
        tree.addEdge(node2, node4);

        Iterator<Integer> iterator = tree.dfsIterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }
}

