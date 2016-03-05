import java.util.ArrayList;
import java.util.List;

public class HufTree implements Comparable<HufTree> {
	private HufBaseNode root;
	
	
    HufTree(String element,int weight){
    	root = new HufLeafNode(element,weight);
    }
    HufTree(HufBaseNode lf,HufBaseNode rg,int weight){
    	root = new HufInternalNode(lf,rg,weight);
    }
    public HufBaseNode root(){
    	return root;
    }
    public String toString(){
    	return root.toString();
    }
    /**
     * Given a ID return its binary code
     * @param ID
     * @return
     */
    public String weight2code(HufBaseNode root,String key,String result){
    	if(root == null || root.isLeaf()) return result;
    	if(root.left().getEle().compareTo(key)==0) return result+"0";
    	else if(root.right().getEle().compareTo(key)==0) return result+"1";
    	
    	String lf = "0"+weight2code(root.left(),key,result);
    	String rg ="1"+weight2code(root.right(),key,result);
    	if(lf.length()>rg.length()) result =lf;
    	else if(lf.length()<rg.length()) result =rg;
   
    	return result;
    }
    // travesal the tree
    public List<List<String>> travesal(HufBaseNode root,List<List<String>> result,int level){
    	if(root==null) return result;
    	List<String> tmp;
    	level++;
    	if(level>result.size()){
    		tmp = new ArrayList<String>();
    		result.add(tmp);
    	}
    	else {
    		tmp = result.get(level-1);
    	}
    	tmp.add(root.toString());
    	result = travesal(root.left(),result,level);
    	result = travesal(root.right(),result,level);
    	return result;
    }
	@Override public int compareTo(HufTree o) {
		// TODO Auto-generated method stub
		if(root.Weight()> o.root().Weight()) return 1;
		else if(root.Weight()<o.root().Weight()) return -1;
		else return 0;
	}

}
