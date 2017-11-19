import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Save {
	public static void save(String path, String text)
	{
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println(text);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
}
