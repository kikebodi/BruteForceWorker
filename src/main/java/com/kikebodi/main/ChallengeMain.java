package com.kikebodi.main;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ChallengeMain {
	
	public static final String easy = "e4820b45d2277f3844eac66c903e84be";
	public static final String medium = "23170acc097c24edb98fc5488ab033fe";
	public static final String hard = "665e5bcb0c20062fe8abaaf4628bb154";
	public static final String anagram = "poultry outwits ants";
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException{
		//Start performance test
		long startTime = System.currentTimeMillis();
		//Get List<String> of words
		List<String> wordList = FileManager.getListOfWords();
		System.out.println("Total words: "+wordList.size());
		//Create and run the worker;
		BruteForceWorker myWorker = new BruteForceWorker(wordList,anagram);
		System.out.println("Remaining words: "+myWorker.getWordListSize());
		myWorker.solve();
		//Stop performance test
		long duration = (System.currentTimeMillis() - startTime)/1000;
		System.out.println("Done. Lasted "+duration+" secs");
		}
}

