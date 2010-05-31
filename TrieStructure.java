import java.util.ArrayList;

/**
 * My implementation of a Trie Tree. Stores important information
 * about the contents of a document and their word indexes. Searches
 * can be conducted in near O(md) where m is size of alphabet and d length of 
 * search string.
 * 
 * @author Chuka Okoye
 */

public class TrieStructure 
{
	
	private TrieNode rootNode;
	
	/**
	 * Simple constructor to initialize our Trie Data Structure with an empty element 
	 * @param nothing
	 * */
	public TrieStructure()
	{
		//Should only create the empty root node
		 rootNode = new TrieNode('.',false);
	}
	
	
	/**
	 * Function responsible for inserting new words into a Trie Data Struture
	 * @param word, a word to be inserted into Trie Data Structure
	 * @param occurIndex, the index in the document the word occurs
	 * @return true if insert successful
	 */
	public boolean putWord(String word, int occurIndex)
	{
		if(checkValidity(word))
		{
			char[] charArray = word.toLowerCase().toCharArray();
			ArrayList<TrieNode> tempNodeList = rootNode.childNodes;
			boolean inserted;
			
			for(int i=0; i<charArray.length; i++)
			{
				inserted = false;
				while(inserted == false)
				{
					if(rootNode.childNodes.isEmpty()) //test for first insertion
					{
						inserted = true;
						TrieNode temp;
						if(charArray.length == 1) //test for endWord
						{
							temp = new TrieNode(charArray[0],true);
							
						}
						else
						{
							temp = new TrieNode(charArray[0], false);
						}
						rootNode.childNodes.add(temp);
						tempNodeList = rootNode.childNodes.get(0).childNodes;
					}
					else
					{
						int index = getIndexChar(charArray[i],tempNodeList);
						inserted = true;
						//Two cases, either current char exists already or does not.
						if(index != -1) 
						{
							if((charArray.length-1) == i) //test for endWord [REFACTOR]
							{
								TrieNode pointer = tempNodeList.get(index);
								pointer.endWord = true;
							}
							tempNodeList.get(index).index.add(occurIndex);
							tempNodeList = tempNodeList.get(index).childNodes;
						}
						else
						{
							if((charArray.length-1) == i) //test for endWord [REFACTOR]
							{
								TrieNode aNode = new TrieNode(charArray[i],true);
								aNode.index.add(occurIndex);
								tempNodeList.add(aNode);
								tempNodeList = tempNodeList.get(tempNodeList.indexOf(aNode)).childNodes;
							}
							else
							{
								TrieNode aNode = new TrieNode(charArray[i], false);
								aNode.index.add(occurIndex);
								tempNodeList.add(aNode);
								tempNodeList = tempNodeList.get(tempNodeList.indexOf(aNode)).childNodes;
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * A function to traverse the Trie tree and retrieve all indexes a word occurs in a document. 
	 * @param word, the word whose indexes in document should be returned
	 * @return ArrayList<Integer>, a list containing all indexes of specified word else returns null if not exist.
	 */
	public ArrayList<Integer> getWordIndexes(String word)
	{
		if(word.equals(""))
			return null; //Error check.
		ArrayList<TrieNode> tempNodeList = rootNode.childNodes;
		char[] charArray = word.toLowerCase().toCharArray();
		int index = -1;
		for(int i=0; i<charArray.length; i++)
		{
			index = getIndexChar(charArray[i], tempNodeList);
			if(index != -1)
			{
				if(i != (charArray.length-1))
					tempNodeList = tempNodeList.get(index).childNodes;
			}
			else
				return null; //Word simply does not exist in tree
		}
		try
		{
			if(tempNodeList.get(index).endWord)
				return tempNodeList.get(index).index; //Oleee! found the exact word.
			else
				return null; //A similar word exists in tree that has same the prefix as search phrase.
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return null; //Will never occur
		}
	}
	
	/**
	 * A function to search through a list of nodes to find a corresponding character
	 * @param value, value to be checked
	 * @param nodes, a list whose characters will be checked against
	 * @return the index of the node containing such character. -1 if not found
	 */
	private int getIndexChar(char value, ArrayList<TrieNode> nodes)
	{
		for(int i=0; i<nodes.size(); i++)
		{
			if(nodes.get(i).character == value)
				return i;
		}
		return -1;
	}
	
	/**
	 * Makes sure only the letters a-z, A-Z and 0-9 exist in the string
	 * @param value, string to be verified
	 * @return true if it is a valid string
	 */
	private boolean checkValidity(String string)
	{
		char[] myArray = null;
		try
		{
			myArray = string.toCharArray();
		}
		catch(NullPointerException e)
		{
			return false;
		}
		int value;
		for(int i=0; i<myArray.length; i++)
		{
			value = (int)myArray[i];
			if(!((value >= 48 && value <= 57) || (value >= 65 && value <= 90) || (value >= 97 && value <= 122)))
				return false;
		}
		return true;
	}
	
}
