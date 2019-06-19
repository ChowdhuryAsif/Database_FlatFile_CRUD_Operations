package implementations;

import entities.Student;
import interfaces.StudentDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import singletons.DBConnection;

/**
 *
 * @author chowd
 */
public class StudentDAOMySQLImpl implements StudentDAO {

    private Connection connection;
    private PreparedStatement prepareCreateStudentStatement;
    private PreparedStatement prepareRetrieveByIdStatement;
    private PreparedStatement prepareRetrieveAllStudentStatement;
    private PreparedStatement prepareDeleteStudentStatement;
    private PreparedStatement prepareDeleteAllStudentStatement;

    public StudentDAOMySQLImpl() {
        connection = DBConnection.getConnection();

        try {
            prepareCreateStudentStatement = connection.prepareStatement("insert into student values(?,?)");
            prepareRetrieveByIdStatement = connection.prepareStatement("select studentName from student where studentId = ?");
            prepareRetrieveAllStudentStatement = connection.prepareStatement("select * from student");
            prepareDeleteStudentStatement = connection.prepareStatement("delete from student where studentId = ?");
            prepareDeleteAllStudentStatement = connection.prepareStatement("delete from student");
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOMySQLImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Student create(Student student) {
        Student retrieveStudent = null;
        try {
            prepareCreateStudentStatement.setString(1, student.getId());
            prepareCreateStudentStatement.setString(2, student.getName());

            prepareCreateStudentStatement.executeUpdate();

            retrieveStudent = retrieve(student.getId());
        } catch (SQLException ex) {
            System.err.println("You can't add duplicate copies...");
        }
        return retrieveStudent;
    }

    @Override
    public Student retrieve(String studentId) {
        Student student = null;

        try {
            prepareRetrieveByIdStatement.setString(1, studentId);

            ResultSet resultSet = prepareRetrieveByIdStatement.executeQuery();

            if (resultSet.next()) {
                String studentName = resultSet.getString("studentName");
                student = new Student(studentId, studentName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOMySQLImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(student == null)
                System.out.println("There is no such Student to retrieve.");
        return student;
    }

    @Override
    public List<Student> retrieve() {
        List<Student> studentList = new ArrayList<>();

        try {
            ResultSet resultSet = prepareRetrieveAllStudentStatement.executeQuery();
            while (resultSet.next()) {
                String studentId = resultSet.getString("studentId");
                String studentName = resultSet.getString("studentName");

                studentList.add(new Student(studentId, studentName));
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOMySQLImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
    }

    @Override
    public List<Student> retrieve(Predicate<Student> predicate) {
        return retrieve().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Student update(String studentId, Student student) {
        Student retrieveStudent = retrieve(studentId);
        
        if (retrieveStudent == null) {
            System.out.println("There is no such Student to update.");
            return null;
        } else {
            if (retrieveStudent.getId().equals(student.getId())) {
                delete(studentId);
                Student createdStudent = create(student);
                System.out.println("Updated.");
                return createdStudent;
            } else {
                System.out.println("Can't Update, Please Provide the right information.");
                return null;
            }
        }
    }

    @Override
    public boolean delete(String studentId) {
        try {
            prepareDeleteStudentStatement.setString(1, studentId);
            if (prepareDeleteStudentStatement.executeUpdate() == 1) {
                return true;
            } else {
                System.out.printf("There is no Student whose id is %s\n", studentId);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOMySQLImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void deleteAll() {
        try{
            prepareDeleteAllStudentStatement.executeUpdate();
            
            System.out.println("All records are deleted.");
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOMySQLImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
