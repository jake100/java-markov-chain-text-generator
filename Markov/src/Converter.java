import java.util.Random;

public class Converter {

	private String nouns = Load.load("res/nouns");
	private String verbs = Load.load("res/verbs");
	private String adjectives = Load.load("res/adjectives");
	private String madlibs = Load.load("res/madlib");
	private Random rnd = new Random();
	public String convert()
	{
		//# = noun
		//@ = verbs
		//* = adjectives
		String text = madlibs.split("\n")[rnd.nextInt(madlibs.split("\n").length)];
		
		text = text.replaceFirst("#", getNoun()).replaceFirst("#", getNoun());
		
		text = text.replaceFirst("@", getVerb()).replaceFirst("@", getVerb());
		
		text = text.replaceFirst("\\*", getAdjective()).replaceFirst("\\*", getAdjective());
		
		text = text.replaceFirst("\\$", getInt() + "").replaceFirst("\\$", getInt() + "");
		
		
		return is_areFix(a_anFix(text));
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
	public int getInt()
	{
		if(rnd.nextBoolean())return (rnd.nextInt(10000)/ 50);
		if(rnd.nextBoolean())return (rnd.nextInt(1000)/ 25);
		if(rnd.nextBoolean())return rnd.nextInt(100) / 15;
		if(rnd.nextBoolean())return rnd.nextInt(100) / 5;
		if(rnd.nextBoolean())return rnd.nextInt(10) / 2;
		return rnd.nextInt(5);
	}
	public String getNoun()
	{
		String  nounText = nouns.trim();
		String[] nouns = nounText.split("\n");
		
		return nouns[rnd.nextInt(nouns.length)].trim();
	}
	public String getVerb()
	{
		String  verbText = verbs.trim();
		String[] verbs = verbText.split("\n");
		
		return verbs[rnd.nextInt(verbs.length)].trim();
	}
	public String getAdjective()
	{
		String  adjectiveText = adjectives.trim();
		String[] adjectives = adjectiveText.split("\n");
		
		return adjectives[rnd.nextInt(adjectives.length)].trim();
	}
}
