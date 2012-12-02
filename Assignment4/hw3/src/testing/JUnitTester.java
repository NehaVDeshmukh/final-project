package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.*;

import student.BloomFilter;
import student.HashTable;
import student.Trie;

public class JUnitTester {
	private Scanner urlScanner, testScanner;
	private HashTable<String, String> ht;
	private Trie t;
	private BloomFilter<String> bf;

	@Before
	public void openFile() throws FileNotFoundException {
		File urls = new File("/Users/lauraherrle/Documents/workspace/hw3/src/blacklist100k.txt");
		File tests = new File("/Users/lauraherrle/Documents/workspace/hw3/src/blacklist1mil.txt");
		urlScanner = new Scanner(urls);
		testScanner = new Scanner(tests);
		ht = new HashTable<String, String>(11);
		t = new Trie();
		bf = new BloomFilter<String>(null, 1000000000, 30);
	}

	@Test
	public void hashImport() {
		urlScanner.reset();
		while(urlScanner.hasNext()){
			String s = urlScanner.nextLine();
			ht.put(s, s);
		}
	}
	
	@Test
	public void hashCheck() {
		testScanner.reset();
		while(testScanner.hasNext()){
			String s = testScanner.nextLine();
			ht.containsKey(s);
		}
	}
	
	@Test
	public void hashDelete() {
		testScanner.reset();
		while(testScanner.hasNext()){
			String s = testScanner.nextLine();
			ht.remove(s);
		}
	}
	
	
	@Test
	public void bloomImport() {
		urlScanner.reset();
		while(urlScanner.hasNext()){
			String s = urlScanner.nextLine();
			bf.insert(s);
		}
	}
	
	@Test
	public void bloomCheck() {
		testScanner.reset();
		while(testScanner.hasNext()){
			String s = testScanner.nextLine();
			bf.mightContain(s);
		}
	}
	
	@Test
	public void trieImport() {
		urlScanner.reset();
		while(urlScanner.hasNext()){
			String s = urlScanner.nextLine();
			t.insert(s);
			System.out.println("string inserted to trie" + s);
		}
	}
	
	@Test
	public void trieCheck() {
		testScanner.reset();
		while(testScanner.hasNext()){
			String s = testScanner.nextLine();
			t.contains(s);
		}
	}
	
	
	@Test
	public void trieDelete() {
		testScanner.reset();
		while(testScanner.hasNext()){
			String s = testScanner.nextLine();
			t.delete(s);
		}
	}
}
