
public class FHsdTreeNode<E> extends FHtreeNode<E >{
	
	protected boolean deleteMe;
	
	//default constructor
	public FHsdTreeNode() {
		super();
		deleteMe = false;
	}
	
	//overloader
	public FHsdTreeNode (E storageTN, FHtreeNode<E> siblingTN, FHtreeNode<E> childTN, FHtreeNode<E> previousTN, boolean deleteMeTN)
	{
		super(storageTN, siblingTN, childTN, previousTN);
		deleteMe = deleteMeTN;
	}
	
	//accessors
	public FHsdTreeNode<E> getSibling() {
		return (FHsdTreeNode<E>) this.sib;
	}
	
	public FHsdTreeNode<E> getFirstChild() {
		return (FHsdTreeNode<E>) firstChild; 
	}
	
	public FHsdTreeNode<E> getPrevious() {
		return (FHsdTreeNode<E>) prev;
	}
	
	public FHsdTreeNode<E> getRoot() {
		return (FHsdTreeNode<E>) myRoot;
	}
	
	public boolean checkDeleted() 
	{
		return deleteMe;
	}
	
	public void setDeleted(boolean deleteMeTN) {
		this.deleteMe = deleteMeTN;
	}
	
	//sibling mutator
	public boolean setSibling(FHtreeNode<E> siblingTN)
	{
		if (siblingTN == null)
			return false;
			
		this.sib = siblingTN;
		return true;
	}
	
	//child mutator
	public boolean setFirstChild(FHtreeNode<E> childTN)
	{
		if (childTN == null)
			return false;
			
		this.firstChild = childTN;
		return true;
	}
	
	public boolean setPrevious (FHtreeNode<E> previousTN)
	{
		if (previousTN == null)
			return false;
			
		this.prev = previousTN;
		return true;
	}
	
	public boolean setRoot(FHtreeNode<E> rootTN)
	{
		if (rootTN == null)
			return false;
		
		this.myRoot = rootTN;
		return true;
	}
}
