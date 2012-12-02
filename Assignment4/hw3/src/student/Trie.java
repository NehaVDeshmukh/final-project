package student;

/** A collection of strings. */
public class Trie {
	private TrieNode root;

	/**
	 * creates a new empty trie
	 */
	public Trie(){
		root = new TrieNode((char)0, false);
	}
	
    /** 
     * Add {@code elem} to the collection.
     */
    public void insert(String elem) {
    	TrieNode n = root;
    	boolean child = true;
    	String e = elem;
        while(child && e.length()>0)
        {
        	try {
				n = n.getChild(e.charAt(0));
				e = elem.substring(1);
			} catch (NoSuchFieldException nfe) {
				child = false;
			}
        }
        
        if(e.length() == 0){
        	n.makeTerminator();
        	return;
        }
        
        for(int i=0; i<e.length()-1; i++)
        {
        	n.addChild(new TrieNode(e.charAt(i), false));
        	try {
				n = n.getChild(e.charAt(i));
			} catch (NoSuchFieldException e1) {
				System.out.println("I just added this child so this error should never happen.");
			}
        }
       
        n.addChild(new TrieNode(e.charAt(0), true));
    }
    
    /** 
     * Remove {@code elem} from the collection, if it is there. 
     */
    public void delete(String elem) {
    	TrieNode n = root;
    	boolean child = true;
    	String e = elem;
        while(child && e.length()>0)
        {
        	try {
				n = n.getChild(e.charAt(0));
				e = elem.substring(1);
			} catch (NoSuchFieldException nfe) {
				child = false;
			}
        }
        
        n.removeTerminator();
    }
    
    /** 
     * True if this trie contains {@code elem}, false otherwise. 
     */
    public boolean contains(String elem) {
    	TrieNode n = root;
    	boolean child = true;
    	String e = elem;
        while(child && e.length()>0)
        {
        	try {
				n = n.getChild(e.charAt(0));
				e = elem.substring(1);
			} catch (NoSuchFieldException nfe) {
				child = false;
			}
        }
        if(child)
        	return n.terminates();
        
        return false;
    }
}
