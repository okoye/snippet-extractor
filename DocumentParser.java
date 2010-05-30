import java.util.Iterator;

/**
 * @author Chuka Okoye
 *
 */
public class DocumentParser
{
	private String document;
	private char[] charArray;
	
	/**
	 * 
	 * @param document
	 */
	public DocumentParser(String document)
	{
		this.document = document;
		this.charArray = document.toCharArray();
	}
	
	/**
	 * A simple function that iterates over all "words" in a specified document
	 * @return Iterator, an iterator of document content
	 */
	public Iterator getAllWords() 
	{
		return new WordIterator();
	}
	
	/**
	 * Retrieves the ...
	 * @param index
	 * @return
	 */
	public Snippet getSnippet(int index)
	{
		WordIterator iter = new WordIterator();
		Word aWord;
		Snippet aSnippet = new Snippet();
		int count = 1;
		
		if(!iter.setIteratorPosition(index))
			return null;
		while(iter.hasNext() && count <= 15)
		{
			aWord = (Word)iter.nextFullWord();
			if(aWord != null)
				aSnippet.addWord(aWord);
			else
				break;
			count++;
		}
		return aSnippet;
	}
	
	
	/**
	 * A class implementing the iterable and iterator interface. It allows 
	 * the calling element to have full control over the iteration of a document.
	 * This means the class only parses the document until the next word rather 
	 * than the whole document content. Also, multiple calling elements can iterate over
	 * the same document without re-instantiating the Document Parser.
	 * 
	 * @author Chuka Okoye
	 *
	 */
	private class WordIterator implements Iterable<Word>, Iterator<Word>
	{
		private int currentPointer = 0;
		public Iterator<Word> iterator()
		{
			return this;
		}
		
		/**
		 * A simple method to retrieve all words iteratively in a document. Upon reaching
		 * a non alpha numeric term, it assumes the end of the word has been reached.
		 * @return Word, the next word in the document. null object if end is reached.
		 */
		public Word next()
		{
			StringBuffer tempBuffer = new StringBuffer();
			String tempString = "";
			Boolean seeking = true; //Check to ensure we are not seeking first letter of word.
			while((currentPointer < charArray.length) && (seeking == true))
			{
				//Look for beginning of next valid character by checking ASCII value
				//valid chars are 0-9, a-z, A-Z.
				if(compareASCIICode(charArray[currentPointer]))
				{
					tempBuffer.append(charArray[currentPointer]);
				}
				else if(tempBuffer.length() != 0) //Check to ensure we found a word
				{
					seeking = false; 
				}
				
				if(seeking == false) //We reached the end of some string
					break;
				currentPointer++;
			}
			tempString = tempBuffer.toString();
			if(tempString.length() != 0)
				return new Word(tempString,(currentPointer-tempString.length()));
			else
				return null;
		}
		
		/**
		 * A simple method to retrieve all words iteratively in a document. Upon reaching
		 * a space character, it assumes the end of the word has been reached.
		 * @return Word, the next word in the document. null object if end is reached.
		 */
		public Word nextFullWord()
		{
			StringBuffer tempBuffer = new StringBuffer();
			String tempString = "";
			Boolean seeking = true; //Check to ensure we are not seeking first letter of word.
			while((currentPointer < charArray.length) && (seeking == true))
			{
				//Look for beginning of next valid character
				//Space denotes the end of a word.
				if(charArray[currentPointer] != ' ')
				{
					tempBuffer.append(charArray[currentPointer]);
				}
				else if(tempBuffer.length() != 0) //Check to ensure we found a word
				{
					seeking = false; 
				}
				
				if(seeking == false) //We reached the end of some string
					break;
				currentPointer++;
			}
			tempString = tempBuffer.toString();
			if(tempString.length() != 0)
				return new Word(tempString,(currentPointer-tempString.length()));
			else
				return null;
		}
		
		public boolean hasNext()
		{
			return (currentPointer <= (charArray.length - 1));
		}
		
		/**
		 * Ensures only valid characters a-z, A-Z and 0-9 are parsed
		 * out of the document.
		 * @param character, the character to be verified
		 * @return boolean true if a valid character.
		 */
		private boolean compareASCIICode(char character)
		{
			int value = (int)character;
			return ((value >= 48 && value <= 57) || (value >= 65 && value <= 90) || (value >= 97 && value <= 122));
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Allows a calling element to set what index iteration should
		 * start from. It is up to the caller to ensure an index corresponding
		 * to the beginning of a word is supplied. 
		 * @param index, the beginning index position
		 * @return true if updated successfully.
		 */
		public boolean setIteratorPosition(int index)
		{
			if(index >= 0)
			{
				this.currentPointer = index;
				return true;
			}
			return false;
		}
	}
	
}
