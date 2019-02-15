/*
 * AuthorName : Rahul.T
 * Description : Program to sort set of values using comparator.
 * Created : 10-Feb-2019 4:30 PM.
 * Modified : 11-Feb-2019 8:30 PM.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Sort {

    /* The global Variables are declared here */
    int intValue;
    String stringValue;

	//constructor overloading
    Sort(int intValue) {
        this.intValue = intValue;
        stringValue = String.valueOf(intValue);
    }

    //constructor overloading
    Sort(String stringValue) {
        this.stringValue = stringValue;
    }

    //used to print the values
    public String toString() {
        return this.stringValue;
    }
}

/* Comparator interface is implemented here */
class SortByValue implements Comparator<Sort> {

    public int compare(Sort a, Sort b) {

        //used to sort the values
        if ((a.intValue > 0) && (b.intValue > 0)) {
            return a.intValue - b.intValue;
        } else {
            return a.stringValue.compareTo(b.stringValue);
        }
    }
}

class RahulSortingValues {
    private static void getValuesAndDisplaySorted(){
        ArrayList<Sort> ar = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of Inputs : ");
        int numberOfElements;
        int count,intcount=0,stringcount=0;
        numberOfElements = scanner.nextInt();
		System.out.println("Enter the Elements one by one");
        for(int i = 0; i < numberOfElements; i++){
            String a = scanner.next();
            count=0;
            for(int j=0;j<a.length();j++){
                if(Character.isDigit(a.charAt(j))){
                    count++;
                }
            }
            if((a.length() == count)) {
                ar.add(new Sort(Integer.parseInt(a)));	//Integers are inserted as integer here
                intcount++;
            } else{
                ar.add(new Sort(a));	//Symbols and Strings are inserted as String here
                stringcount++;
            }
        }

		System.out.println();
        System.out.println("Number of Integers = "+intcount);	//Number of Integers in the given elements
        System.out.println("Number of String and Symbols = "+stringcount);	//Number of String elements and Sysmbol elements in the given elements
        
        ar.sort(new SortByValue());
        System.out.println("\nSorted ");
        for (Sort values : ar) System.out.println(values); // The sorted elements are printed here
    }
    public static void main(String[] args) {
        getValuesAndDisplaySorted();
    }
} 