import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;

public class DocumentParserTest 
{
	DocumentParser aParser;
	String testString = "So it seems pretty likely that HTC will give us \nan Android-tablet at some point, although any hard and; fast details continue to. ";
	char[] charArray = testString.toCharArray();
	@Before
	public void setUp() throws Exception 
	{
		aParser = new DocumentParser(testString);
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testGetAllWords() 
	{
		Iterator itr = aParser.getAllWords();
		Word temp;
		
		while(itr.hasNext())
		{
			temp = (Word)itr.next();
			if(temp != null)
			{
				assertEquals(temp.getWord().charAt(0), charArray[temp.getStartIndex()]);
				System.out.println("Computed index ["+temp.getWord()+"] ["+temp.getStartIndex()+ "]  || Real value at index ["+ charArray[temp.getStartIndex()]+"]");
			}
		}
	}
	
	@Test
	public void testGetSnippet()
	{
		Snippet aSnip = aParser.getSnippet(0);
		assertEquals(aSnip.getEndIndex(),73);
		aSnip = aParser.getSnippet(118);
		assertEquals(aSnip.getEndIndex(),128);
	}

}
