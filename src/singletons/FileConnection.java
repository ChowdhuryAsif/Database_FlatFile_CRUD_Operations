
package singletons;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileConnection {
    private static String fileName = "student.txt";
    private static String filePath = "src" + File.separator + "databases" + File.separator + fileName; 
    private FileConnection instance = new FileConnection();
    
    private FileConnection(){
        File file = new File(filePath);
        try{
            if(file.createNewFile())
                System.out.println("Student text file created.");
        } catch (IOException ex) {
            System.out.println("File exist or could not created.");
        }
    }
    
    public static String getFilePath(){
        return filePath;
    }
}
