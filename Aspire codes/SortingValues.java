/*
 * AuthorName : Rahul.T
 * Description : Program to sort values.
 * Created : 01-Feb-2019 2:00 PM.
 * Modified : 01-Feb-2019 11:30 PM.
 */
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
  
public class SortData
{ 
	public static void main(String[] args) 
	{
		ArrayList arrayList= new ArrayList();
		arrayList.add("16");
		arrayList.add("a");
		arrayList.add("*");
		arrayList.add("3");
		arrayList.add("4");
		Comparator comparator = Collections.reverseOrder();
		Collections.sort(arrayList,comparator);
		System.out.println("Elements in List");
		for(int i=arrayList.size()-1;i>=0;i--)
		{
 			System.out.println(arrayList.get(i));
		}
	}
}