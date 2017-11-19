import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPool {
	private Generator generator;
	private Random rnd = new Random();
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	private boolean verbose = false;
	public WordPool(String text)
	{
		generator = new Generator(text);
//		while(true)
//		{
//			generator = new Generator(text);
//			String newText = generate();
//			System.out.println(newText);
//			
//		}
	}
	public String generate()
	{
		int length = 50;
		String[] strings = new String[length];
		double[] weight = new double[length];
		for (int i = 0; i < length; i++) {
			String newText = generator.generate(3, 5);
			if(map.containsKey(newText))map.put(newText, map.get(newText) * 10);
			else map.put(newText, 2);
//			if(isRhyme(newText))
//			{
//				map.put(newText, map.get(newText) * length);
//			}
			if(isBetterRhyme(newText))
			{
				if(isSameSyllableCount(newText))
				{
					map.put(newText, map.get(newText) * 10);
					if(verbose)System.out.println("same) " + newText);
				}
				map.put(newText, map.get(newText) * 10);
			}
			if(isBestRhyme(newText))
			{
				map.put(newText, map.get(newText) * 20);
			}
			if(isPuntuationAfterRhyme(newText))
			{
				map.put(newText, map.get(newText) * 10);
			}
			if(isEndWithRhyme(newText))
			{
				map.put(newText, map.get(newText) * 10);
			}
			if(isStartAndEndRhyme(newText))
			{
				map.put(newText, map.get(newText) * 20);
			}
			if(isTripleRhyme(newText))
			{
				map.put(newText, map.get(newText) * 20);
			}
			if(isQuadtrupleRhyme(newText))
			{
				map.put(newText, map.get(newText) * 20);
			}
			if(isSoundexPair(newText))
			{
				map.put(newText, map.get(newText) * 15);
				if(verbose)System.out.println("soundex) " + newText);
			}
			if(isIngPair(newText))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("ing pair) " + newText);
			}
			if(isErePair(newText))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("ere pair) " + newText);
			}
			if(isThoughPair(newText))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("though pair) " + newText);
			}
			
			if(isFuckerPair(newText))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("though pair) " + newText);
			}
			if(isEvenPair(newText))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("though pair) " + newText);
			}
			
			if(isMelloPair(newText))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("though pair) " + newText);
			}
			
			if(isHerePair(newText))
			{
				map.put(newText, (map.get(newText) / 20));
				if(verbose)System.out.println("though pair) " + newText);
			}
			if(isFightPair(newText))
			{
				map.put(newText, (map.get(newText) / 20));
				if(verbose)System.out.println("fight pair) " + newText);
			}
			
			if(newText.startsWith("often"))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("often start) " + newText);
			}
			if(newText.startsWith("pondering"))
			{
				map.put(newText, (map.get(newText) / 10));
				if(verbose)System.out.println("pondering start) " + newText);
			}
			weight[i] = map.get(newText);
			strings[i] = newText;
			if(verbose)System.out.println(i);
		}
		return strings[rouletteSelect(weight)];
	}

	public boolean isRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{3,20}).{4,100}\\3\\b.*");
	}
	public boolean isBetterRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{4,20}).{4,100}\\3\\b.*");
	}
	public boolean isBestRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{5,20}).{4,100}\\3\\b.*");
	}
	public boolean isPuntuationAfterRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{4,20})[\\.\\,\\?\\!] .{4,100}\\3\\b.*");
	}
	public boolean isEndWithRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{4,20}).{4,100}\\3[\\.\\?\\!]");
	}
	public boolean isStartAndEndRhyme(String text)
	{
		return text.matches("(\\w{1,2})(\\w{4,20}).{4,100}\\2[\\.\\?\\!]");
	}
	public boolean isTripleRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{4,20}).{4,100}\\3.{4,100}\\3\\b.*");
	}
	public boolean isQuadtrupleRhyme(String text)
	{
		return text.matches("(.*)(\\w{1,2})(\\w{4,20}).{4,100}\\3.{4,100}\\3.{4,100}\\3\\b.*");
	}
	public boolean isSameSyllableCount(String text)
	{
		Pattern p = Pattern.compile("(.*)(\\w{1,2})(\\w{4,20})(.{4,100})(\\w{1,7})(\\3)\\b.*");
		Matcher m = p.matcher(text);
		if(!m.find(6))return false;
		String newText = m.group(2) + m.group(3);
		String otherText = m.group(5) + m.group(6);
		return isSameSyllableCount(newText, otherText);
	}
	public boolean isSameSyllableCount(String text, String otherText)
	{
		Pattern p = Pattern.compile("[aeiouy]+[^$e(,.:;!?)]");
		Matcher m = p.matcher(text);
		
		int syllables = 0;
		
		while(m.find())
		{
			syllables++;
		}
		
		p = Pattern.compile("[aeiouy]+[^$e(,.:;!?)]");
		m = p.matcher(otherText);
		int otherSyllables = 0;
		
		while(m.find())
		{
			otherSyllables++;
		}
		if(syllables == otherSyllables)return true;
		return false;
	}
	public int rouletteSelect(double[] weight) {
		double weight_sum = 0;
		for (int i = 0; i < weight.length; i++) {
			weight_sum += weight[i];
		}
		double value = rnd.nextDouble() * weight_sum;
		for (int i = 0; i < weight.length; i++) {
			value -= weight[i];
			if (value <= 0)
				return i;
		}
		return weight.length - 1;
	}
	public String soundex(String text){
		if(text.length() <= 1)return text;
		String newSoundex = text.substring(0, 1);
		text = text.split(" ")[0].toLowerCase();
		
		char[] code = text.toCharArray();
		char last = 0;
		for (int i = 1; i < code.length; i++) {
			if(last != code[i])
			{
				if(code[i] == 'b' || code[i] == 'f' || code[i] == 'p' || code[i] == 'v')
				{
					newSoundex += '1';
				}
				else if(code[i] == 'c' || code[i] == 'g' ||  code[i] == 'j' ||  code[i] == 'k' ||  code[i] == 'q' ||  code[i] == 's' ||  code[i] == 'x' ||  code[i] == 'z')
				{
					newSoundex += '2';
				}
				else if(code[i] == 'd' || code[i] == 't')
				{
					newSoundex += '3';
				}
				else if(code[i] == 'l')
				{
					newSoundex += '4';
				}
				else if(code[i] == 'm' || code[i] == 'n')
				{
					newSoundex += '5';
				}
				else if(code[i] == 'r')
				{
					newSoundex += '6';
				}
			}
			
			last = code[i];
		}
		int length = 6;
		while(newSoundex.length() < length)
		{
			newSoundex += '0';
		}
		newSoundex = newSoundex.substring(0, length);
		return newSoundex;
	}
	public boolean isSoundexPair(String text)
	{
		String[] textArr = text.split(" ");
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < textArr.length; i++) {
			if(words.contains(soundex(textArr[i])))return true;
			else words.add(soundex(textArr[i]));
		}
		return false;
	}
	public boolean isIngPair(String text)
	{
		return text.matches(".*ing.*ing.*");
		
	}
	
	public boolean isErePair(String text)
	{
		return text.matches(".*ere.*ere.*");
		
	}
	
	public boolean isThoughPair(String text)
	{
		return text.matches(".*ough.*ough.*");
		
	}
	
	public boolean isFuckerPair(String text)
	{
		return text.matches(".*fucker.*motherfucker.*") ||text.matches(".*motherfucker.*fucker.*");
		
	}
	
	public boolean isEvenPair(String text)
	{
		return text.matches(".*seven.*even.*");
		
	}
	
	public boolean isMelloPair(String text)
	{
		return text.matches(".*yello.*mello.*") || text.matches(".*mello.*yello.*");
		
	}
	
	public boolean isHerePair(String text)
	{
		return text.matches(".*here.*there.*") || text.matches(".*there.*here.*");
		
	}
	public boolean isFightPair(String text)
	{
		return text.matches(".*fight.*eight.*") || text.matches(".*eight.*fight.*");
		
	}
	public static void main(String[] args) {
		WordPool wordPool = new WordPool(Load.load("res/text"));
		
		while(true)
		{
			System.out.println(wordPool.generate());
		}
	}
}
