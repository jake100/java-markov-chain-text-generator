import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Load {
	public static String load(String path)
	{
		String everything = "";
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while(line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	return everything;
	}
}
