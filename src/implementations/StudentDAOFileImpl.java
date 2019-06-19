package implementations;

import com.google.gson.Gson;
import entities.Student;
import interfaces.StudentDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import singletons.FileConnection;

public class StudentDAOFileImpl implements StudentDAO {

    private String filePath = FileConnection.getFilePath();

    @Override
    public Student create(Student student) {

        Gson gson = new Gson();
        String studentJSON = gson.toJson(student); // Student JSON element
        //System.out.println(studentJSON);
        if (retrieve(student.getId()) == null) {
            try {
                RandomAccessFile output = new RandomAccessFile(filePath, "rw");
                long fileLength = output.length();
                output.seek(fileLength);

                output.writeBytes(studentJSON + "\n");
                System.out.println("Created.");

            } catch (FileNotFoundException ex) {
                System.err.println("File Not Found!!!");
            } catch (IOException ex) {
                System.err.println("Some IO Exception Occured!!!");
            }
        }

        return retrieve(student.getId());
    }

    @Override
    public Student retrieve(String studentId) {

        Gson gson = new Gson();
        Student student = null;

        try {
            RandomAccessFile input = new RandomAccessFile(filePath, "r");
            String studentJSON;
            while ((studentJSON = input.readLine()) != null) {
                if (gson.fromJson(studentJSON, Student.class).getId().equals(studentId)) {
                    student = gson.fromJson(studentJSON, Student.class);
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File Not Found!!!");
        } catch (IOException ex) {
            System.err.println("Some IO Exception Occured!!!");
        }
        return student;
    }

    @Override
    public List<Student> retrieve() {
        List<Student> studentList = new ArrayList<>();

        Gson gson = new Gson();
        Student student;

        try {
            RandomAccessFile input = new RandomAccessFile(filePath, "r");
            String studentJSON;
            while ((studentJSON = input.readLine()) != null) {
                student = gson.fromJson(studentJSON, Student.class);
                studentList.add(student);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File Not Found!!!");
        } catch (IOException ex) {
            System.err.println("Some IO Exception Occured!!!");
        }

        return studentList;
    }

    @Override
    public List<Student> retrieve(Predicate<Student> predicate) {
        return retrieve().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Student update(String studentId, Student student) {
        List<Student> retrievedStudentList = retrieve();
        Gson gson = new Gson();

        try {
            RandomAccessFile output = new RandomAccessFile(filePath, "rw");
            deleteAll(); // clearing the file
            
            for (Student st : retrievedStudentList) {
                if (st.getId().equals(studentId)) 
                    st.setName(student.getName());
                
                String studentJSON = gson.toJson(st);
                output.writeBytes(studentJSON + "\n");
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File Not Found!!!");
        } catch (IOException ex) {
            System.err.println("Some IO Exception Occured!!!");
        }
        
        return retrieve(studentId);
    }

    @Override
    public boolean delete(String studentId) {
        List<Student> retrievedStudentList = retrieve();
        Gson gson = new Gson();

        try {
            RandomAccessFile output = new RandomAccessFile(filePath, "rw");
            deleteAll(); // clearing the file
            
            for (Student st : retrievedStudentList) {
                if (!st.getId().equals(studentId)){
                    String studentJSON = gson.toJson(st);
                    output.writeBytes(studentJSON + "\n");
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.err.println("File Not Found!!!");
        } catch (IOException ex) {
            System.err.println("Some IO Exception Occured!!!");
        }
        return false;
    }

    @Override
    public void deleteAll() {
        try{
            PrintWriter pw = new PrintWriter(filePath);
        } catch (FileNotFoundException ex) {
            System.err.println("File Not found!!!");
        }
    }

}
