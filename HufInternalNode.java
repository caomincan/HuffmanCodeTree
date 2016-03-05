
public class HufInternalNode implements HufBaseNode {
    private int weight;
    private HufBaseNode left;
    private HufBaseNode right;
    
    // constructor
    HufInternalNode() { this.weight=0; this.left=null;this.right=null;}
    HufInternalNode(HufBaseNode left,HufBaseNode right,int weight){
    	this.weight = weight;
    	this.left = left;
    	this.right = right;
    }
    public String toString(){
    	return Integer.toString(weight);
    }
	@Override public boolean isLeaf() {return false;}
    @Override public int Weight() { return weight;}
	@Override public HufBaseNode left() {return left;}
	@Override public HufBaseNode right() {return right;}
	@Override public String getEle() {return "";}

}
