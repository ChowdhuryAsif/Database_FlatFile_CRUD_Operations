
package db.connection.demo;

import entities.Student;
import implementations.StudentDAOFileImpl;
import implementations.StudentDAOMySQLImpl;
import interfaces.StudentDAO;
import singletons.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnectionDemo {

    public DBConnectionDemo(){
        
        studentOperations();
        
    }
    
    public static void main(String[] args) {
        new DBConnectionDemo();
    }

    private void studentOperations() {
        //StudentDAO studentDAO = new StudentDAOMySQLImpl();
        //StudentDAO studentDAO = new StudentDAOFileImpl();
        
        // Creating Student
//        Student createdStudent = studentDAO.create(new Student("2017100000021", "Ejaz Zaman"));
//        if(createdStudent != null)
//            System.out.printf("Created Student: %s\n", createdStudent.toString());
        
        //Retrieve Student By ID
//        Student retrievedStudent = studentDAO.retrieve("2017100000021");
//        if(retrievedStudent != null)
//            System.out.println("Retrieved Student: " + retrievedStudent.toString());
        
        // Retrieve All Student as List
//        System.out.println("Student List:");
//        studentDAO.retrieve().stream().forEach(System.out::println);
        
        // Retrieve Students by some Predicate
//        System.out.println("Student List by Predicate:");
//        studentDAO.retrieve(predicate -> predicate.getId().startsWith("20171")).stream().forEach(System.out::println);
        
        // Student Count
//        System.out.printf("There are Total %d Students.\n", studentDAO.count());
        
        // Student Delete
//        if(studentDAO.delete("2016100000001"))
//            System.out.println("Deleted.");
       
        // Student Update
//        Student updatedStudent = studentDAO.update("2016100000001", new Student("2016100000001", "Abdullah Al Jamil"));
//        if(updatedStudent != null)
//            System.out.println("Updated Student: " + updatedStudent.toString());

        // Delete All Student Records
//        studentDAO.deleteAll();

    }
    
}
