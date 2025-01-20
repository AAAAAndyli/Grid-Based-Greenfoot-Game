import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import greenfoot.*;

/**
 * Write a description of class SaveFile here.
 * 
 * @author Darren T.
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
        loadFile("saveFile/saveFile.csv");
    }
    
    public static void loadFile(String file){
        info.clear();
        try{
            s = new Scanner(new File(file));
            while(s.hasNext()){
                info.add(s.nextLine());
            }
            s.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }
    
    public static void setInfo(String infoType, String set){
        int index = 0;
        
        for(String s : info){
            tokenizer = new StringTokenizer(s, ",");
            currentToken = tokenizer.nextToken();
            
            if(currentToken.equals(infoType)){
                String newInfo = currentToken + "," + set; 
                info.set(index, newInfo);
            }
            index++;
        }       
        
        save();
    }
    
    public static void setInfo(String infoType, int set){
        setInfo(infoType, Integer.toString(set));
    }
    
    public static String getString(String keyBind){
        
        for(String s : info){
            tokenizer = new StringTokenizer(s, ",");
            currentToken = tokenizer.nextToken();
            
            if(currentToken.equals(keyBind)){
                return tokenizer.nextToken();
            }
        }
        //keybind not found
        System.out.println("Keybind not found: " + keyBind);
        return null;
    }
    
    public static void updateVolume(GreenfootSound reference, String type){
        reference.setVolume((int)(reference.getVolume() * SaveFile.getInt(type) / 100.0));
    }
    
    public static void updateVolume(GreenfootSound[] references, String type){
        for(GreenfootSound s : references){
            s.setVolume((int)(s.getVolume() * SaveFile.getInt(type) / 100.0));   
        }
    }
    
    public static int getInt(String keyBind){
        
        for(String s : info){
            tokenizer = new StringTokenizer(s, ",");
            currentToken = tokenizer.nextToken();
            
            if(currentToken.equals(keyBind)){
                return Integer.parseInt(tokenizer.nextToken());
            }
        }
        //keybind not found
        System.out.println("Keybind not found: " + keyBind);
        return -1;
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
    
    public static ArrayList<String> getInfo(){
        return info;
    }
}
