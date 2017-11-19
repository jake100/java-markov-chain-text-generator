import java.util.ArrayList;
import java.util.Random;

public class Generator {
	private Random rnd = new Random();
	public ArrayList<Table> tables = new ArrayList<Table>();
	public ArrayList<Table> firstWords = new ArrayList<Table>();
	private int minWords = 0, maxWords = 0, numWords = 0;
	private Converter converter = new Converter();
	private String[] lines;
	
	boolean verbose = false;
	public Generator(String text) {

		lines = text.trim().toLowerCase().split("\n");
		String[] words = text.trim().toLowerCase().replaceAll("\n", " ").split(" ");

		for (int i = 0; i < words.length - 1; i++) {
			if (inTable(words[i])) {
				tables.get(i).addWord(words[i]);
			} else {
				Table newTable = new Table(words[i]);
				newTable.addWord(words[i]);
				tables.add(newTable);
			}

			String[] sentances = text.trim().toLowerCase().split("\n");

			for (int j = 0; j < sentances.length; j++) {
				String word = sentances[j].split(" ")[0];
				Table table = new Table(word);
				if(firstWords.size() != 0)
				{
					
					boolean found = false;
					for (int k = 0; k < firstWords.size(); k++) {
						if(firstWords.get(k).word.equals(word))
						{
							found = true;
							firstWords.get(k).num++;
						}
					}
					if(!found)firstWords.add(table);
				}
				else
				{
					firstWords.add(table);
				}
			}
		}
	}
	public String generate(int minWords, int maxWords) {
		this.minWords = minWords;
		this.maxWords = maxWords;
		this.numWords = randomizeNumWords();
		String text = createString().trim().toLowerCase();
		String[] singles = Load.load("res/wrongSingles").trim().toLowerCase().split("\n");
		
		String[] pairs = Load.load("res/wrongPairs").trim().toLowerCase().split("\n");
		
		String[] beginnings = Load.load("res/wrongBeginnings").trim().toLowerCase().split("\n");
		
		String[] endings = Load.load("res/wrongEndings").trim().toLowerCase().split("\n");
		
		text = text.toLowerCase().trim().replaceAll("\\n ", "\\n");
		for (int i = 0; i < singles.length; i++) {
			if(text.trim().toLowerCase().contains(singles[i].trim().toLowerCase()))
			{
				if(verbose)System.out.println("single) " + singles[i]);
				return generate(minWords, maxWords);
			}
		}
		for (int i = 0; i < pairs.length; i++) {
			if(text.trim().toLowerCase().contains(pairs[i].trim().toLowerCase()))
			{
				if(verbose)System.out.println("pairs) " + pairs[i]);
				return generate(minWords, maxWords);
			}
		}
		
		for (int i = 0; i < beginnings.length; i++) {
			if(text.trim().toLowerCase().startsWith(beginnings[i].replaceAll("\\.", "").toLowerCase().trim()))
			{
				if(verbose)System.out.println("beginning) " + beginnings[i]);
				return generate(minWords, maxWords);
			}
		}
		for (int i = 0; i < endings.length; i++) {
			if(text.trim().endsWith(" " + endings[i].replaceAll("\\.", "") + ".") || text.endsWith(" " + endings[i].trim().toLowerCase().replaceAll("\\.", "") + "?") || text.endsWith(" " + endings[i].trim().toLowerCase().replaceAll("\\.", "") + "..."))
			{
				if(verbose)System.out.println("ending) " + endings[i]);
				return generate(minWords, maxWords);
			}
		}
		if(text.matches("(.*)(\\b)(\\w{3,30}).{1,140} \\3.*"))
		{
			if(verbose)System.out.println("double) " + text);
			return generate(minWords, maxWords);
		}
		if(text.contains("null"))
		{
			if(verbose)System.out.println("null) " + text);
			return generate(minWords, maxWords);
		}
		if(isFourPuntuation(text.trim().toLowerCase()))
		{
			if(verbose)System.out.println("four puntuation) " + text);
			return generate(minWords, maxWords);
		}
		if(isShortSentances(text.trim().toLowerCase()))
		{
			if(verbose)System.out.println("is short) " + text);
			return generate(minWords, maxWords);
		}
		if(isLongSentances(text.trim().toLowerCase()))
		{
			if(verbose)System.out.println("is long) " + text);
			return generate(minWords, maxWords);
		}
		text = text.replaceAll("\n ", "\n");
		text = text.replaceAll("  ", " ");
		text = text.replaceAll("!s", "'s");
		if(text.endsWith(","))
		{
			text = text.substring(0, text.length() - 1);
			text = text + ".";
		}
		text.replace("null ", "");
		return is_areFix(a_anFix(text));
	}
	public String[] getWords()
	{
		String[] words = new String[tables.size()];
		for (int i = 0; i < tables.size(); i++) {
			words[i] = tables.get(i).word;
		}
		return words;
	}
	public String createString()
	{
		String text = "";
		double[] weights = new double[4];
		weights[0] = 2.5;
		weights[1] = 2.5;
		weights[2] = 15;
		weights[3] = 2.5;
//		weights[4] = 2.5;
//		weights[5] = 1.5;
		int index = rouletteSelect(weights);

		String currentWord = "";
		String lastWord = "null";
		for (int i = 0; i < numWords; i++) {

			if (text.equals("")) {
				text += getFirstWord();
			} else if (index == 0) {
				text += tables.get(rnd.nextInt(tables.size())).word;
			} else if (index == 1) {
				currentWord = getRndWord(currentWord);
				text += currentWord;
			} else if (index == 2) {
				String current = "";
				if (text.split(" ").length == 1) {
					current = text.split(" ")[0].toLowerCase();
				} 
				else {
					current = text.split(" ")[text.split(" ").length - 1];
				}
				currentWord = getNextWord(current.toLowerCase());
			} else if(index == 3) {
				String current = "";
				if (text.split(" ").length == 1) {
					current = text.split(" ")[0];
				} 
				else {
					current = text.split(" ")[text.split(" ").length - 1];
				}
				currentWord = getNextType(current).replaceAll("  ", " ");

			} else if(index == 4)
			{
				if(currentWord.length() > 0)return currentWord + " " + converter.convert();
				return converter.convert();
			} else if(index == 5)
			{
				if(currentWord.length() > 0)return currentWord + " " + lines[rnd.nextInt(lines.length)].trim().toLowerCase();
				return lines[rnd.nextInt(lines.length)];
			}
			if (currentWord.trim().toLowerCase().equals(lastWord.trim().toLowerCase())) {
				int oldNumWords = numWords;
				numWords = 1;
				lastWord = getRndWord(lastWord);
				if(text.length() > 0)text += " " + createString();
				else text += createString();
				numWords = oldNumWords;
			} else {
				text += " " + getRndWord(currentWord);
			}
			lastWord = currentWord.trim().toLowerCase();
			if (i == numWords - 1) {
				Char[] chars = new Char[4];
				chars[0] = new Char(".", 10);
				chars[1] = new Char("?", 5);
				chars[2] = new Char("!", 2.5);
				chars[3] = new Char("...", 1);
				text = endChar(chars, text);

			} else {
				if(text.length() > 0)text = text + " ";
			}
			index = rouletteSelect(weights);
		}
		if (text.equals("")) return createString();
		return text.replaceAll(" i ", " I ").replaceAll(" i'm ", " I'm ").replaceAll("\\,\\.", "\\.").trim().toLowerCase().replaceAll("  ", " ");
	}

	public String getFirstWord() {
		double[] weight = new double[firstWords.size()];
		if (firstWords.size() == 1)
			return tables.get(0).word;
		for (int i = 0; i < weight.length; i++) {
			weight[i] = firstWords.get(i).num;
		}
		if (weight.length == 0)
			return "null";
		int index = rouletteSelect(weight);
		return tables.get(index).word.trim();
	}

	public String getRndWord(String current) {
		double[] weight = new double[tables.size()];
		for (int i = 0; i < weight.length; i++) {
			weight[i] = tables.get(i).num;
		}
		if (weight.length == 0)
			return "null";
		int index = rouletteSelect(weight);
		if (current.equals(tables.get(index).word))
			return getRndWord(current);
		return tables.get(index).word.trim();
	}

	public String getNextWord(String current) {
		Table table = new Table("Null");
		if (tables.size() == 0)
			return "null";
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).word.equals(current)) {
				table = tables.get(i);
			}
		}
		double[] weight = new double[table.words.size()];
		for (int i = 0; i < weight.length; i++) {
			weight[i] = table.words.get(i).num;
		}
		if (weight.length == 0)
			return "null";
		int index = rouletteSelect(weight);
		return table.words.get(index).str.trim();
	}
	public String endChar(Char[] chars, String text) {
		if (chars.length == 0)
			return text;
		if (text.endsWith(".") || text.endsWith("!") || text.endsWith("?"))
			return text;
		double[] charWeights = new double[3];
		for (int i = 0; i < charWeights.length - 1; i++) {
			charWeights[i] = chars[i].weight;
		}
		String endText = chars[rouletteSelect(charWeights)].text;
		return text.trim() + endText.trim();
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
	public boolean isLongSentances(String text)
	{
		return text.matches(".*[\\w\\d]+ [\\w\\d]+ [\\w\\d]+ [\\w\\d]+");
	}
	public boolean inTable(String str) {
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).word == str)
				return true;
		}
		return false;
	}
	public String getNextType(String text)
	{
		text = text.toLowerCase().trim();
		if(isNoun(text))
		{
			return getVerb();
		}
		else if(isVerb(text))
		{
			return getAdjective();
		}
		else if(isAdjective(text))
		{
			return getNoun();
		}
		int index = rnd.nextInt(3);
		if(index == 0)return getVerb();
		if(index == 1)return getAdjective();
		if(index == 2)return getNoun();
		return "";
			
	}
	public String getNoun()
	{
		String  nounText = Load.load("res/nouns").trim();
		String[] nouns = nounText.split("\n");
		
		return nouns[rnd.nextInt(nouns.length)];
	}
	public String getVerb()
	{
		String  verbText = Load.load("res/verbs").trim();
		String[] verbs = verbText.split("\n");
		
		return verbs[rnd.nextInt(verbs.length)];
	}
	public String getAdjective()
	{
		String  adjectiveText = Load.load("res/adjectives").trim();
		String[] adjectives = adjectiveText.split("\n");
		
		return adjectives[rnd.nextInt(adjectives.length)];
	}
	public boolean isNoun(String text)
	{
		String  nounText = Load.load("res/nouns").trim();
		String[] nouns = nounText.split("\n");
		for (int i = 0; i < nouns.length; i++) {
			if(text.trim().equals(nouns[i]))
			{
				return true;
			}
		}
		return false;
	}
	public boolean isVerb(String text)
	{
		String  verbText = Load.load("res/verbs").trim();
		String[] verbs = verbText.split("\n");
		for (int i = 0; i < verbs.length; i++) {
			if(text.trim().equals(verbs[i]))
			{
				return true;
			}
		}
		return false;
	}
	public boolean isAdjective(String text)
	{
		String  adjectiveText = Load.load("res/adjectives").trim();
		String[] adjectives = adjectiveText.split("\n");
		for (int i = 0; i < adjectives.length; i++) {
			if(text.trim().equals(adjectives[i]))
			{
				return true;
			}
		}
		return false;
	}
	public String is_areFix(String text)
	{
		text = text.replaceAll("a are", "a is");
		text = text.replaceAll("b are", "b is");
		text = text.replaceAll("c are", "c is");
		text = text.replaceAll("d are", "d is");
		text = text.replaceAll("e are", "e is");
		text = text.replaceAll("f are", "f is");
		text = text.replaceAll("g are", "g is");
		text = text.replaceAll("h are", "h is");
		text = text.replaceAll("i are", "i is");
		text = text.replaceAll("j are", "j is");
		text = text.replaceAll("k are", "k is");
		text = text.replaceAll("l are", "l is");
		text = text.replaceAll("m are", "m is");
		text = text.replaceAll("n are", "n is");
		text = text.replaceAll("o are", "o is");
		text = text.replaceAll("p are", "p is");
		text = text.replaceAll("q are", "q is");
		text = text.replaceAll("r are", "r is");
		text = text.replaceAll("t are", "t is");
		text = text.replaceAll("u are", "u is");
		text = text.replaceAll("v are", "v is");
		text = text.replaceAll("w are", "w is");
		text = text.replaceAll("x are", "x is");
		text = text.replaceAll("y are", "y is");
		text = text.replaceAll("z are", "z is");
		
		text = text.replaceAll("s is", "s are");
		text = text.replaceAll("is you", "are you");
		return text;
	}
	public String a_anFix(String text)
	{
		//Vowels
		text = text.replaceAll(" a a", " an a");
		text = text.replaceAll(" a e", " an e");
		text = text.replaceAll(" a i", " an i");
		text = text.replaceAll(" a o", " an o");
		text = text.replaceAll(" a u", " an u");
		text = text.replaceAll(" a y", " an v");
		
		text = text.replaceAll(" an b", " a b");
		text = text.replaceAll(" an c", " a c");
		text = text.replaceAll(" an d", " a d");
		//e
		text = text.replaceAll(" an f", " a f");
		text = text.replaceAll(" an g", " a g");
		text = text.replaceAll(" an h", " a h");
		//i
		text = text.replaceAll(" an j", " a j");
		text = text.replaceAll(" an k", " a k");
		text = text.replaceAll(" an l", " a l");
		text = text.replaceAll(" an m", " a m");
		text = text.replaceAll(" an n", " a n");
		//o
		text = text.replaceAll(" an p", " a p");
		text = text.replaceAll(" an q", " a q");
		text = text.replaceAll(" an r", " a r");
		text = text.replaceAll(" an s", " a s");
		text = text.replaceAll(" an t", " a t");
		//u
		text = text.replaceAll(" an v", " a v");
		text = text.replaceAll(" an w", " a w");
		text = text.replaceAll(" an x", " a x");
		//y
		text = text.replaceAll(" an z", " a z");
		return text;
	}
	public boolean isThreeQuotes(String text)
	{
		return text.matches(".*['].*['].*[']");
	}
	public boolean isThreeHythens(String text)
	{
		return text.matches(".*[-].*[-].*[-]");
	}
	public boolean isFourPuntuation(String text)
	{
		return text.matches(".*[\\,\\.\\?\\!:].*[\\,\\.\\?\\!:].*[\\,\\.\\?\\!:].*[\\,\\.\\?\\!:]");
	}
	public boolean isShortSentances(String text)
	{
		return text.matches(".*[\\w\\d\\.]+[\\.\\,\\?] [\\w\\d]+[\\.\\,\\?] [\\w\\d]+[\\.\\,\\?].*");
	}
	public int getInt()
	{
		if(rnd.nextBoolean())return (rnd.nextInt(10000)/ 50);
		if(rnd.nextBoolean())return (rnd.nextInt(1000)/ 25);
		if(rnd.nextBoolean())return rnd.nextInt(100) / 25;
		if(rnd.nextBoolean())return rnd.nextInt(100) / 5;
		if(rnd.nextBoolean())return rnd.nextInt(10) / 2;
		return rnd.nextInt(5);
	}
	public int randomizeNumWords() {
		int index = rnd.nextInt((maxWords - minWords) + 1) + minWords;
		if (index < 1)
			return 0;
		return index;
	}
}