import java.util.ArrayList;

public class Table {
	public String word;
	public int num;
	public ArrayList<Word> words = new ArrayList<Word>();
	public void addWord(String nextWord)
	{
		num++;
		boolean found = false;
		for (int i = 0; i < words.size(); i++) {
			if(words.get(i).str == nextWord)
			{
				found = true;
				words.get(i).addWord();
			}
		}
		if(!found)
		{
			words.add(new Word(nextWord));
		}
	}
	public Table(String word)
	{
		this.word = word;
		//smoothing num, num would normally be 1
		num = 2;
	}
}
