/*
 * AuthorName : Rahul.T
 * Description : Program to validate Employee Details and implement Inheritance,Encapsulation,Inheritance, Polymorphism, Array concept, Threads Concept and JDBC Connectivity.
 * Created : 08-Jan-2019 10:00 AM.
 * Modified : 10-Feb-2019 10:30 PM.
 */

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Interface for getting details
interface InterfaceForEmployeeManagementSystem {
    void interactiveModelStart();
}

//Parent class for extending the variables and has socket connection method
class EmployeeManagementSystemUserInput {
    //Creating global object for Buffered Reader
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /*Default admin name and Password */
    String adminName = "admin";
    String adminPassword = "123";

    /* Variables are defined here */
    String adminLoginNameString;
    String adminLoginPasswordString;
    String employeeLoginIdString;
    String employeeLoginPasswordString;
    String employeePasswordTemp;
    String numberOfEmployeeString;
    String userOptionString;
    String continueOption;
    String idToUpdate;
    String idToDelete;
    String name;
    String id;
    String dob;
    String doj;
    String mobile;
    String mail;
    String designation;
    String password;
    int numberOfEmployee;
    int userOption;
    int count;
    int realAge;
    int dojYearInt;
    int dobYearInt;

    //socket connection method
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

//Main class which extends parent class and interface
public class EmployeeManagementSystem extends EmployeeManagementSystemUserInput implements InterfaceForEmployeeManagementSystem {

    /* This line is used for creating Connection */
    private Connection con = EmployeeManagementSystemUserInput.getConnection();

    /* Salary is Encapsulated in here */
    private String salary;

    /* This method is used to update Details of the Employee */
    private void updateEmployeeDetails() {
        System.out.println("Please Specify the Data to be modified ");
        System.out.println("1 = NAME");
        System.out.println("2 = DATE OF BIRTH");
        System.out.println("3 = MOBILE NUMBER");
        System.out.println("4 = EMAIL-ID");
        System.out.println("5 = PASSWORD");
        System.out.println("6 = EXIT");
        System.out.println();
        System.out.print("Enter your option :");
        try {
            userOptionString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = 0;
        for (int i = 0; i < userOptionString.length(); i++) {
            if (Character.isDigit(userOptionString.charAt(i)))
                count++;
        }
        if (count != userOptionString.length()) {
            System.out.println("Enter only numbers between 1 to 5");
            adminOptions();
        }
        userOption = Integer.parseInt(userOptionString);
        if (userOption <= 0 || userOption > 5) {
            System.out.println("Enter only numbers between 1 to 5");
            adminOptions();
        }
        switch (userOption) {
            case 1:
                System.out.println("Enter the new Name ");
                name = validateName();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_name=? where emp_emailid=?");
                    ps.setString(1, name);
                    ps.setString(2, employeeLoginIdString);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 2:
                System.out.println("Enter the new Date of Birth ");
                dob = validateDob();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_dob=? where emp_emailid=?");
                    ps.setString(1, dob);
                    ps.setString(2, employeeLoginIdString);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 3:
                System.out.println("Enter the new Mobile Number ");
                mobile = validateMobile();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_mobile=? where emp_emailid=?");
                    ps.setString(1, mobile);
                    ps.setString(2, employeeLoginIdString);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 4:
                System.out.println("Enter the new Email-ID ");
                mail = validateMail();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_email=? where emp_emailid=?");
                    ps.setString(1, mail);
                    ps.setString(2, employeeLoginIdString);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 5:
                System.out.println("Enter the new Password ");
                password = getPassword();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_password=? where emp_emailid=?");
                    ps.setString(1, password);
                    ps.setString(2, employeeLoginIdString);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 6:
                System.out.println("Exiting Update");
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
        }
    }

    /* This method is used to view details of the Employees in the Database by Employee. */
    private void viewDatabaseEntriesForEmployee() throws SQLException {
        System.out.println("=============================================   Employee Details   ================================================================================");
        System.out.println();
        System.out.println("Id\tName\t\tDateOfBirth\tMobileNumber\t\tMail-Id\t\tDateOfJoining\tDesignation");
        System.out.println("===================================================================================================================================================");
        PreparedStatement ps = con.prepareStatement("select * from employeemanagementsystem where emp_emailid = (?)");
        ps.setString(1, employeeLoginIdString);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t\t" + rs.getString(5) + "\t" + rs.getString(6)+ "\t\t" + rs.getString(7));
        }
        System.out.println("===================================================================================================================================================");
    }

    /* This method is executed when the Employee is logged in */
    private void employeeOptions() {
        System.out.println("=========EMPLOYEE=========");
        System.out.println("EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("Enter your choice: ");
        System.out.println("1 = VIEW RECORD");
        System.out.println("2 = UPDATE RECORD");
        System.out.println("3 = LOG OUT");
        System.out.println("4 = EXIT");
        System.out.println();
        System.out.print("Enter your option : ");
        try {
            userOptionString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = 0;
        for (int i = 0; i < userOptionString.length(); i++) {
            if (Character.isDigit(userOptionString.charAt(i)))
                count++;
        }
        if (count != userOptionString.length()) {
            System.out.println("Enter only numbers between 1 to 4");
            adminOptions();
        }
        userOption = Integer.parseInt(userOptionString);
        if (userOption <= 0 || userOption > 4) {
            System.out.println("Enter only numbers between 1 to 4");
            adminOptions();
        }
        switch (userOption) {
            case 1:
                try {
                    viewDatabaseEntriesForEmployee();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 2:
                try {
                    updateEmployeeDetails();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeOptions();
                break;
            case 3:
                System.out.println("Logging out....");
                interactiveModelStart();
                break;
            case 4:
                System.out.println("Exiting Program....");
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
                break;
        }
    }

    /* Getting Password of the Employee and Validation is done here */
    private String getPassword() {
        System.out.println("Enter the Password of the Employee. The Password should be 6 to 15 letters with a upper case" +
                " letter, a special character and a numerical value :");
        try {
            password = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int passLength = password.length();
        if (passLength < 6 || passLength > 15) {
            System.out.println(" The password did not satisfy all the conditions ");
            getPassword();
        } else if (password.equals("")) {
            System.out.println(" Enter a valid Password ");
            getPassword();
        } else {
            int upperCaseFlag = 0, specialCharFlag = 0, numericalValueFlag = 0, tempAscii;
            for (int i = 0; i < passLength; i++) {
                tempAscii = (int) password.charAt(i);
                if (tempAscii >= 65 && tempAscii < 91) {
                    upperCaseFlag++;
                } else if (tempAscii >= 48 && tempAscii < 58) {
                    numericalValueFlag++;
                } else if ((tempAscii >= 33 && tempAscii < 48) || (tempAscii >= 58 && tempAscii < 65) ||
                        (tempAscii >= 91 && tempAscii < 97) || (tempAscii >= 123 && tempAscii < 127)) {
                    specialCharFlag++;
                }
            }
            if (!(upperCaseFlag >= 1 && specialCharFlag >= 1 && numericalValueFlag >= 1)) {
                System.out.println(" The password did not satisfy all the conditions ");
                getPassword();
            }
        }
        return password;
    }

    /* Getting Salary of the Employee and Validation is done here */
    private String getSalary() {
        System.out.print("Enter the Salary of the Employee : ");
        try {
            salary = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (int i = 0; i < salary.length(); i++) {
            if (Character.isDigit(salary.charAt(i)))
                count++;
        }
        if (count != salary.length() || salary.length() == 0) {
            System.out.println("Enter an numerical amount only");
            getSalary();
        }
        if (Integer.parseInt(salary) < 1000) {
            System.out.println("The salary should be higher than 1000 Rs.");
            getSalary();
        }
        return salary;
    }

    /* Selecting Designation of the Employee and Validation is done here */
    private String validateDesignation(){
        System.out.println("Choose the designation : ");
        System.out.println("1 = Tester");
        System.out.println("2 = Software Developer");
        System.out.println("3 = Data Analyzer");
        System.out.println("4 = Intern");
        System.out.println("5 = UI/UX Developer");
        System.out.println();
        System.out.print("Enter your option :");
        try {
            designation = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = 0;
        for (int i = 0; i < designation.length(); i++) {
            if (Character.isDigit(designation.charAt(i)))
                count++;
        }
        if (count != designation.length()) {
            System.out.println("Enter only numbers between 1 to 5");
            validateDesignation();
        }
        userOption = Integer.parseInt(designation);
        if (userOption <= 0 || userOption > 5) {
            System.out.println("Enter only numbers between 1 to 5");
            validateDesignation();
        }
        switch(userOption){
            case 1 : return "Tester";
            case 2 : return "Software Developer";
            case 3 : return "Data Analyzer";
            case 4 : return "Intern";
            case 5 : return "UI/UX Developer";
        }
        return "";
    }
    /* Getting EMail-ID of the Employee and Validation is done here */
    private String validateMail() {
        System.out.print("Enter the Mail Id of the Employee : ");
        try {
            mail = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0, count1 = 0, count2 = 0;
        for (int i = 0; i < mail.length(); i++) {
            if (i == 0 || i == mail.length() - 1) {
                if (mail.charAt(i) == '@' || mail.charAt(i) == '.')
                    count2++;
            }
            if (mail.charAt(i) == '@')
                count++;
            if (mail.charAt(i) == '.')
                count1++;
        }
        if (count != 1 || count1 < 1 || count2 != 0) {
            System.out.println("The Mail ID is not valid Try again Valid format is xxxxxxx@gmail.com");
            mail = validateMail();
        }
        return mail;
    }

    /* Getting Mobile Number of the Employee and Validation is done here */
    private String validateMobile() {
        System.out.print("Enter the Mobile Number of the Employee : ");
        try {
            mobile = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("^[6-9][0-9]{9}$");
        Matcher matcher = pattern.matcher(mobile);
        boolean mobileFlag = (matcher.find() && matcher.group().equals(mobile));
        int count = 0;
        for (int i = 0; i < mobile.length(); i++) {
            if (count > 5)
                break;
            else {
                count = 0;
                for (int j = 0; j < mobile.length(); j++) {
                    if (mobile.charAt(i) == mobile.charAt(j))
                        count++;
                    if (count > 5)
                        break;
                }
            }
        }
        if (count > 5) {
            System.out.println("The Mobile number is not valid Try again Valid format is 91 9834567890 or 9834567890");
            validateMobile();
        } else if (mobile.length() != 10) {
            System.out.println("The Mobile number should be 10 digits");
            validateMobile();
        } else {
            if (!mobileFlag) {
                System.out.println("The Mobile number is not valid Try again Valid format is 91 9834567890 or 9834567890");
                validateMobile();
            }
        }
        return mobile;
    }

    /* Calculating Experiance of the Employee and Validation is done here */
    private void verifyDoj(String doj) {
        int length = doj.length();
        String dojYear = doj.substring(length - 4);
        String dojMonth = doj.substring(length - 7, length - 5);
        dojYearInt = Integer.parseInt(dojYear);
        int dojMonthInt = Integer.parseInt(dojMonth);
        int approximateExperience = 2019 - dojYearInt;
        int realExperience = (2 <= dojMonthInt) ? approximateExperience : (approximateExperience + 1);
        if ((dojYearInt - dobYearInt) < 18) {
            System.out.println("InValid Date of Join please enter a valid date of join ");
            validateDoj();
        } else {
            if (realExperience <= 0 || realExperience >= 62) {
                System.out.println("InValid Date of Join please enter a valid date of join ");
                validateDoj();
            }
        }
    }

    /* Getting Date of Joining of the Employee and Validation is done here */
    private String validateDoj() {
        System.out.print("Enter the Date of join of the Employee : ");
        try {
            doj = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int charCheck = 0;
        for (int i = 0; i < doj.length(); i++) {
            if (!(Character.isDigit(doj.charAt(i)) || doj.charAt(i) == '-')) {
                charCheck = 1;
                break;
            }
        }
        if (charCheck == 1) {
            System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY Enter integers only");
            validateDoj();
        } else {
            Pattern pattern = Pattern.compile("^[0-3]\\d-[01]\\d-\\d{4}$");
            Matcher matcher = pattern.matcher(doj);
            boolean dojFlag = matcher.find();
            if (!dojFlag) {
                System.out.println("The Date of join is not valid Try again Valid format is DD-MM-YYYY");
                validateDoj();
            }
            verifyDoj(doj);
        }
        return doj;
    }

    /* Calculating Age of the Employee and Validation is done here */
    private int verifyAge(String dob) {
        int length = dob.length();
        String dobYear = dob.substring(length - 4);
        String dobMonth = dob.substring(length - 7, length - 5);
        dobYearInt = Integer.parseInt(dobYear);
        int dobMonthInt = Integer.parseInt(dobMonth);
        int approximateAge = 2019 - dobYearInt;
        realAge = (2 <= dobMonthInt) ? approximateAge : (approximateAge + 1);
        if (realAge < 18 || realAge > 60) {
            System.out.println("InValid Age for the Employee Should be Greater than 18 and less than 60 years old");
            validateDob();
        }
        return realAge;
    }

    /* Getting Date of Birth of the Employee and Validation is done here */
    private String validateDob() {
        System.out.print("Enter the Date of Birth of the Employee : ");
        try {
            dob = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int charCheck = 0;
        for (int i = 0; i < dob.length(); i++) {
            if (!(Character.isDigit(dob.charAt(i)) || dob.charAt(i) == '-')) {
                charCheck = 1;
                break;
            }
        }
        if (charCheck == 1) {
            System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY Enter integers only");
            validateDob();
        } else {
            Pattern pattern = Pattern.compile("^[0-3]\\d-[01]\\d-\\d{4}$");
            Matcher matcher = pattern.matcher(dob);
            boolean dobFlag = matcher.find();
            if (!dobFlag) {
                System.out.println("The Date of Birth is not valid Try again Valid format is DD-MM-YYYY");
                validateDob();
            }
            verifyAge(dob);
        }
        return dob;
    }

    /* Getting ID of the Employee and Validation is done here */
    private String validateId() {
        System.out.print("Enter the ID of the Employee : ");
        try {
            id = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (int i = 0; i < id.length(); i++) {
            if (Character.isDigit(id.charAt(i))) {
                count++;
            }
        }
        if (count == id.length() && id.length() == 4) {
            int parsedId = Integer.parseInt(id);
            if (parsedId == 0 || parsedId > 9999) {
                System.out.println("The ID is not valid Try again Valid format is four digits ");
                validateId();
            }
        } else {
            System.out.println("The ID is not valid Try again Valid format is four digits ");
            validateId();
        }
        try {
            PreparedStatement ps = con.prepareStatement("select count(emp_id) from employeemanagementsystem where emp_id = (?)");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                System.out.println("The Id is already present in DataBase ");
                validateId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /* Getting Name of the Employee and Validation is done here */
    private String validateName() {
        System.out.print("Enter the Name of the Employee : ");
        try {
            name = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (name.length() < 4) {
            System.out.println("The Name is not valid Try again Valid format is XXXXXX Y ");
            validateName();
        }
        String regx = "^[a-zA-Z\\s]*$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        boolean nameFlag = matcher.find();
        int i;
        for (i = 0; i < name.length() - 2; i++) {
            if (name.charAt(i) == name.charAt(i + 1) && name.charAt(i + 1) == name.charAt(i + 2))
                break;
        }
        if (!nameFlag || i != name.length() - 2) {
            System.out.println("The Name is not valid Try again Valid format is XXXXXX Y ");
            validateName();
        }
        return name;
    }

    /*This method is used to update the query to database*/
    private void deleteId() throws SQLException {
        PreparedStatement ps;
        ps = con.prepareStatement("delete from employeemanagementsystem where emp_id = (?)");
        ps.setInt(1, Integer.parseInt(idToDelete));
        ps.executeUpdate();
        System.out.println("Data related to the Id is deleted");
        adminOptions();
    }

    /*This method is used to confirm the delete is to be done or not. */
    private void confirmDelete() {
        System.out.print("Enter your choice : ");
        try {
            continueOption = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (continueOption) {
            case "1":
                try {
                    deleteId();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                adminOptions();
                break;
            default:
                System.out.println("This not a Valid Option choose 1 or 2 ");
                confirmDelete();
                break;
        }
    }

    /* This method is used to delete Details of a Particular ID */
    private void deleteParticularData() {
        System.out.print("Enter an ID to Delete from Database : ");
        try {
            idToDelete = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idToDelete.equals("")) {
            System.out.println("This is not a valid ID. Retry...");
            deleteParticularData();
        } else if (!idToDelete.equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement("select count(emp_id) from employeemanagementsystem where emp_id = (?)");
                ps.setInt(1, Integer.parseInt(idToDelete));
                ResultSet rs = ps.executeQuery();
                count = 0;
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                if (count > 0) {
                    System.out.println("Do you want to Delete this Id = " + idToDelete);
                    System.out.println("1 = YES");
                    System.out.println("2 = NO");
                    System.out.println();
                    confirmDelete();
                } else if (count == 0) {
                    System.out.println("The ID is not present in the database please insert this ID before Deleting");
                    continueOrExit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            continueOrExit();
        }
    }

    /*This method is used to update the query to the database*/
    private void updateId() {
        System.out.println("Please Specify the Data to be modified ");
        System.out.println("1 = ID");
        System.out.println("2 = NAME");
        System.out.println("3 = DATE OF BIRTH");
        System.out.println("4 = DATE OF JOIN");
        System.out.println("5 = MOBILE NUMBER");
        System.out.println("6 = EMAIL-ID");
        System.out.println("7 = DESIGNATION");
        System.out.println("8 = SALARY");
        System.out.println("9 = PASSWORD");
        System.out.println("10 = EXIT");
        System.out.println();
        System.out.print("Enter your option :");
        try {
            userOptionString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = 0;
        for (int i = 0; i < userOptionString.length(); i++) {
            if (Character.isDigit(userOptionString.charAt(i)))
                count++;
        }
        if (count != userOptionString.length()) {
            System.out.println("Enter only numbers between 1 to 10");
            adminOptions();
        }
        userOption = Integer.parseInt(userOptionString);
        if (userOption <= 0 || userOption > 10) {
            System.out.println("Enter only numbers between 1 to 10");
            adminOptions();
        }
        switch (userOption) {
            case 1:
                System.out.println("Enter the new ID ");
                id = validateId();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_id=? where emp_id=?");
                    ps.setString(1, id);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 2:
                System.out.println("Enter the new Name ");
                name = validateName();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_name=? where emp_id=?");
                    ps.setString(1, name);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 3:
                System.out.println("Enter the new Date of Birth ");
                dob = validateDob();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_dob=? where emp_id=?");
                    ps.setString(1, dob);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 4:
                System.out.println("Enter the new Date of Birth ");
                doj = validateDoj();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_doj=? where emp_id=?");
                    ps.setString(1, doj);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 5:
                System.out.println("Enter the new Mobile Number ");
                mobile = validateMobile();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_mobile=? where emp_id=?");
                    ps.setString(1, mobile);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 6:
                System.out.println("Enter the new Email-ID ");
                mail = validateMail();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_email=? where emp_id=?");
                    ps.setString(1, mail);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 7:
                System.out.println("Enter the new Designation ");
                designation = validateDesignation();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_designation=? where emp_id=?");
                    ps.setString(1, designation);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 8:
                System.out.println("Enter the new Salary ");
                salary = getSalary();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_salary=? where emp_id=?");
                    ps.setString(1, salary);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 9:
                System.out.println("Enter the new Password ");
                password = getPassword();
                try {
                    PreparedStatement ps;
                    ps = con.prepareStatement("update employeemanagementsystem set emp_dob=? where emp_id=?");
                    ps.setString(1, password);
                    ps.setString(2, idToUpdate);
                    ps.executeUpdate();
                    System.out.println("Database updated Successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 10:
                System.out.println("Exiting Update");
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
        }
    }

    /*This method is used to confirm the update is to be done or not. */
    private void confirmUpdate() {

        System.out.print("Enter your choice : ");
        try {
            continueOption = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (continueOption) {
            case "1":
                try {
                    updateId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                adminOptions();
                break;
            default:
                System.out.println("This not a Valid Option choose 1 or 2 ");
                confirmUpdate();
                break;
        }
    }

    /* This method is used to update Details of specific ID */
    private void updateSpecificIdDetails() {
        System.out.print("Enter an ID to update Datas : ");
        try {
            idToUpdate = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idToUpdate.equals("")) {
            System.out.println("This is not a valid ID");
            updateSpecificIdDetails();
        } else {
            try {
                PreparedStatement ps = con.prepareStatement("select count(emp_id) from employeemanagementsystem where emp_id = (?)");
                ps.setInt(1, Integer.parseInt(idToUpdate));
                ResultSet rs = ps.executeQuery();
                count = 0;
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                if (count > 0) {
                    System.out.println("Do you want to update this Id =" + idToUpdate);
                    System.out.println("1 = YES");
                    System.out.println("2 = NO");
                    System.out.println();
                    confirmUpdate();
                } else if (count == 0) {
                    System.out.println("The ID is not present in the database please insert this ID before Updating");
                    continueOrExit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* This method is used to view details of the Employees in the Database by admin. */
    private void viewDatabaseEntries() throws SQLException {
        System.out.println("=============================================   Employee Details   ================================================================================");
        System.out.println();
        System.out.println("Id\tName\tDateOfBirth\tMobileNumber\t\tMail-Id\t\tDateOfJoining\t\tDesignation\tSalary\t\tPassword");
        System.out.println("===================================================================================================================================================");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from employeemanagementsystem");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t" + rs.getString(7) + "\t" + rs.getString(8)+ "\t\t" + rs.getString(9));
        }
        System.out.println("===================================================================================================================================================");
    }

    /* Continue or Exit method is used to navigate user for returning to menu or quitting the application */
    private void continueOrExit() {
        System.out.println("=========================");
        System.out.println("Do you want to continue ?");
        System.out.println("1 = Go to Main Menu");
        System.out.println("2 = Exit from Application");
        System.out.println("=========================");
        System.out.println();
        System.out.print("Enter your option : ");
        try {
            continueOption = bufferedReader.readLine(); //Number of employees is entered here
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (continueOption) {
            case "1":
                System.out.println();
                adminOptions();
                break;
            case "2":
                System.out.println("Exiting Program....");
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            default:
                System.out.println("Enter option 1 or 2");
                continueOrExit();
                break;
        }
    }

    /* This methood is for getting an Employee Detail */
    private void getEmployeeInformation() {
        id = validateId();
        name = validateName();
        dob = validateDob();
        realAge = verifyAge(dob);
        System.out.println("Age is :" + realAge);
        doj = validateDoj();
        mobile = validateMobile();
        mail = validateMail();
        designation = validateDesignation();
        salary = getSalary();
        password = getPassword();
    }

    /* This method is used for getting Employee Details */
    private void getDatasFromUser() throws SQLException {
        PreparedStatement ps;
        for (int i = 0; i < numberOfEmployee; i++) {
            System.out.println("Enter the Details of Employee  " + (i + 1));
            getEmployeeInformation();
            ps = con.prepareStatement("insert into employeemanagementsystem values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, dob);
            ps.setString(4, mobile);
            ps.setString(5, mail);
            ps.setString(6, doj);
            ps.setString(7, designation);
            ps.setString(8, salary);
            ps.setString(9, password);
            ps.executeUpdate();
        }
    }

    /* This method is used for getting total number of Employee. Interface Method is defined in here */
    private void getTotalEmployees() {

        System.out.println("Enter the Number of Employees");
        try {
            numberOfEmployeeString = bufferedReader.readLine(); //Number of employees is entered here
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = 0;
        for (int i = 0; i < numberOfEmployeeString.length(); i++) {
            if (Character.isDigit(numberOfEmployeeString.charAt(i)))
                count++;
        }
        if (count != numberOfEmployeeString.length()) {
            System.out.println("Enter only numbers between 1 to 5");
            getTotalEmployees();
        }
        numberOfEmployee = Integer.parseInt(numberOfEmployeeString);
        if (numberOfEmployee <= 0 || numberOfEmployee > 5) {
            System.out.println("Enter only numbers between 1 to 5");
            getTotalEmployees();
        } else {
            try {
                getDatasFromUser();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /* This method is executed when the admin is logged in */
    private void adminOptions() {
        System.out.println("==========ADMIN===========");
        System.out.println("EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("Enter your choice: ");
        System.out.println("1 = INSERT RECORD");
        System.out.println("2 = VIEW RECORDS");
        System.out.println("3 = UPDATE RECORD");
        System.out.println("4 = DELETE RECORD");
        System.out.println("5 = LOG OUT");
        System.out.println("6 = EXIT");
        System.out.println("=========================");
        System.out.println();
        System.out.print("Enter your option : ");
        try {
            userOptionString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = 0;
        for (int i = 0; i < userOptionString.length(); i++) {
            if (Character.isDigit(userOptionString.charAt(i)))
                count++;
        }
        if (count != userOptionString.length()) {
            System.out.println("Enter only numbers between 1 to 6");
            adminOptions();
        }
        userOption = Integer.parseInt(userOptionString);
        if (userOption <= 0 || userOption > 6) {
            System.out.println("Enter only numbers between 1 to 6");
            adminOptions();
        }
        switch (userOption) {
            case 1:
                getTotalEmployees();
                System.out.println("Database Updated.");
                continueOrExit();
                break;
            case 2:
                try {
                    viewDatabaseEntries();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 3:
                try {
                    updateSpecificIdDetails();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continueOrExit();
                break;
            case 4:
                deleteParticularData();
                continueOrExit();
                break;
            case 5:
                System.out.println("Logging out....");
                interactiveModelStart();
                break;
            case 6:
                System.out.println("Exiting Program....");
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.exit(0);
        }
    }

    /* Here the employee name and Password is verified*/
    private void validateEmployee() {
        System.out.println("Enter Empolyee EMail-id");
        try {
            employeeLoginIdString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (employeeLoginIdString.equals("")) {
            System.out.println("This is not a valid EMail-id");
            validateEmployee();
        } else {
            try {
                PreparedStatement ps = con.prepareStatement("select count(emp_emailid) from employeemanagementsystem where emp_emailid = (?)");
                ps.setString(1, employeeLoginIdString);
                ResultSet rs = ps.executeQuery();
                count = 0;
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                if (count == 0) {
                    System.out.println("This EMail is Incorrecct or it does not present in the Database. Retry...");
                    validateEmployee();
                } else {
                    System.out.println("Enter Empolyee Password");
                    try {
                        employeeLoginPasswordString = bufferedReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (employeeLoginPasswordString.equals("")) {
                        System.out.println("This is not a valid ID. Enter Employee ID and PASSWORD");
                        validateEmployee();
                    } else {
                        try {
                            ps = con.prepareStatement("select emp_password from employeemanagementsystem where emp_emailid = (?)");
                            ps.setString(1, employeeLoginIdString);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                employeePasswordTemp = rs.getString(1);
                            }
                            if (employeePasswordTemp.equals(employeeLoginPasswordString)) {
                                employeeOptions();
                            } else {
                                System.out.println("The Entered password is not correct. Enter the employee ID and employee PASSWORD");
                                validateEmployee();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* Here the admin name and Password is verified */
    private void validateAdmin() {
        System.out.println("Enter admin NAME");
        try {
            adminLoginNameString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!adminLoginNameString.equals(adminName)) {
            System.out.println("The Admin Name is Incorrect");
            validateAdmin();
        } else if (adminLoginNameString.equals("")) {
            System.out.println("The Admin Name is Incorrect");
            validateAdmin();
        } else {
            System.out.println("Enter admin PASSWORD");
            try {
                adminLoginPasswordString = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!adminLoginPasswordString.equals(adminPassword)) {
                System.out.println("The Admin Password is Incorrect Enter Admin NAME and PASSWORD");
                validateAdmin();
            } else if (adminLoginPasswordString.equals("")) {
                System.out.println("The Admin Password is Incorrect Enter Admin NAME and PASSWORD");
                validateAdmin();
            } else {
                adminOptions();
            }
        }
    }

    /* This method is used to get user option on login*/
    private void getUserOption() {
        System.out.print("Enter your choice : ");
        try {
            userOptionString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (userOptionString) {
            case "1":
                validateAdmin();
                break;
            case "2":
                validateEmployee();
                break;
            case "3":
                System.out.println("Exiting Application....");
                System.exit(0);
            default:
                System.out.println("This not a Valid Option choose 1 or 2 ");
                getUserOption();
                break;
        }
    }

    /*This is the called Function from main*/
    public void interactiveModelStart() {
        System.out.println("=========WELCOME==========");
        System.out.println("EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("1 = ADMIN LOGIN");
        System.out.println("2 = EMPLOYEE LOGIN");
        System.out.println("3 = EXIT APPLICATION");
        System.out.println("==========================");
        getUserOption();
    }

    /* This is the main method in this class*/
    public static void main(String args[]) {
        InterfaceForEmployeeManagementSystem interfaceForEmployeeManagementSystem = new EmployeeManagementSystem();
        interfaceForEmployeeManagementSystem.interactiveModelStart();
    }
}