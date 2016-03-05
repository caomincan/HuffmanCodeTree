
public class HufLeafNode implements HufBaseNode {
    private String element;
    private int weight;
    
    HufLeafNode() { this.element=""; this.weight=0;}
    HufLeafNode(String element,int weight){
    	this.element=element;
    	this.weight = weight;
    }
    public String getEle(){ return element;}
    public String toString(){
    	return Integer.toString(weight)+": "+element;
    }
	@Override public boolean isLeaf() {return true;}
	@Override public int Weight() {return weight;}
	@Override public HufBaseNode left() {return null;}
	@Override public HufBaseNode right() {return null;}

}
