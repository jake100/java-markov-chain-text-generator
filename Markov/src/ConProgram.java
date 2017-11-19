import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ConProgram{
	
	static String text = Load.load("res/text");
	WordPool pool = new WordPool(text);
	int threadNum = 0;
	
	public ConProgram(int threadNum)
	{
		this.threadNum = threadNum;
	}
	public void start()
	{
		System.out.println("start: " + threadNum);
		this.run();
	}
	public void run() {
		while(true)
		{
			String genText = pool.generate();
			System.out.println("thread: " + threadNum);
			System.out.println(genText);
		}
	}
	public static void main(String[] args)
	{
		ConProgram con = new ConProgram();
		con.start();
	}

}
