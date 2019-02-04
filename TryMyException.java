class TryMyException{

   static void validate(int Salary)throws InvalidSalaryException{
     if(Salary>10000)
      throw new InvalidSalaryException("Salary should be less than 10,000");
     else
      System.out.println("Salary - "+Salary);
   }

   public static void main(String args[]){
      try{  
      validate(10100);
      }
      catch(InvalidSalaryException e){
     System.out.println("Exception occured: "+e);
       }
  }
}
