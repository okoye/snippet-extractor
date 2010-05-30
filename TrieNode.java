import java.util.ArrayList;

/**
 * @author Chuka Okoye
 *
 */


public class TrieNode 
{
	protected char character;
	protected boolean endWord;
	protected ArrayList<TrieNode> childNodes;
	protected ArrayList<Integer> index;
	
	//TODO: Full Documentation
	public TrieNode(char character, boolean endword)
	{
		this.character = character;
		this.endWord = endword;
		this.childNodes = new ArrayList<TrieNode>();
		this.index = new ArrayList<Integer>();
	}
	
}
