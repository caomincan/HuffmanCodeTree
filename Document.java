import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Document{
	public final static char[] MASK ={0x80,0x40,0x20,0x10,0x08,0x04,0x02,0x01};
	//private HufTree code;
	private String[] var;
	private HashMap<String,Integer> Map;
	private HufTree head;
	
	Document(){
		var =new String[0];
		Map = null;
		head = null;
	}
	public String[] getValue(){return var;}
	public HashMap<String,Integer> getMap() { return Map;}
	public HufTree getTree() { return head;}
	// set value
	public void setValue(String[] var) { this.var=var;}
	public void setMap(HashMap<String,Integer> Map) { this.Map=Map;}
	public void setTree(HufTree head) { this.head=head;}
	/**
	 * According Map obtain heap list for building tree purpose
	 * @return
	 */
	public List<HufTree> trans2List(){
		if(Map==null) return null;
		List<HufTree> heap = new ArrayList<HufTree>();
		for(String key:Map.keySet()){
	    	HufTree node= new HufTree(key,Map.get(key));
	    	heap.add(node);
	    }
		Collections.sort(heap);
		return heap;
	}
	/**
	 *  build such Huffman coding tree
	 * @param heap
	 */
	public void buildTree(){
		List<HufTree> heap = trans2List();
		int size = heap.size();
		if(size == 0) head = null;
		if(size == 1){
			HufTree tmp1 = heap.get(0);
			head = new HufTree(tmp1.root(),null,tmp1.root().Weight());
		}
		while(size >=2){
			HufTree tmp1 = heap.get(0);
			HufTree tmp2 = heap.get(1);
			head = new HufTree(tmp1.root(),tmp2.root(),tmp1.root().Weight()+tmp2.root().Weight());
		    heap.remove(0);
		    heap.remove(0);
			heap.add(head);
			Collections.sort(heap);
			size = heap.size();
		}
	}
	/**
	 * Encode the documents based on key.txt file
	 * @param src
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public boolean encodeDoc(String src) throws Exception{
		  BufferedReader bufferedReader= null;
	       try{
	    	   bufferedReader = new BufferedReader(new FileReader(src)); 
           }catch(FileNotFoundException e){
	    	   System.out.print("No such file or directory\n");
	    	   return false;
	       }
	       File file = new File("compressed"+src); 
		   FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				System.out.print("Could not open or create key.txt\n");
				return false;
			}
		   PrintStream ps = new PrintStream(fos);
		   int c= 0;
		   String binary,C;
		   String mid = "";
		   char var=0;
     	  while ((c = bufferedReader.read()) != -1){
     		    binary = "";
     		    C = Character.valueOf((char) c).toString();
     		    binary = this.head.weight2code(this.head.root(), C, binary);
     		    mid += binary;
     		    if(mid.length()>8){
     		    	String tmp = mid.substring(0, 8);
     		    	mid = mid.substring(8);
     		    	var = 0;
     		    	for(int i=0;i<8;i++){
     		    		if(tmp.charAt(i) == '0'){
    		    			var <<=1;
    		    		}else{
    		    			var<<=1;
    		    			var |= 0x01;
    		    		}
     		    	}
     		    	ps.print(var);
     		    } // if mid lenght >8    
     	 } // end while EOF of file
     	  // if mid still have value
     	  if(mid.length()>0){
     		    var = 0;
		    	for(int i=0;i<8;i++){
		    	 if(i<mid.length())	{
		    		if(mid.charAt(i) == '0'){
		    			var <<=1;
		    		}else{
		    			var<<=1;
		    			var |= 0x01;
		    		}
		    	 }else
		    		 var<<=1;
		    	}
		    	ps.print(var);
     	  }
     	  bufferedReader.close();
     	  ps.close();
     	  return true;
	}
	/**
	 * Decode the text file according to key.txt
	 * @param src
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public boolean decodeDoc(String src) throws Exception{
		 BufferedReader bufferedReader= null;
	       try{
	    	   bufferedReader = new BufferedReader(new FileReader("compressed"+src)); 
         }catch(FileNotFoundException e){
	    	   System.out.print("No such file or directory\n");
	    	   return false;
	       }
	       File file = new File("re"+src); 
		   FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				System.out.print("Could not open or create re"+src+'\n');
				return false;
			}
		PrintStream ps = new PrintStream(fos);
	    int c = 0;
 		String result="";
 		HufBaseNode node;
 		node = this.head.root();
	     while ((c = bufferedReader.read()) != -1){
	    	 char x = (char) c;
	    	 for(int j=0;j<8;j++){
	    		 if((x&MASK[j])==0){
	    			 node = node.left();
	    			 }else{
	    				 node = node.right();
	    				 }
	    		 if(node.isLeaf()){
	    			 result =node.getEle();
	    			 ps.print(result);
	    			 node = this.head.root();
	    			 }
	    		 }
	    	 }// end while
	     bufferedReader.close();
	     ps.close();
	     return true;
	}
	/**
	 * Create or overwrite key.txt file store letters frequency information
	 * @param mode 0: do not output code for each letter; 1: output code
	 */
	public void writeKeyFile(int mode){
		
		if(this.Map == null){
			System.out.print("There is no key values exsits\n");
			return;
		} else{
			// create output file
			File file = new File("key.txt"); 
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				System.out.print("Could not open or create key.txt\n");
				return;
			}
			PrintStream ps = new PrintStream(fos);
			for(String elem:this.Map.keySet()){
				if(mode == 1){
					String result = new String();
					result= this.head.weight2code(this.head.root(), elem, result);
					System.out.print(elem+" "+this.Map.get(elem).toString()+" "+result+'\n');
				}
				// write in file
				ps.print(elem+" "+this.Map.get(elem).toString()+'\n');
				}
			ps.close();
			System.out.print("Key file writing has finished!\n");	
		}
	}
	/**
	 * Count each letters frequency in file
	 * @param filename
	 * @return boolean value for whether successful create
	 * @throws Exception 
	 */
	public boolean countFreq(String filename) throws Exception{

	       BufferedReader bufferedReader= null;
	       try{
	       // wrap a BufferedReader around FileReader
            bufferedReader = new BufferedReader(new FileReader(filename)); 
            }catch(FileNotFoundException e){
            	//throws Exception
	    	   System.out.print("No such file or directory\n");
	    	   return false;
	       }
        // use the readLine method of the BufferedReader to read one line at a time.
        // the readLine method returns null when there is nothing else to read.
		   String line=null;
		   String letter;
		   this.Map = new HashMap<String,Integer>();
      	  while ((line = bufferedReader.readLine()) != null){
      		  int length = line.length();
      		  for(int i=0;i<length;i++){
      			  letter = String.valueOf(line.charAt(i));
      			  if(this.Map.containsKey(letter)){
      				  this.Map.put(letter, this.Map.get(letter)+1);
      			  }else
      				  this.Map.put(letter, 1);
      			  } // end line  	
      		  } // end bufferedReader
      // close the BufferedReader when we're done
      	  bufferedReader.close();
      	  return true;
	}
	/**
	 * Read key.txt store value into HashMap
	 * @return boolean whether success
	 * @throws Exception
	 */
	public boolean readKeyFile() throws Exception{
		 BufferedReader bufferedReader= null;
	       try{
	       // wrap a BufferedReader around FileReader
          bufferedReader = new BufferedReader(new FileReader("key.txt")); 
          }catch(FileNotFoundException e){
          	//throws Exception
	    	   System.out.print("No key.txt\n");
	    	   return false;
	      }
	       String line=null;
		   String letter;
		   String value;
		  if(this.Map==null) this.Map = new HashMap<String,Integer>();
      	  while ((line = bufferedReader.readLine()) != null){
      		  letter = line.substring(0,1);
      		  value = line.substring(2);
      		  this.Map.put(letter, Integer.parseInt(value));
      	 } 	
      	  bufferedReader.close(); 
      	  System.out.print("Success Read key.txt\n");
	      return true;
	}
    @SuppressWarnings("unused")
	public static void main(String[] args) throws Exception
    {
    	Document doc = new Document();
    	HufTree code;
    	String name;
    	if(args.length != 0){
    		switch(args[0]){
    		 // write key file     
    		case "-g":
    			  if(args.length != 2) {
    				  System.out.print("No input text file!\n");
    				  break;
    			  }
    		       name = args[1];
    		       if(doc.countFreq(name)){
    		    	   doc.buildTree();
    		    	   doc.writeKeyFile(1);
    		       }
                  break;
            // according key file compress file
    		case "-b":
    			if(args.length != 2) {
  				  System.out.print("No input text file!\n");
  				  break;
  				  }
    			  name = args[1];
    			if(!doc.readKeyFile()){
    				doc.countFreq(name);
    				doc.buildTree();
    				doc.writeKeyFile(0);
    			}else{
    				doc.buildTree();
    			}
    			if(doc.encodeDoc(name)){
    				System.out.print("Success Encode the txt file!\n");
    			}else{
    				System.out.print("Fail to Encode the txt file!\n");
    			}
  			  break;
  			// Retrieve the compressed file 
    		case "-r":
    			if(args.length != 2) {
    				  System.out.print("No input text file!\n");
    				  break;
    				  }
      			  name = args[1];
      			if(!doc.readKeyFile()){
    				doc.countFreq(name);
    				doc.buildTree();
    				doc.writeKeyFile(0);
    			}else{
    				doc.buildTree();
    			}
      			if(doc.decodeDoc(name)){
      				System.out.print("Success Decode the txt file!\n");
      			}else{
      				System.out.print("Fail to Decode the txt file!\n");
      			}
    			break;
    		// demo case
    		case "-demo":
    			   HashMap<String,Integer> map = new HashMap<String,Integer>();
    			   String[] var={"C","D","E","K","L","M","U","Z"};
    			   Integer[] freq={32,42,120,7,42,24,37,2};
    			   for(int i=0;i<var.length;i++)
    				   map.put(var[i],freq[i]);
    	    		 // Store Map to Document object
    	            doc.setMap(map);
    	            doc.buildTree();
    	            // out put result
    	      		for(int i=0;i<var.length;i++){
    	      			String result = new String();
    	      			result= doc.getTree().weight2code(doc.getTree().root(), var[i], result);
    	      			System.out.print(var[i]+" "+freq[i].toString()+" "+result+'\n');
    	      			}
                   break;
            default:
            	System.out.print("No match augment!\n");
            	break;
          }// switch case
            
    	} // if args != 0	
    }// end main
}