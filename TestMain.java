import java.io.File;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author Chuka Okoye
 *
 */

public class TestMain {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) 
	{
		File file = new File("/Users/Chuka/Documents/workspace/Yelp_Java/src/testdata/test1.txt"); //Document
		String searchKeyword = "japanese koryori-ya koryori ya"; //Search keyword
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		StringBuffer buf = new StringBuffer();
		
		try
		{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			
			while(dis.available() != 0)
			{
				buf.append(dis.readLine());
			}
			
			fis.close();
			bis.close();
			dis.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File was not found");
		}
		catch(IOException e)
		{
			System.out.println("An I/O error occured.");
		}
		
		HighlightDocument highlighter = new HighlightDocument(buf.toString());
		System.out.println("**MOST RELEVANT** "+highlighter.search(searchKeyword));
	}

}
