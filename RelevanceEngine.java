import java.util.ArrayList;
import java.util.Collections;

/**
 * The relevanceEngine is responsible for merging, multiple snippets and also computing
 * relevancy of each snippet using the scoring computer. 
 * 
 * @author Chuka Okoye
 *
 */
public class RelevanceEngine 
{
	private ScoreComputer snippetScore = null;
	private ArrayList<Snippet> allSnippets = null;
	private DocumentParser docParser = null;
	private final double mergeFactor = 0.8; //For merge to occur, new snippet must exceed this factor.
	private final int snippetSize = 150; //Approximately 150 characters
	
	/**
	 * @param parser, a DocumentParser.
	 */
	public RelevanceEngine(DocumentParser parser)
	{
		docParser = parser;
	}
	
	/**
	 * This method merges 'greatly overlapping' snippets, tags keywords and scores
	 * each snippet.
	 * 
	 * @param snippets, a list of all snippets extracted from trie tree
	 * @param searchTerms, an array of parsed search keywords.
	 * @return
	 */
	public Snippet getMostRelevant(ArrayList<Snippet> snippets, String[] searchTerms)
	{
		snippetScore = new ScoreComputer(searchTerms);
		allSnippets = snippets;
		
		//Sort snippets by start index
		Collections.sort(allSnippets, new StartIndexComparator());
		
		//Algorithm to merge and delete or skip overlapping snippets
		int index = 0;
		while((index+1) <= (allSnippets.size()-1)) //Must be at least two snippets remaining
		{
			int snippet1Start = allSnippets.get(index).getStartIndex();
			int snippet1End = allSnippets.get(index).getEndIndex();
			int snippet2Start = allSnippets.get(index+1).getStartIndex();
			
			Snippet snippet1 = null; 
			Snippet newSnippet2 = null;
			Snippet oldSnippet2 = null;
			
			if(snippet1End > snippet2Start && snippet1Start < snippet2Start) //Overlap exists!
			{
				snippet1 = snippetScore.computeScore(allSnippets.get(index),false);
				newSnippet2 = snippetScore.computeScore(docParser.getSnippet(snippet1End+1),false);
				oldSnippet2 = snippetScore.computeScore(allSnippets.get(index+1), false);
				
				if((mergeFactor*oldSnippet2.getScore()) <= newSnippet2.getScore()) //Most search key words must fall in non-overlapping portion
				{
					snippet1 = mergeSnippets(snippet1, newSnippet2, false);
					snippet1.setScore(snippet1.getScore()+newSnippet2.getScore());
					allSnippets.set(index, snippet1);
					allSnippets.remove(index+1);
				}
				else //Don't merge and just delete second snippet since it is redundant.
				{
					allSnippets.set(index, snippet1); //replace with most recent snippet.
					allSnippets.remove(index+1);
				}
			}
			else
			{
				index++;
			}
		}
		
		//Recompute scores with tags enabled this time.
		for(int i=0; i<allSnippets.size(); i++)
			allSnippets.set(i, snippetScore.computeScore(allSnippets.get(i), true));
		
		//Sort by scores
		Collections.sort(allSnippets, new ScoreComparator());
		
		//Snippets should be approximately 150 characters in length
		Snippet tempSnippet = null;
		for(int i=allSnippets.size()-1; i>0; i--)
		{
			tempSnippet = mergeSnippets(allSnippets.get(i), allSnippets.get(i-1), true);
			allSnippets.set(i, tempSnippet);
			allSnippets.remove(i-1);
			
			if(tempSnippet.getEndIndex() - tempSnippet.getStartIndex() >= snippetSize)
				return tempSnippet;
		}
		
		if(allSnippets.size() == 0)
			return null;
		
		return allSnippets.get(allSnippets.size()-1); //Just return highest scored snippet. Should not reach here if document is large enough.
	}
	
	/**
	 * A routine to merge two snippets. Accepts two snippets not necessarily in proper order in document
	 * @param snippet1
	 * @param snippet2
	 * @param runon, indicates if both snippets should be connected with a run on '...'
	 * @return Snippet, the merged snippet.
	 */
	private Snippet mergeSnippets(Snippet snippet1, Snippet snippet2, boolean runon)
	{
		ArrayList<Word> tempWord = snippet1.getWordList();
		ArrayList<Word> buff = new ArrayList<Word>();
		
		for(int i=0; i<tempWord.size(); i++)
			buff.add(tempWord.get(i));
		
		if(runon)
			buff.add(new Word("...",-1));
		
		tempWord = snippet2.getWordList();
		for(int i=0; i<tempWord.size(); i++)
			buff.add(tempWord.get(i));
		
		Snippet aSnip = new Snippet();
		for(int i=0; i<buff.size(); i++)
			aSnip.addWord(buff.get(i));
		
		return aSnip;
	}
}
