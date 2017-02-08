package com.kikebodi.main;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.primitives.Chars;

/**
 * This class will do all the hard brute force work.
 * @author Kike Bodi (www.kikebodi.com)
 *
 */
public class BruteForceWorker {
	
	final private List<Character> anagram;
	final private List<String> wordList;

	/**
	 * It creates the worker object. To run it we should use solve() method.
	 * @param input
	 * @param anagram
	 */
	public BruteForceWorker(List<String> input, String anagram) {
		//Prepare anagram
		anagram = anagram.replaceAll(" ", "");
		this.anagram = new ArrayList<>(Chars.asList(anagram.toCharArray()));
		//Prepare the word list: Remove impossible and repeated words
		this.wordList = removeImposibleWords(input, this.anagram).stream().distinct().collect(Collectors.toList());;
		
	}
	
	/**
	 * We need to perform a brute force work so we use this method to start.
	 * It can take few hours (depending of the input).
	 * Currently it has 4 loops but the the computational complexity is < O(n^4) because we remove the impossible words each loop.
	 */
	public void solve(){
		for(String firstWord : wordList){	
			List<Character> currentAnagram1 = inverseIntersection(firstWord, anagram);
			List<String> currentDictionary1 = removeImposibleWords(wordList, currentAnagram1);
			//System.out.println(anagram);
			if(currentAnagram1.size() == 0){
				checkSolution(firstWord);
			}
			for(String secondWord : currentDictionary1){
				List<Character> currentAnagram2 = inverseIntersection(secondWord, currentAnagram1);
				List<String> currentDictionary2 = removeImposibleWords(currentDictionary1, currentAnagram2);
				//System.out.println(currentAnagram1);
				if(currentAnagram2.size() == 0){
					checkSolution(firstWord+" "+secondWord);
				}
				for(String thirdWord: currentDictionary2){
					List<Character> currentAnagram3 = inverseIntersection(thirdWord, currentAnagram2);
					List<String> currentDictionary3 = removeImposibleWords(currentDictionary2, currentAnagram3);
					//System.out.println(currentAnagram2);
					if(currentAnagram3.size() == 0){
						checkSolution(firstWord+" "+secondWord+" "+thirdWord);
					}
					for(String fourthWord : currentDictionary3){
						List<Character> currentAnagram4 = inverseIntersection(fourthWord, currentAnagram3);
						//List<String> currentDictionary3 = removeImposibleWords(currentDictionary2, currentAnagram3);
						if(currentAnagram4.size() == 0){
							checkSolution(firstWord+" "+secondWord+" "+thirdWord+" "+fourthWord);
						}
					}
				}
			}
	}
	}
	
	/**
	 * Compare the hashes of the @param to the defined hashes.
	 * @param solution
	 */
	private void checkSolution(String solution){
		String md5="";
		try {
			md5 = Utils.getMD5(solution.trim());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			System.out.println("Couldn't get the MD5");
			e.printStackTrace();
		}
		//System.out.println(solution+" : "+md5);
		switch(md5){
			case ChallengeMain.easy:
				System.out.println("Easy: "+solution);
				break;
			case ChallengeMain.medium:
				System.out.println("Difficult: "+solution);
				break;
			case ChallengeMain.hard: 
				System.out.println("Hard: "+solution);
				break;
		}
	}
	
	/**
	 * We need to clean the dictionary from the impossible words in order to reduce complexity costs.
	 * This operation cost O(n) but avoids so many iterations in the following loop.
	 * @param wordList
	 * @param match
	 * @return clean List
	 */
	private ArrayList<String> removeImposibleWords(List<String> wordList, List<Character> match){
		ArrayList<String> aux = new ArrayList<String>();
		for (Iterator<String> iter = wordList.listIterator(); iter.hasNext(); ) {
		    String a = iter.next();
		    if(isContainedInAnagram(a, match)){
		    	aux.add(a);
		    	}
		}		
		return aux;
		}
	
	/**
	 * @param word
	 * @param anagram
	 * @return true is it's contained in anagram. false if not.
	 */
	private boolean isContainedInAnagram(String word, List<Character> anagram){
		List<Character> intersection = intersection(word,anagram);
		return word.length() == intersection.size() && word.length() <= anagram.size();
	}
	
	/**
	 * It performs the intersection between to sets of characters.
	 * @param word
	 * @param anagramList
	 * @return A(intersection)B
	 */
	private List<Character> intersection(String word, List<Character> anagramList){
		List<Character> aux = new ArrayList<Character>();
		List<Character> newAnagramList = new ArrayList<>(anagramList);
		List<Character> wordCharList = new ArrayList<>(Chars.asList(word.toCharArray()));
		
		for(Character c : wordCharList){
			if(newAnagramList.remove(c))
				aux.add(c);
		}
		return aux;
	}
	
	/**
	 * Despite it's name it only returns the inverse intersection of the anagramList items.
	 * Is short, it removes the characters of anagramList that contains the String word.
	 * @param word
	 * @param anagramList
	 * @return current anagram
	 */
	private List<Character> inverseIntersection(String word, List<Character> anagramList){
		List<Character> newAnagramList = new ArrayList<>(anagramList);
		List<Character> wordCharList = new ArrayList<>(Chars.asList(word.toCharArray()));
		
		for(Character c : wordCharList){
			newAnagramList.remove(c);
		}	
		return newAnagramList;
	}	
	
	/**
	 * 
	 * @return current dictionary length
	 */
	public int getWordListSize(){
		return wordList.size();
	}
}
