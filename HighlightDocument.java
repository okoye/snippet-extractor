import java.util.Iterator;
import java.util.ArrayList;

/**
 * Instantiates and connects all other critical classes used in 
 * processing such as TriStructure, RelevanceEngine, DocumentParser
 * 
 * @author Chuka Okoye
 */
public class HighlightDocument 
{
	private char[] documentCharRepresentation = null;
	private DocumentParser docParser = null;
	private TrieStructure trieTree = null;
	private RelevanceEngine relevanceEngine = null;
	private String[] searchTerms;
	
	/**
	 * Instantiates the DocumentParser and RelevanceEngine
	 * with supplied document.
	 * 
	 * @param doc, the document to be parsed
	 */
	public HighlightDocument(String doc)
	{
		documentCharRepresentation = doc.toCharArray();
		
		//Now instantiate all critical components:
		//parser, and special trie tree
		
		docParser = new DocumentParser(doc);
		trieTree = new TrieStructure();
		relevanceEngine = new RelevanceEngine(docParser);
		
		initializeTree();
	}
	
	/**
	 * Inserts each word in the document into the Trie Tree.
	 * This ensures that the document is scanned only once and all
	 * searches for keywords can be done in near log time.
	 */
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
	
	/**
	 * Searches for a searchKey in the tree. Also retrieves most relevant
	 * snippet.
	 * @param searchKey, user typed search string.
	 * @return String, the most relevant snippet.
	 */
	public String search(String searchKey)
	{
		searchKey = searchKey.toLowerCase();
		searchKey = searchKey.trim();
		
		searchTerms = searchKey.split("\\W"); //Split around all non-word characters.
		
		ArrayList<Integer> termIndex = new ArrayList<Integer>();
		ArrayList<Integer> temp;
		Snippet mostRelevantSnippet = null;
		
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
			
			for(int i=0; i<allSnippets.size(); i++)
				System.out.println("Snippet "+i+" : "+allSnippets.get(i));
			
			//Score each snippet and extract most relevant one.
			mostRelevantSnippet = relevanceEngine.getMostRelevant(allSnippets, searchTerms);
		}
		
		if(mostRelevantSnippet == null)
			return null;
		
		return mostRelevantSnippet.toString();
	}
	
}
