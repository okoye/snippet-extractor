import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TrieStructureTest 
{

	@Before
	public void setUp() throws Exception 
	{
		
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testPutWord() 
	{
		TrieStructure x = new TrieStructure();
		
		assertTrue(x.putWord("chuka", 25));
		assertTrue(x.putWord("cab", 30));
		assertTrue(x.putWord("0chuka", 10));
		assertTrue(x.putWord("xylophone", 17));
		assertTrue(x.putWord("random", 1000));
		assertTrue(x.putWord("a", 70));
		assertTrue(x.getWordIndexes("chuka").contains(25));
		assertTrue(x.getWordIndexes("xylophone").contains(17));
		assertTrue(x.getWordIndexes("rabbit") == null);
		
		assertFalse(x.putWord("-", 21)); 
		assertFalse(x.putWord(null, 42)); //Should never happen
		
	}

}
