import java.util.ArrayList;
import java.util.Scanner;

public class Program {

	ArrayList<String> pairs = new ArrayList<String>();
	public Program()
	{
		String text = Load.load("res/text");
		Generator generator = new Generator(text);
		while(true)
		{
			String genText = generator.generate(3, 5);
			
			String genText2 = generator.generate(3, 5);
			
			String genText3 = generator.generate(3, 5);
			
			String genText4 = generator.generate(3, 5);
			
			String genText5 = generator.generate(3, 5);
			
			System.out.println(genText);
			System.out.println(genText2);
			System.out.println(genText3);
			System.out.println(genText4);
			System.out.println(genText5);
			
			Scanner sc = new Scanner(System.in); 
			if(sc.hasNext())
			{
				String str = sc.nextLine();
				if(str.equals("y"))
				{
					Save.save("res/rightText", Load.load("res/rightText").trim().replaceAll("\n", "") + "\n" + genText.trim().replaceAll("\n", ""));
				}
				else if(str.equals("n"))
				{
					Save.save("res/wrongText", Load.load("res/wrongText").trim().replaceAll("\n", "") + "\n" + genText.trim().replaceAll("\n", ""));
				}
				else if(str.equals("b"))
				{
					
					Save.save("res/pairsCopy", Load.load("res/wrongPairs").trim().replace("\n\n", ""));
				}
				else
				{
					System.out.println("invalid choice.");
				}
			}
		}
	}
	public static void main(String[] args) {
		new Program();
	}
}
