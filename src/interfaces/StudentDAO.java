package interfaces;


import entities.Student;
import java.util.List;
import java.util.function.Predicate;


public interface StudentDAO {
    // Insert or create a new instance of student
    public Student create(Student student);
    
    // Retrieve Student
    public Student retrieve(String studentId);
    
    // Retrieve All Student
    public List<Student> retrieve();
    
    // Retrieve filtered Student
    public List<Student> retrieve(Predicate<Student> predicate);
    
    // Update Student
    public Student update(String studentId, Student student);
    
    // Delete particular Student
    public boolean delete(String studentId);
    
    // Delete All Student
    public void deleteAll();
    
    // Count all the copies
    // Default Method for StudentDAO
    default int count(){
        return retrieve().size();
    }

}
