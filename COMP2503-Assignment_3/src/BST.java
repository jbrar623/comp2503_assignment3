import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The BST class represents a Binary Search Tree that stores elements of type T.
 * It supports various operations such as adding elements, removing elements, traversing through, 
 * and provides an iterator for in-order traversal.
 *
 * @param <T> The generic type of elements stored in the tree, must implement Comparable interface.
 */
public class BST<T extends Comparable<T>> implements Iterable<T> {
	class BSTNode implements Comparable<BSTNode> {
		private T data;
		private BSTNode left;
		private BSTNode right;

		/**
	     * The BSTNode represents a node in the Binary Search Tree.
	     * Each node contains data, a reference to the left child, and a reference to the right child.
	     * @param T - data to create node 
	     */
		public BSTNode(T d) {
			setLeft(null);
			setRight(null);
			setData(d);
		}
		
		/**
	     * @return T - the data of the node.
	     */
		public T getData() { 
			return data; 
		}
		

		/**
	     * @param T - the data of the node to set.
	     */
		public void setData(T d) { 
			data = d; 
		}
		
		/**
	     * @param BSTNode - the data to set the left child of node.
	     */
		public void setLeft(BSTNode l) { 
			left = l; 
		}
		
		/**
	     * @param BSTNode - the data to set the right child of node.
	     */
		public void setRight(BSTNode r) { 
			right = r; 
		}
		
		/**
	     * @return BSTNode - retrieves the left child of node.
	     */
		public BSTNode getLeft() { 
			return left; 
		}
		
		/**
	     * @return BSTNode - retrieves the right child of node.
	     */
		public BSTNode getRight() { 
			return right; 
		}
		
		 /**
         * Checks if the node is a leaf (has no children).
         *
         * @return true if the node is a leaf. 
         */
		public boolean isLeaf() { 
			return (getLeft() == null) && (getRight() == null); 
		}
		
		 /**
         * Compares this node with another given node based on the data they contain.
         *
         * @param o The node to compare with.
         * @return A negative integer, zero, or a positive integer as this node is less than, equal to, or greater than the other.
         */
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
	 * @param data of type T 
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
	 * Return the height of the tree
	 * @return int the height of a tree given the root
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
		return  delete(root, d).getData();
	}
	
	public int optimalHeight() {
		return optimalHeight(root);
	}
	
	// Private methods.

//	private BSTNode delete(T toRemove, BSTNode r) {
//		
//		
//		// Base case
//        if (r == null)
//        	return r;
//       
//        int compResult = toRemove.compareTo(r.getData());
//        
//        if (compResult < 0) 
//        	 // case 1: node to delete is a leaf 
//        	r.setLeft(delete (toRemove, r.getLeft()));
//        else if (compResult > 0) {
//        	 // case 2: node to delete has one child 
//        	r.setRight(delete (toRemove, r.getRight()));
//        }
//        else if (r.getLeft() != null &&  r.getRight() != null) {
//        // case 3: node to delete has two children 
//        	  // Find in-order predecessor
//	        r.setData(findMin(r.getRight()));
//	        // delete the in-order successor
//	        r.setRight(delete(r.getData(), r.getRight()));
//        }
//        else {
//        	r = ( r.getLeft() != null ) ? r.getLeft() : r.getRight();
//        	}
//    	return r;
//    	
//    	}
	
	
	public BSTNode delete(BSTNode r, T val) {
		//base case - if node is null
	    if(r == null) {
	      return r;
	    }
	    int compResult = val.compareTo(r.getData());
	    if(compResult > 0) {
	      r.left = delete(r.getLeft(), val);
	    } else if(compResult < 0) {
	      r.right = delete(r.right, val);
	    } else {
	      if(r.left == null || r.right == null) {
	        BSTNode temp = r.getLeft() != null ? r.getLeft() : r.getRight();

	        if(temp == null) {
	          return null;
	        } else {
	          return temp;
	        }
	      } else {
	          BSTNode min = findMin(r);
	          r.setData(min.getData());
	          
	          r.setRight(delete(r.getRight(), min.getData()));
	          return r;
	        }
	    }
	    
	    return r;
	  }


	
// first attempt at delete method:
//        else if (r.isLeaf()) {
//        	size --;
//        	return null;
//        }
//		
//		// case 2: node to delete has one child 
//        else if (r.getLeft() == null &&  r.getRight() != null) {
//        	size --;
//        	r.setRight(null);
//        }
//        else if (r.getLeft() != null &&  r.getRight() == null) {
//        	size --;
//        	r.setLeft(null);
//        }
//		// case 3: node to delete has two children 
//	    else {
//	        // Find in-order predecessor
//	        r.setData(findMin(r.getRight()));
//	        // delete the in-order successor
//	        r.setRight(delete(r.getData(), r.getRight()));
//	    }
	
	
	private BSTNode findMin(BSTNode r) {
		//first attempt at findMin method:
//		   T minNode = r.getData();
//	        while (r.getLeft() != null) {
//	            minNode = (r.getLeft().getData());
//	            r = r.getLeft();
//	        }
//	        return minNode;
	        
	        if(r == null) {
	            return null;
	          }
	          
	          BSTNode temp = r.getRight();
	          
	          while(temp.left != null) {
	            temp = temp.left;
	          }
	          
	          return temp;
	        
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
	
	
	
	private void visit(BSTNode r) {
		Queue<T> queue = new LinkedList<T>();
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
	
	
	
	private class BSTIterator implements Iterator<T> {
		private Queue<T> queue = new LinkedList<T>();

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
		
		public static final int INORDER = 0;
		public static final int PREORDER = 1;
		public static final int POSTORDER = 2;
		public static final int LEVELORDER = 3;
		
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
		
	}


	@Override
	/** Return an in-order tterator over a tree.
	* @returns an iterator
	*/
	public Iterator<T> iterator() {
		return new BSTIterator();
	}
}
