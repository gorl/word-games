package edu.phmf.wordgames.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Random;
import java.util.Set;


import edu.phmf.wordgames.R;

import android.content.res.Resources;


public class Dictionary {
	private Map<Integer, Set<String>> dicts;
	private static Dictionary inst; 
	private static Random rand;
	public static Dictionary getInstance(){
		return inst;
	}
	public static void initInstance(Resources resources) {
		try {
			inst = new Dictionary(resources);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public String getRandomWord(int len){
		Set<String> dic = dicts.get(Integer.valueOf(len));
		if (dic != null) {
			int wordNumber = rand.nextInt(dic.size());
			for (String word : dic){
				if (wordNumber != 0)
					--wordNumber;
				else
					return word;
			}
		}
		return null;
	}
	
	public boolean check(String word) {
		Set<String> dic = dicts.get(Integer.valueOf(word));
		if (dic != null) {
			return dic.contains(word);
		}
		return false;
	}
	private Dictionary(Resources resources) throws IOException{
		rand = new Random();
		dicts = new HashMap<Integer, Set<String>>();		
		loadPackagedData(resources);
	}

	private void loadPackagedData(Resources resources) throws IOException {
		int i = 0;
		String loadWord = null;
		Set<String> curDic;
		InputStream is = resources.openRawResource(R.raw.dic);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "cp1251"));
			while((loadWord = reader.readLine()) != null) {
				++i;
				curDic = dicts.get(Integer.valueOf(loadWord.length()));
				if (curDic == null){
					dicts.put(Integer.valueOf(loadWord.length()),new HashSet<String>());
					curDic = dicts.get(Integer.valueOf(loadWord.length()));
				}
				System.out.println("lw " + loadWord + " cd " + curDic.size() + "  all " + i);
				curDic.add(loadWord);
			}
		} catch(Exception e){
			System.out.println("13err: " + e.getMessage());
		} finally {
			is.close();
		}
	}


}
