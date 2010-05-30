import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Chuka Okoye
 *
 */
public class HighlightDocument 
{
	private char[] documentCharRepresentation = null;
	private DocumentParser docParser = null;
	private TrieStructure trieTree = null;
	private ScoreComputer snippetScore = null;
	private String[] searchTerms;
	
	/**
	 * 
	 * @param doc
	 */
	public HighlightDocument(String doc)
	{
		documentCharRepresentation = doc.toCharArray();
		
		//Now instantiate all critical components:
		//parser, and special trie tree
		
		docParser = new DocumentParser(doc);
		trieTree = new TrieStructure();
		
		initializeTree();
	}
	
	private void initializeTree()
	{
		Iterator<Word> iter = docParser.getAllWords();
		Word temp = null;
		while(iter.hasNext())
		{
			temp = iter.next();
			if(temp != null)
				trieTree.putWord(temp.getWord(), temp.getStartIndex());
		}
	}
	
	public String search(String searchKey)
	{
		searchKey = searchKey.toLowerCase();
		searchKey = searchKey.trim();
		
		searchTerms = searchKey.split("\\W"); //Split around all non-word characters.
		
		ArrayList<Integer> termIndex = new ArrayList<Integer>();
		ArrayList<Integer> temp;
		if(searchTerms.length != 0)
		{
			//Retrieve all indexes of search terms from trieTree
			for(int i=0; i< searchTerms.length; i++)
			{
				temp = trieTree.getWordIndexes(searchTerms[i]);
				if(temp != null)
					termIndex.addAll(temp);
			}
			
			ArrayList<Snippet> allSnippets = new ArrayList<Snippet>();
			//Now extract snippets around each term search result.
			for(int i=0; i<termIndex.size(); i++)
			{
				allSnippets.add(docParser.getSnippet(termIndex.get(i)));
			}
			
			//Score each snippet
			snippetScore = new ScoreComputer(searchTerms);
			for(int i=0; i<allSnippets.size(); i++)
			{
				allSnippets.set(i, snippetScore.computeScore(allSnippets.get(i)));
			}
			
			//Sort from highest to lowest
			Collections.sort(allSnippets, new Snippet());
			
			for(int i=0; i<allSnippets.size(); i++)
				System.out.println(allSnippets.get(i).toString());
		}
		return "";
	}
	
}
