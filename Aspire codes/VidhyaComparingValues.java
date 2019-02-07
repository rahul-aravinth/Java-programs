import java.util.*;



class Sort 
{ 
    
	int intValue; 
    
	String stringValue;
 
  
    
	public Sort(int intValue) 
{ 
        
		this.intValue = intValue;
        
		stringValue = String.valueOf(intValue);
    
	} 
    
	//constructor overloading
    
	public Sort(String stringValue) { 
        
		this.stringValue = stringValue; 
   
	 } 
   

	 //used to print the values
    
	public String toString() { 
        	
		return this.stringValue; 
    
	} 

} 
  
  


class SortByValue implements Comparator<Sort> 
{ 
    
	public int compare(Sort a, Sort b) {
	//used to sort the values
    
	if((a.intValue>0)&&(b.intValue>0)) {

            return a.intValue-b.intValue;
        
	}
else
  {

            return a.stringValue.compareTo(b.stringValue); 
        }

	} 

}
  


// Driver class 

public class VidhyaComparingValues 
{ 
    
	public static void main (String[] args) { 
        
		ArrayList<Sort> ar = new ArrayList<Sort>(); 
        
		ar.add(new Sort("a")); 
        
		ar.add(new Sort(1)); 
        
		ar.add(new Sort(16)); 
        
		ar.add(new Sort("*"));
		
		ar.add(new Sort(2));
		
		ar.add(new Sort(-2));
 
  
        
		System.out.println("Unsorted"); 
        
	
		for (int i=0; i<ar.size(); i++) 
            
			System.out.println(ar.get(i)); 

        

		Collections.sort(ar, new SortByValue()); 
  
        
	
		System.out.println("\nSorted "); 
        

		for (int i=0; i<ar.size(); i++) 
            
			System.out.println(ar.get(i)); 
    
	} 

} 