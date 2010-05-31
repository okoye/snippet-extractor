import java.util.ArrayList;

/**
 * Class definition for a TrieNode: a single element in a Trie Tree
 * 
 * @author Chuka Okoye
 */


public class TrieNode 
{
	protected char character;
	protected boolean endWord;
	protected ArrayList<TrieNode> childNodes; //Can be improved by using a hash instead of a list. Reduces runtime to O(m)
	protected ArrayList<Integer> index;
	
	public TrieNode(char character, boolean endword)
	{
		this.character = character;
		this.endWord = endword;
		this.childNodes = new ArrayList<TrieNode>();
		this.index = new ArrayList<Integer>();
	}
	
}
