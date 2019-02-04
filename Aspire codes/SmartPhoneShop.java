/*
AuthorName : Rahul.T
Program to Create a UI for Buying a smartphone from a list.
Created  on 30-Dec-18 12:00 PM
Modified on 07-Jan-19 12:00 PM

*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Here I had used Interface

interface InterfaceForSmartPhone{
	public void viewDetails();
	public int chooseOption();
	public void myKartContains();
	public void continueShoppingOrNot();
}

// Here I had implemented the DisplayDetails Interface

public class SmartPhoneShop implements InterfaceForSmartPhone{

BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

// Variables are declared here

private int option;
private int phoneoption;
private int paymentoption;
private int choice;
private int count=0;
private int[] typeprice = {700,1000,1100};
private int[] touchprice = {60000,70000,35000};
private int[] touchandtypeprice = {10000,22000,40000};
private int[] kart = {0,0};

public void viewDetails(){
	System.out.println();
	System.out.println("****************   WELCOME   ****************");
	System.out.println("******************   TO   *******************");
	System.out.println("************    YOUR MOBILES   **************");
	System.out.println();
	System.out.println("Please select your phone type -");
	System.out.println();
	System.out.println("1 - Touch Phones (SAMSUNG, APPLE, ONE PLUS)");
	System.out.println("2 - Type Phones (NOKIA)");
	System.out.println("3 - Touch and Type Phones (BLACKBERRY)");
	System.out.println("4 - Exit");
	option = chooseOption();
	showPhonesDetail(option);
	}
public int chooseOption(){
	System.out.println();
	System.out.print("Please choose an option - ");
	try{
	option = Integer.parseInt(bufferedReader.readLine());
	}
	catch(IOException e){
		e.printStackTrace();
	}
	if(option<1 || option>4){
		System.out.println("Choose options between 1 2 3 4");
		option = chooseOption();
	}
	return option;
	}
public void addToMyKart(int price){
	kart[0]++;
	kart[1] = kart[1]+price;
	System.out.println("Kart Contains "+kart[0]+" items and Price -"+kart[1]);
	}
public void myKartContains(){
	System.out.println("Kart Contains "+kart[0]+" items and Price -"+kart[1]);
	}
public void showPhonesDetail(int option){
	System.out.println();
	switch(option){
		case 1 :System.out.println(" The list of Touch Phones ");
			System.out.println("_________________________");
			System.out.println();
			System.out.println("1.SAMSUNG GALAXY S9\t Price 60,000Rs");
			System.out.println("Specs -\n8 GB RAM \n128 GB Storage \n6' inch Display ");
			System.out.println();
			System.out.println("2.APPLE X\t Price - 70,000Rs");
			System.out.println("Specs -\n4 GB RAM  \n128 GB / 256 GB Storage  \n6' inch Display ");
			System.out.println();
			System.out.println("3.ONE PLUS 6\t Price - 35,000Rs");
			System.out.println("Specs -\n10 GB RAM  \n128 GB / 256 GB Storage  \n6' inch Display ");
			System.out.println();
			System.out.println("4. Go Back to previous tab");
			break;

		case 2 :System.out.println(" The list of Type Phones ");
			System.out.println("_________________________");
			System.out.println();
			System.out.println("1.NOKIA 200\t Price - 700Rs");
			System.out.println("Specs -\n8 MB RAM  \n16 MB Storage  \n2.7' inch Display ");
			System.out.println();
			System.out.println("2.NOKIA 0100\t Price - 1,000Rs");
			System.out.println("Specs -\n16 MB RAM  \n32 MB Storage  \n2.7' inch Display ");
			System.out.println();
			System.out.println("3.NOKIA 1100\t Price - 1,100Rs");
			System.out.println("Specs -\n32 MB RAM  \n64 MB Storage  \n2.7' inch Display ");
			System.out.println();
			System.out.println("4. Go Back to previous tab");
			break;

		case 3 :System.out.println(" The list of Touch and Type Phones ");
			System.out.println("_________________________");
			System.out.println();
			System.out.println("1.BLACK BERRY Q9\t Price - 10,000Rs");
			System.out.println("Specs -\n3 GB RAM  \n64 GB Storage  \n5.5' inch Display ");
			System.out.println();
			System.out.println("2.BLACK BERRY CLASSIC\t Price - 22,000Rs");
			System.out.println("Specs -\n4 GB RAM  \n64 GB Storage  \n3.7' inch Display ");
			System.out.println();
			System.out.println("3.BLACK BERRY PASSPORT\t Price - 40,000Rs");
			System.out.println("Specs -\n6 GB RAM  \n128 GB / 256 GB Storage  \n5' inch Display ");
			System.out.println();
			System.out.println("4. Go Back to previous tab");
			break;
		case 4 :if(count==0){
				System.out.println("Thank you for Visiting our store");
				System.exit(0);
			}
			else{
				myKartContains();
				System.out.println();
				paymentOptions();
				exitMethod();
				System.exit(0);
			}
			break;
	}
	System.out.println();
	count++;
	addToKart();
	}

public void addToKart(){
	System.out.print("Enter the phone ID you want - ");
	try{
	phoneoption = Integer.parseInt(bufferedReader.readLine());
	}
	catch(IOException e){
		e.printStackTrace();
	}
	if(option == 1){
		addToMyKart(touchprice[phoneoption-1]);
		continueShoppingOrNot();
	}
	if(option == 2){
		addToMyKart(typeprice[phoneoption-1]);
		continueShoppingOrNot();
	}
	if(option == 3){
		addToMyKart(touchandtypeprice[phoneoption-1]);
		continueShoppingOrNot();
	}
	if(option == 4){
		viewDetails();
	}
	System.out.println();
	}
public void continueShoppingOrNot(){
	System.out.print("To Continue Shopping enter 1 else 0 - ");
	try{
	choice = Integer.parseInt(bufferedReader.readLine());
	}
	catch(IOException e){
		e.printStackTrace();
	}
	if(choice == 1)
		viewDetails();
	if(choice == 0)
		showPhonesDetail(4);
	else{
		System.out.println("Choose option 1 or 0 ");
		continueShoppingOrNot();
	}
	}
public void paymentOptions(){
	System.out.println();
	System.out.println("1.Cash on Delivery");
	System.out.println("2.Online Transaction");
	try{
	paymentoption = Integer.parseInt(bufferedReader.readLine());
	}
	catch(IOException e){
		e.printStackTrace();
	}
	if(paymentoption == 1){
	getCustomerDetails();
	System.out.println("Cash will be collected on Delivery");
	exitMethod();
	}
	else if(paymentoption == 2){
	getCustomerDetails();
	// cardDetails();
	//System.out.println("Enter the Debit Card Details ");
	//System.out.println("Enter the IFSC Code ");
	//System.out.println("Enter the Debit Card Number ");
	//System.out.println("Enter the Debit Card Owner Name ");
	//System.out.println("Transaction completed, Purchase Successfull");
	exitMethod();
	}
	else{
	System.out.println("Enter an option 1 or 2");
	}
	}
String name;
String dob;
String address;
public void getCustomerDetails(){
	name = validateName();
	dob = validateDob();
	address = validateAddress();
	}
public String validateName() {
	System.out.println("Enter your Name - ");
	try{
	name = bufferedReader.readLine();
	}
	catch(IOException e){
		e.printStackTrace();
	}
    	String regx = "^[a-zA-Z\\s]*$";
    	Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
    	Matcher matcher = pattern.matcher(name);
    	boolean nameFlag = matcher.find();
	if(nameFlag==false){
		System.out.println("The Name is not valid Try again Valid format is XXXXXX Y ");
		name = validateName();
	}
	return name;
	}
public String validateDob(){
	System.out.println("Enter your Date of Birth - ");
	try{
	dob = bufferedReader.readLine();
	}
	catch(IOException e){
		e.printStackTrace();
	}
	Pattern pattern = Pattern.compile("^[0-3]\\d-[01]\\d-\\d{4}$");
    	Matcher matcher = pattern.matcher(dob);
	boolean dobFlag = matcher.find();
	if(dobFlag==false){
		System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY");
		dob = validateDob();
	}
	return dob;
	}
public String validateAddress() {
	System.out.println("Enter your Name - ");
	try{
	address = bufferedReader.readLine();
	}
	catch(IOException e){
		e.printStackTrace();
	}
    	String regx = "^[0-9]{3}\\d,[a-zA-Z\\s]*$";
    	Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
    	Matcher matcher = pattern.matcher(address);
    	boolean nameFlag = matcher.find();
	if(nameFlag==false){
		System.out.println("The Address is not valid Try again Valid format is 0-9[2] XYZ ");
		name = validateName();
	}
	return name;
	}
public void exitMethod(){
	System.out.println("***********   THANK YOU   ************");
	System.out.println();
	System.out.println("Product is on the way to delivery");
	}

// In main I had created an instance for the Interface

public static void main(String args[]){
	InterfaceForSmartPhone interfaceForSmartPhone= new SmartPhoneShop();
	interfaceForSmartPhone.viewDetails();
	}
}
