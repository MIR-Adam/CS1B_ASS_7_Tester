
public class FHsdTree<E> extends FHtree<E> implements Cloneable {

	public int sizePhysical() {
		return super.size();
	}

	public boolean empty() {
		return size() == 0;
	}

	public FHsdTreeNode<E> addChild(FHsdTreeNode<E> treeNode, E s) {

		// is tree null?
		if (mSize == 0) {
			if (treeNode != null) {
				return null;
			}
			this.mRoot = new FHsdTreeNode<E>(s, null, null, null, false);
			((FHsdTreeNode<E>) mRoot).setRoot(mRoot);
			mSize = 1;
			return (FHsdTreeNode<E>) mRoot;
		}

		// is node null?
		if (treeNode == null) {
			return null;
		}

		// is the root set/does root belong to tree?
		if (treeNode.getRoot() != mRoot) {
			return null;
		}

		// initialize sd node
		FHsdTreeNode<E> sdNode = new FHsdTreeNode<E>(s, treeNode.getFirstChild(), null, treeNode, false);

		// set root for sd node
		sdNode.setRoot(mRoot);

		// set firstChild for sd node
		sdNode.setFirstChild(sdNode);

		// set previous sibling
		if (sdNode.getSibling() != null) {
			sdNode.getSibling().setPrevious(sdNode);
		}

		++mSize;
		return (FHsdTreeNode<E>) sdNode;
	}

	public FHsdTreeNode<E> find(FHsdTreeNode<E> root, E s, int branch) {
		FHsdTreeNode<E> returnFind;

		// check if root is null or deleted
		if (mSize == 0 || root == null || root.checkDeleted()) {
			return null;
		}

		// return storage
		if (root.getData().equals(s)) {
			return root;
		}

		if (branch > 0 && (returnFind = find(root.getSibling(), s, branch)) != null) {
			return (FHsdTreeNode<E>) returnFind;
		}
		// return find and increase branch
		return find(root.getFirstChild(), s, branch);
	}

	public FHsdTreeNode<E> find(E s) {
		return find((FHsdTreeNode<E>) mRoot, s, 0);
	}

	// searches tree for an object with matching storage, beginning with root
	public boolean remove(E s) {
		return remove((FHsdTreeNode<E>) (mRoot), s);
	}

	public boolean remove(FHsdTreeNode<E> root, E s) {
		FHsdTreeNode<E> removeMe = null;

		// null tree check
		if (mSize == 0 || root == null || root.checkDeleted()) {
			return false;
		}

		if ((removeMe = find(root, s, 0)) != null) {
			removeMe.setDeleted(true);
			return true;
		}
		return false;
	}

	public void display() {
		display((FHsdTreeNode<E>) (mRoot), 0);
	}

	// let be public so client can call on subtree
	public void display(FHtreeNode<E> treeNode, int branch) {
		String indent;

		// stop runaway indentation/recursion
		if (branch > (int) blankString.length() - 1) {
			System.out.println(blankString + " ... ");
			return;
		}

		if (treeNode == null)
			return;

		indent = blankString.substring(0, branch);

		// pre-order processing done here ("visit")
		System.out.println(indent + treeNode.data);

		// recursive step done here
		display(treeNode.firstChild, branch + 1);
		if (branch > 0)
			display(treeNode.sib, branch);
	}

	public <T extends Traverser<? super E>> void traverse(T func) {
		traverse(func, (FHsdTreeNode<E>) mRoot, 0);
	}

	public <T extends Traverser<? super E>> void traverse(T func, FHsdTreeNode<E> treeNode, int branch) {
		if (treeNode == null || treeNode.checkDeleted()) {
			return;
		}
		func.visit(treeNode.data);

		// increase branch
		traverse(func, treeNode.getFirstChild(), branch++);
		if (branch > 0) {
			traverse(func, treeNode.getSibling(), branch);
		}
	}

	public int size() {
		return size((FHsdTreeNode<E>) (mRoot), 0, 0);
	}

	public int size(FHsdTreeNode<E> treeNode, int branch, int returnSize) {
		// if treeNode is null
		if (treeNode == null) {
			return returnSize;
		}

		if (treeNode.checkDeleted() == false) {
			returnSize++;
			returnSize = size(treeNode.getFirstChild(), branch++, returnSize);
		}

		if (branch > 0) {
			returnSize = size(treeNode.getSibling(), branch, returnSize);
		}

		return returnSize;
	}

	void displayPhysical() {
		displayPhysical((FHsdTreeNode<E>) (mRoot), 0);
	}

	void displayPhysical(FHsdTreeNode<E> treeNode, int branch) {
		String indent;

		// stop runaway indentation/recursion
		if (branch > (int) blankString.length() - 1) {
			System.out.println(blankString + " ... ");
			return;
		}

		if (treeNode == null)
			return;

		indent = blankString.substring(0, branch);

		// pre-order processing done here ("visit")
		System.out.println(indent + treeNode.data);

		if (treeNode.checkDeleted()) {
			System.out.println("[DELETED]");
			System.out.println();
		}
		// recursive step done here
		display(treeNode.firstChild, branch + 1);
		if (branch > 0)
			displayPhysical(treeNode.getSibling(), branch);
	}

	public boolean collectGarbage() {

		int virtual = size();
		int physical = sizePhysical();

		if (virtual == 0) {
			return false;
		}

		if (physical > virtual) {
			// reset tree
			collectGarbage((FHsdTreeNode<E>) (mRoot), 0);
		}

		return physical > virtual;
	}

	public void collectGarbage(FHsdTreeNode<E> treeNode, int Branch) {

		if (treeNode == null || mSize == 0) {
			return;
		}

		if (treeNode.checkDeleted()) {
			// get previous node
			FHsdTreeNode<E> previous = treeNode.getPrevious();

			// remove the node
			super.removeNode(treeNode);
			
			collectGarbage(previous.getFirstChild(), Branch++);

			if (Branch > 0) {
				collectGarbage(previous.getSibling(), Branch);
			}
			return;
		}
		collectGarbage(treeNode.getFirstChild(), Branch++);
		if (Branch > 0) {
			collectGarbage(treeNode.getSibling(), Branch);
		}
		return;
	}
}