import java.util.Comparator;

/**
 * A simple class defining how two snippets can be compared
 * using their start index.
 * @author Chuka
 *
 */

public class StartIndexComparator implements Comparator<Snippet>
{
	public int compare(Snippet snip1, Snippet snip2) 
	{
		int snip1StartIndex = snip1.getStartIndex();
		int snip2StartIndex = snip2.getStartIndex();
		
		if(snip1StartIndex > snip2StartIndex)
			return -1;
		else if(snip1StartIndex == snip2StartIndex)
			return 0;
		else
			return 1;
	}
}
