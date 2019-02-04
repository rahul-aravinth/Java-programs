/*
 * AuthorName : Rahul.T
 * Description : Program to validate Employee Details and implement Inheritance,Encapsulation,Inheritance, Polymorphism, Array concept, Threads Concept and JDBC Connectivity.
 * Created : 08-Jan-19 10:00 AM.
 * Modified : 31-Jan-19 4:30 PM.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

interface InterfaceForEmployeeManagementSystem{
    void getTotalEmployees();
}

class EmployeeManagementSystemUserInput
{
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Declaration of variables and Objects are done here
     */
    String numberOfEmployeeString;
    int numberOfEmployee;
    int realAge;
    String name;
    String id;
    String dob;
    String doj;
    String mobile;
    String mail;
    int dojYearInt;
    int dobYearInt;
	
    public static Connection getConnection() {
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }
}


public class EmployeeManagementSystem extends EmployeeManagementSystemUserInput implements InterfaceForEmployeeManagementSystem {
    // creating connection
    private Connection con = EmployeeManagementSystemUserInput.getConnection();

    //Encapsulation of Salary
    private String salary;


    //Interface Method is defined in here
    public void getTotalEmployees(){

        System.out.println("Enter the Number of Employees");
        try{
            numberOfEmployeeString = bufferedReader.readLine();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        int count=0;
        for(int i=0;i<numberOfEmployeeString.length();i++){
            if(Character.isDigit(numberOfEmployeeString.charAt(i)))
                count++;
        }
        if(count!=numberOfEmployeeString.length()){
            System.out.println("Enter only numbers between 1 to 5");
            getTotalEmployees();
        }
        if(!Character.isDigit(numberOfEmployeeString.charAt(0))){
            System.out.println("Enter only numbers between 1 to 5");
            getTotalEmployees();
        }
        numberOfEmployee = Integer.parseInt(numberOfEmployeeString);
        if(numberOfEmployee<=0||numberOfEmployee>5) {
            System.out.println("Enter only numbers between 1 to 5");
            getTotalEmployees();
        }
        try{
            PreparedStatement ps = null;
            for(int i=0;i<numberOfEmployee;i++){
                System.out.println("Enter the Details of Employee  "+(i+1));
                getEmployeeInformation();
                ps=con.prepareStatement("insert into employeemanagementsystem values(?,?,?,?,?,?,?)");
                ps.setInt(1,Integer.parseInt(id));
                ps.setString(2,name);
                ps.setString(3,dob);
                ps.setString(4,mobile);
                ps.setString(5,mail);
                ps.setString(6,doj);
                ps.setInt(7,Integer.parseInt(salary));
            }
            assert ps != null;
            ps.executeUpdate();
            System.out.println("******************************************************************************************************************************");
            System.out.println("*********************************************   Employee Details   ***********************************************************");
            System.out.println();
            System.out.println("Id\t\tName\t\tDateOfBirth\t\tMobileNumber\t\tMail-Id\t\tDateOfJoining");
            System.out.println("______________________________________________________________________________________________________________________________");

            ResultSet rs = ps.executeQuery("Select * from employeemanagementsystem");
            while(rs.next()){
                System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\t\t"+rs.getString(5)+"\t\t"+rs.getString(6));
            }
            con.close();
        }
        catch(Exception e) {
            System.out.print(e);
        }
    }
    private void getEmployeeInformation(){
        name = validateName();
        id = validateId();
        dob = validateDob();
        realAge = verifyAge(dob);
        System.out.println("Age is :"+ realAge);
        doj = validateDoj();
        mobile = validateMobile();
        mail = validateMail();
        salary = getSalary();
    }

    //Validating Name is done here

    private String validateName() {
        System.out.println("Enter the Name of the Employee :");
        try{
            name = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        if(name.length()<4){
            System.out.println("The Name is not valid Try again Valid format is XXXXXX Y ");
            name = validateName();
        }
        String regx = "^[a-zA-Z\\s]*$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        boolean nameFlag = matcher.find();
        int i;
        for(i=0;i<name.length()-2;i++)
        {
            if(name.charAt(i)==name.charAt(i+1) && name.charAt(i+1)==name.charAt(i+2))
                break;
        }
        if(!nameFlag || i != name.length()-2){
            System.out.println("The Name is not valid Try again Valid format is XXXXXX Y ");
            name = validateName();
        }
        return name;
    }

    //Validating Id is done here

    private String validateId(){
        System.out.println("Enter the ID of the Employee :");
        try{
            id = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        int count=0;
        for(int i=0;i<id.length();i++){
            if(Character.isDigit(id.charAt(i))){
                count++;
            }
        }
        if(count==id.length() && id.length()==4){
            int parsedId = Integer.parseInt(id);
            if(parsedId==0||parsedId>9999){
                System.out.println("The ID is not valid Try again Valid format is four non zero digits ");
                id = validateId();
            }
        }
        else{
            System.out.println("The ID is not valid Try again Valid format is four non zero digits ");
            id = validateId();
        }
        try {
            PreparedStatement ps = con.prepareStatement("select count(emp_id) from employeemanagementsystem where emp_id = (?)");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            count=0;
            if(rs.next()) {
                count = rs.getInt(1);
            }
            if(count > 0){
                System.out.println("The Id is already present in DataBase ");
                id = validateId();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return id;
    }

    //Validating Date of Birth here

    private String validateDob(){
        System.out.println("Enter the Date of Birth of the Employee :");
        try{
            dob = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        int charCheck = 0;
        for(int i=0;i<dob.length();i++){
            if(!(Character.isDigit(dob.charAt(i)) || dob.charAt(i)=='-')){
                charCheck=1;
                break;
            }
        }
        if(charCheck==1){
            System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY Enter integers only");
            dob = validateDob();
        }
        else{
            Pattern pattern = Pattern.compile("^[0-3]\\d-[01]\\d-\\d{4}$");
            Matcher matcher = pattern.matcher(dob);
            boolean dobFlag = matcher.find();
            if(!dobFlag){
                System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY");
                dob = validateDob();
            }
            verifyAge(dob);
        }
        return dob;
    }

    //Age is calculated here

    private int verifyAge(String dob){
        int length = dob.length();
        String dobYear = dob.substring(length-4);
        String dobMonth = dob.substring(length-7,length-5);
        dobYearInt = Integer.parseInt(dobYear);
        int dobMonthInt = Integer.parseInt(dobMonth);
        int approximateAge = 2019-dobYearInt;
        realAge = (2<=dobMonthInt)?approximateAge:(approximateAge+1);
        if(realAge<18||realAge>60){
            System.out.println("InValid Age for the Employee Should be Greater than 18 and less than 60 years old");
            validateDob();
        }
        return realAge;
    }

    //Validating Date of Joining here

    private String validateDoj(){
        System.out.println("Enter the Date of join of the Employee :");
        try{
            doj = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        int charCheck = 0;
        for(int i=0;i<doj.length();i++){
            if(!(Character.isDigit(doj.charAt(i)) || doj.charAt(i)=='-')){
                charCheck=1;
                break;
            }
        }
        if(charCheck==1){
            System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY Enter integers only");
            doj = validateDoj();
        }
        else{
            Pattern pattern = Pattern.compile("^[0-3]\\d-[01]\\d-\\d{4}$");
            Matcher matcher = pattern.matcher(doj);
            boolean dojFlag = matcher.find();
            if(!dojFlag){
                System.out.println("The Date of join is not valid Try again Valid format is DD-MM-YYYY");
                doj = validateDoj();
            }
            verifyDoj(doj);
        }
        return doj;
    }

    //Date of Join is Verified here by calculating experiance and made sure it does not allow invalid value.

    private void verifyDoj(String doj){
        int length = doj.length();
        String dojYear = doj.substring(length-4);
        String dojMonth = doj.substring(length-7,length-5);
        dojYearInt = Integer.parseInt(dojYear);
        int dojMonthInt = Integer.parseInt(dojMonth);
        int approximateExperience = 2019-dojYearInt;
        int realExperience = (2<=dojMonthInt)?approximateExperience:(approximateExperience+1);
        if((dojYearInt-dobYearInt)<18){
            System.out.println("InValid Date of Join please enter a valid date of join ");
            validateDoj();
        }
        else{
            if(realExperience<=0||realExperience>=62){
                System.out.println("InValid Date of Join please enter a valid date of join ");
                validateDoj();
            }
        }
    }

    //Mobile Number is verified here

    private String validateMobile(){
        System.out.println("Enter the Mobile Number of the Employee :");
        try{
            mobile = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("^(0/91)?[6-9][4-9][0-9]{8}$");
        Matcher matcher = pattern.matcher(mobile);
        boolean mobileFlag = (matcher.find() && matcher.group().equals(mobile));
        int count=0;
        for(int i=0;i<mobile.length();i++)
        {
            if(count>5)
                break;
            else{
                count=0;
                for(int j=0;j<mobile.length();j++){
                    if(mobile.charAt(i)==mobile.charAt(j))
                        count++;
                    if(count>5)
                        break;
                }
            }
        }
        if(count>5){
            System.out.println("The Phone number is not valid Try again Valid format is 91 9834567890 or 9834567890");
            mobile = validateMobile();
        }
        else{
            if(!mobileFlag){
                System.out.println("The Phone number is not valid Try again Valid format is 91 9834567890 or 9834567890");
                mobile = validateMobile();
            }
        }
        return mobile;
    }

    //E-Mail Id is verified here

    private String validateMail(){
        System.out.println("Enter the Mail Id of the Employee :");
        try{
            mail = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        int count=0,count1=0,count2=0;
        for(int i=0;i<mail.length();i++)
        {
            if(i==0 || i==mail.length()-1)
            {
                if(mail.charAt(i)=='@' || mail.charAt(i)=='.')
                    count2++;
            }
            if(mail.charAt(i)=='@')
                count++;
            if(mail.charAt(i)=='.')
                count1++;
        }
        if(count!=1 || count1<1  || count2!=0)
        {
            System.out.println("The Mail ID is not valid Try again Valid format is xxxxxxx@gmail.com");
            mail = validateMail();
        }
        return mail;
    }

    //Salary is Encapsulated here

    private String getSalary(){
        System.out.println("Enter the Salary of the Employee :");
        try{
            salary = bufferedReader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        int count=0;
        for(int i=0;i<salary.length();i++){
            if(Character.isDigit(salary.charAt(i)))
                count++;
        }
        if(count!=salary.length()){
            System.out.println("Enter an numerical amount only");
            salary=getSalary();
        }
        return salary;
    }

// In main I had created an instance for the Interface

    public static void main(String args[]){
        InterfaceForEmployeeManagementSystem interfaceForEmployeeManagementSystem = new EmployeeManagementSystem();
        interfaceForEmployeeManagementSystem.getTotalEmployees();
    }
}