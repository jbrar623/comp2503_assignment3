import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<T>> implements Iterable<T> {
	class BSTNode implements Comparable<BSTNode> {
		private T data;
		private BSTNode left;
		private BSTNode right;

		public BSTNode(T d) {
			setLeft(null);
			setRight(null);
			setData(d);
		}
		
		public T getData() { 
			return data; 
		}
		
		public void setData(T d) { 
			data = d; 
		}
		
		public void setLeft(BSTNode l) { 
			left = l; 
		}
		
		public void setRight(BSTNode r) { 
			right = r; 
		}
		
		public BSTNode getLeft() { 
			return left; 
		}
		
		public BSTNode getRight() { 
			return right; 
		}
		
		public boolean isLeaf() { 
			return (getLeft() == null) && (getRight() == null); 
		}
		
		public int compareTo(BSTNode o) {
			return this.getData().compareTo(o.getData());
		}
	}

	private BSTNode root;
	private int size;
	private Comparator<T> comparator;

	public BST() {
		root = null;
		size = 0;
		comparator = null;
	}

	public BST(Comparator<T> externalComp) {
		root = null;
		size = 0;
		comparator = externalComp;
	}

	/**
	 * Return the number of nodes in the tree.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Return true if element d is present in the tree.
	 */
	public T find(T d) {
		return find(d, root);
	}

	/**
	 * Add element d to the tree.
	 */
	public void add(T d) {
		BSTNode n = new BSTNode(d);
		if (root == null) {
			root = n;
			size++;
		} else {
			add(root, n);
		}
	}

	/**
	 * Return the height of the tree.
	 */
	public int height() {
		return height(root);
	}

	public void printInOrder() {
		inOrderTraversal(root);
	}
	
	public void printPreOrder() {
		preOrderTraversal(root);
	}
	
	public void printPostOrder() {
		postOrderTraversal(root);
	}
	
	public void printLevelOrder() {
		levelOrderTraversal(root);
	}
	
	public T delete (T d) {
		return delete(root).getData();
	}
	
	public int optimalHeight() {
		return optimalHeight(root);
	}
	
	// Private methods.

	private BSTNode delete(BSTNode r) {
		//TODO: implement delete method 
		
		// Base case
        if (r == null)
        	return null;
        
		// case 1: node to delete is a leaf 
        else if (r.isLeaf()) {
        	size --;
        	return null;
        }
		
		// case 2: node to delete has one child 
        else if (r.getLeft() == null &&  r.getRight() != null) {
        	size --;
        	r.setRight(null);
        }
        else if (r.getLeft() != null &&  r.getRight() == null) {
        	size --;
        	r.setLeft(null);
        }
        
 
		// case 3: node to delete has two children 
	    else {
	        // Find in-order predecessor
	        r.setData(findMin(r.getRight()));
	        // delete the in-order successor
	        r.setRight(delete(r.getRight()));
	        return r;
	    }
		return r;
	
	}
	
	private T findMin(BSTNode r) {
		   T minNode = r.getData();
	        while (r.getLeft() != null) {
	            minNode = (r.getLeft().getData());
	            r = r.getLeft();
	        }
	        return minNode;
	        
	}

	private T find(T d, BSTNode r) {
		if (r == null)
			return null;
		int c = d.compareTo(r.getData());
		if (c == 0)
			return r.getData();
		else if (c < 0)
			return find(d, r.getLeft());
		else
			return find(d, r.getRight());
	}

	/* Do the actual add of node r to tree rooted at n */
	private void add(BSTNode r, BSTNode n) {
		int c = n.compareTo(r);
		if (c < 0) {
			// TODO the node belongs to the left side 
			// first check if the left side is empty or not 
			// if left side is empty, just add it 
			// if left side is not empty, recursively add n to the left subtree 
			if (r.getLeft() == null) {
				r.setLeft(n);
				size++;
			}
			else {
				add(r.getLeft(),n);
			}
		}
		else if (c > 0) {
			if (r.getRight( )== null) {
				r.setRight(n);
				size++;
			}
			else {
				add(r.getRight(),n);
			}
		}
	}

	/* Implement a height method. */
	private int height(BSTNode r) {
		// TODO
		//The height of a tree is equal to the height of the root node.
		//The height of a node is equal to the maximum of the heights of the
		//two subtrees of the node.
		
		
		int h = -1;
		if (r == null) {
			h++;
			//base case 
		}
		else if (r.getLeft() == null && r.getRight() == null) {
			h++;
			//case: if tree root has no children
		}
		else {
		int leftH = height(r.left);
	    int rightH = height(r.right);

	    if (leftH > rightH) {
	        h = leftH + 1;
	    } 
	    else {
	        h = rightH + 1;
	    }
		}
		return h;
	}

	private int optimalHeight(BSTNode r) {
		
		int n = height(r);
		int opHeight = (int) ((Math.log10(n))/(Math.log10(2)));
		return opHeight;
	}
	
	private Queue<T> queue = new LinkedList<T>();
	
	private void visit(BSTNode r) {
		if (r != null)
			queue.add(r.getData());
	}
	
	private void inOrderTraversal(BSTNode r) {
		if (r == null)
			return;
		else {
			inOrderTraversal(r.getLeft());
			visit(r);
			inOrderTraversal(r.getRight());
		}
	}
	
	private void preOrderTraversal(BSTNode r) {
		if (r == null)
			return;
		else {
			visit(r);
			preOrderTraversal(r.getLeft());
			preOrderTraversal(r.getRight());
		}
	}
	
	private void postOrderTraversal(BSTNode r) {
		if (r == null)
			return;
		else {
			postOrderTraversal(r.getLeft());
			postOrderTraversal(r.getRight());
			visit(r);
		}
	}
	
	private void levelOrderTraversal(BSTNode r) {
		//create a queue, put the root on the queue, while the queue is not empty, traverse through
		Queue<BSTNode> q = new LinkedList<BSTNode>();
		if (r != null) {
			q.add(r);
		}
		while (!q.isEmpty()) {
			// get the front element 
			// visit this element 
			// add the children of this element to the queue 
			BSTNode curr = q.remove();
			visit(curr);
			if (curr.getLeft() != null) {
				q.add(curr.getLeft());
			}
			if (curr.getRight() != null) {
			q.add(curr.getRight());
			}
		}
	}
	
	public static final int INORDER = 0;
	public static final int PREORDER = 1;
	public static final int POSTORDER = 2;
	public static final int LEVELORDER = 3;
	//public static final int REVORDER = 4;
	
	public void traverse (int travType) {
		traverse(root, travType);
	}
	
	private void traverse (BSTNode r, int travType) {
		if (r == null) 
			return;
		else {
			switch (travType) {
			case INORDER:
				traverse(r.getLeft(), travType);
				visit(r);
				traverse(r.getRight(), travType);
				break;
				
			case PREORDER:
				visit(r);
				traverse(r.getLeft(), travType);
				traverse(r.getRight(), travType);
				break;
				
			case POSTORDER:
				traverse(r.getLeft(), travType);
				traverse(r.getRight(), travType);
				visit(r);
				break;
				
			case LEVELORDER:
				//create a queue, put the root on the queue, while the queue is not empty, traverse through
				Queue<BSTNode> q = new LinkedList<BSTNode>();
				if (r != null) {
					q.add(r);
				}
				while (!q.isEmpty()) {
					// get the front element 
					// visit this element 
					// add the children of this element to the queue 
					BSTNode curr = q.remove();
					visit(curr);
					if (curr.getLeft() != null) {
						q.add(curr.getLeft());
					}
					if (curr.getRight() != null) {
					q.add(curr.getRight());
					}
				}
				break;
				
			}
		}
	}
	
	private class BSTIterator implements Iterator<T> {

		public void BSTIteratorInOrder() {
			queue.clear();
			// traverse the tree in-order
			traverse(root, INORDER);
		
		}
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public T next() {
			return queue.remove();
		}
		
	}


	@Override
	/** Return an in-order tterator over a tree.
	* @returns an iterator
	*/
	public Iterator<T> iterator() {
		return new BSTIterator();
	}
}
