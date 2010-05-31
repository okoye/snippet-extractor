
/**
 * @author Chuka Okoye
 *
 */
public class ScoreComputer 
{
	String[] searchTerms;
	public ScoreComputer(String[] searchTerm)
	{
		this.searchTerms = searchTerm;
	}
	
	/**
	 * Responsible for computing the score values for each snippet and
	 * can if specified, tag each snippet with [[HIGHLIGH]] & [[ENDHIGHLIGHT]]
	 * 
	 * @param value, the Snippet whose score is to be computed
	 * @param tag, an indicator for tagging. Passing true will enable tagging.
	 * 
	 * @return The new snippet with a computed score and optionally tagged.
	 */
	protected Snippet computeScore(Snippet value, boolean tag)
	{
		int tempScore = 0;
		Word aWord = null;
		Snippet snippet = value;
		
		for(int i=0; i<snippet.getWordList().size(); i++)
		{
			aWord = snippet.getWordList().get(i);
			tempScore = score(aWord);
			if(tempScore != 0)
			{
				snippet.setScore(snippet.getScore()+tempScore);
				if(tag)
					snippet.setWord(i, aWord.tagWord());
			}
		}
		return snippet;
	}
	
	/**
	 * Compares each word in a snippet to terms in the search string.
	 * @param word, the word whose similarity to the search string is to be tested
	 * @return int, 0 if no match, else 1 if there is a string similar in search string.
	 */
	private int score(Word word)
	{
		String[] wordArray = word.getWord().split("\\W"); //Ignore all non-word characters when scoring.
		int score = 0;
		
		for(int j=0; j<wordArray.length; j++)
			for(int i=0; i<searchTerms.length; i++)
				if(wordArray[j].equalsIgnoreCase(searchTerms[i]))
					score++;
		return score;
	}
}
