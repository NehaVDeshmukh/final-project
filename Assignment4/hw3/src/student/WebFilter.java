package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** 
 * A web filter with an interface to add elements to a blacklist and to test if
 * a given url exists in the blacklist.
 */
public class WebFilter {
	private HashTable<String, String> blacklist;
	
	public WebFilter(){
		blacklist = new HashTable<String, String>(53);
	}

    /**
     * Add all urls from the given file to this blacklist.
     */
    public void addToFilterFromFile(String filename) {
    	String url;
        try {
			Scanner s = new Scanner(new File(filename));
			while(s.hasNext()){
				url = s.nextLine();
				blacklist.put(url, url);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open the file.");
		}
    }

    /**
     * Add the URL to this blacklist.
     */
    public void addToFilter(String url) {
        blacklist.put(url, url);
    }

    /**
     * Returns whether or not the URL exists in the blacklist.
     */
    public boolean isBlacklisted(String url) {
        return blacklist.containsKey(url);
    }
    
    /**
     * Remove all URLs from the current blacklist.
     */
    public void clearBlacklist() {
        blacklist.clear();
    }

}
