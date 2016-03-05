public interface HufBaseNode {
	// whether it is leaf
	boolean isLeaf();
	// what is the weight of the node
	 int Weight();
	// Output String
	String toString();
	// left
	HufBaseNode left();
	// right
	HufBaseNode right();
	// get Element
	String getEle();
}
