package com.kikebodi.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.primitives.Chars;
import com.kikebodi.main.BruteForceWorker;
import com.kikebodi.main.FileManager;
import com.kikebodi.main.Utils;

public class TDD {

	@Test
	public void test1() throws IOException {
		String result = FileManager.readFile("wordlist");
		Assert.assertFalse("The class couldn't be created", result == null);
	}
	
	@Test
	public void test2() throws IOException{
		String result = FileManager.readFile("wordlist");
		Assert.assertNotEquals("The file couldn't be read", "", result);
	}
	
	@Test
	public void test3() throws IOException{
		String data = FileManager.readFile("wordlist");
		String[] result = FileManager.getStringOfWords(data);
		Assert.assertTrue("Couldn't tokenize the data", result.length > 0);
	}

	
	@Test
	public void test5() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String rndm = "This is a random string";
		String result = Utils.getMD5(rndm);
		Assert.assertTrue("Couldn't return MD5 hash", result.length() == 32);
		Assert.assertEquals("The generated hash is not correct", "81fbfa9def2364dc881d1eebe4e4d58d", result);
		
		rndm = "another random hash";
		result = Utils.getMD5(rndm);
		Assert.assertTrue("Couldn't return MD5 hash", result.length() == 32);
		Assert.assertEquals("The generated hash is not correct", "8473460f7d7547f69390a254f444e31e", result);
		
		
	}

	
	
	/**
	 * Intersection test [reflection] (private method)
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test6() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		String word = "hola";
		List<Character> charList = Chars.asList('o','h','l','a','b','c');
		List<String> unuseful_dictionary = new ArrayList<>();
		BruteForceWorker aux = new BruteForceWorker(unuseful_dictionary, "unuseful_anagram");
		Method method = aux.getClass().getDeclaredMethod("intersection", new Class[] { String.class, List.class});
		method.setAccessible(true);
		List<Character> result = (List<Character>) method.invoke(aux, word, charList);
		Assert.assertEquals("Couldn't get the intersection for a complete word", Chars.asList('h','o','l','a'), result);
		
		word = "hola";
		charList = Chars.asList('o','h','a','b','c');
		result = (List<Character>) method.invoke(aux, word, charList);
		Assert.assertEquals("Couldn't get the intersection for a missing letter in anagram", Chars.asList('h','o','a'), result);
		
		word = "hoa";
		charList = Chars.asList('o','h','l','a','b','c');
		result = (List<Character>) method.invoke(aux, word, charList);
		Assert.assertEquals("Couldn't get the intersection for a missing letter in word", Chars.asList('h','o','a'), result);	
	}
	
	/**
	 * inverseIntersection() test, using reflection (private method)
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test7() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String word = "hola";
		List<Character> charList = Chars.asList('o','h','l','a','b','c');
		List<String> unuseful_dictionary = new ArrayList<>();
		BruteForceWorker aux = new BruteForceWorker(unuseful_dictionary, "unuseful_anagram");
		Method method = aux.getClass().getDeclaredMethod("inverseIntersection", new Class[] { String.class, List.class});
		method.setAccessible(true);
		List<Character> result = (List<Character>) method.invoke(aux, word, charList);
		Assert.assertEquals("Couldn't get the intersection for a complete word", Chars.asList('b','c'), result);
		
		word = "holaz";
		charList = Chars.asList('o','h','l','a','b','c');
		result = (List<Character>) method.invoke(aux, word, charList);
		Assert.assertEquals("Couldn't get the intersection for a extra letter in word", Chars.asList('b','c'), result);
		
		word = "xyz";
		charList = Chars.asList('o','h','l','a','b','c');
		result = (List<Character>) method.invoke(aux, word, charList);
		Assert.assertEquals("Couldn't get the intersection for complete different letters", Chars.asList('o','h','l','a','b','c'), result);
	}
	
	/**
	 * isContainedInAnagram() test, using reflection (private method)
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void test8() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List<String> dicctionary = Arrays.asList("hola", "jola", "cocacola", "xyz", "a", "holabc", "holabcc");
		List<Character> charList = Chars.asList('o','h','l','a','b','c','h','h');
		
		BruteForceWorker aux = new BruteForceWorker(new ArrayList<>(), "unuseful_anagram");
		Method method = aux.getClass().getDeclaredMethod("isContainedInAnagram", new Class[] { String.class, List.class});
		method.setAccessible(true);
		
		Assert.assertTrue("Doesn't recognise a character in anagram", (Boolean)method.invoke(aux, dicctionary.get(4), charList));
		Assert.assertTrue("Doesn't recognise words in anagram", (Boolean)method.invoke(aux, dicctionary.get(0), charList));
		Assert.assertFalse("Doesn't recognise a missing letter in anagram", (Boolean)method.invoke(aux, dicctionary.get(1), charList));
		Assert.assertFalse("It allows repeated Characters", (Boolean)method.invoke(aux, dicctionary.get(2), charList));
		Assert.assertFalse("It allows everything", (Boolean)method.invoke(aux, dicctionary.get(3), charList));	
		}
	

}
