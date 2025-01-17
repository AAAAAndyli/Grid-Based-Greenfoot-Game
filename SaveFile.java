import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;

/**
 * Write a description of class SaveFile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SaveFile  
{
    private static FileWriter out;
    private static PrintWriter output;
    private static ArrayList<String> info = new ArrayList<String>();
    private static boolean ranOnce = false;
    private static Scanner s;
    private static StringTokenizer tokenizer;
    private static String currentToken;
    
    /**
     * Constructor for objects of class SaveFile
     */
    public SaveFile()
    {
        
    }
    
    public static void loadFile(){
        info.clear();
        try{
            s = new Scanner(new File("saveFile/saveFile.csv"));
            while(s.hasNext()){
                info.add(s.nextLine());
            }
            s.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }
    
    public static void setKeyBind(String keyBind, String key){
        int index = 0;
        
        for(String s : info){
            tokenizer = new StringTokenizer(s, ",");
            currentToken = tokenizer.nextToken();
            
            if(currentToken.equals(keyBind)){
                String newBind = currentToken + "," + key; 
                info.set(index, newBind);
            }
            index++;
        }       
        
        save();
    }
    
    public static String getKey(String keyBind){
        for(String s : info){
            tokenizer = new StringTokenizer(s, ",");
            currentToken = tokenizer.nextToken();
            
            if(currentToken.equals(keyBind)){
                return tokenizer.nextToken();
            }
        }
        //keybind not found
        return null;
    }
    
    public static void save(){
        try{
            FileWriter out = new FileWriter("saveFile/saveFile.csv");
            PrintWriter output = new PrintWriter(out);
            
            for(String s : info){
                output.println(s);
            }
            
            output.close();
        }
        catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
}
