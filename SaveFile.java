import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Write a description of class SaveFile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SaveFile  
{
    FileWriter out;
    PrintWriter output;
    ArrayList<String> info;
    
    /**
     * Constructor for objects of class SaveFile
     */
    public SaveFile(String[] keyBinds)
    {
        
    }
    
    public void updateKeyBind(String[][] keyBind){
        try{
            out = new FileWriter("saveFile.csv");
            output = new PrintWriter(out);
            
        }
        catch (IOException e){
            System.out.println("Error " + e);
        }
    }
}
