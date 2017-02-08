package com.kikebodi.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {
	
	public static String readFile(String filename) throws IOException{
		String content;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    content = sb.toString();
		}
		finally {
		    br.close();
		}
		
		return content;
	}
	
	public static String[] getStringOfWords(String textplain){
		return textplain.split("\n");
	}

	public static List<String> getListOfWords() throws IOException {
		String data = readFile("wordlist");
		return new ArrayList<>(Arrays.asList(getStringOfWords(data)));
	}

}
