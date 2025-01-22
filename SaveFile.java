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
 * A class that is responsible for getting or saving data to 
 * the csv save file
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class SaveFile  
{
    private static FileWriter out;
    private static PrintWriter output;
    private static ArrayList<String> info = new ArrayList<String>();
    //boolean so that some code is run only once
    private static boolean ranOnce = false;
    private static Scanner s;
    private static StringTokenizer tokenizer;
    private static String currentToken;
    
    public static void loadFile(){
        loadFile("saveFile/saveFile.csv");
    }
    
    /**
     * Method to load all the info from a file and 
     * put it into the info ArrayList for use
     */
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
    
    /**
     * Method to replace data in info with a new string
     * based on the first token of each line
     */
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
    
    /**
     * Method to replace data in info with a new int
     * based on the first token of each line
     */
    public static void setInfo(String infoType, int set){
        setInfo(infoType, Integer.toString(set));
    }
    
    /**
     * Method to get String data in info with a 
     * based on the first token of each line
     */
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
    
    /**
     * Method to update the volume of a single sound
     * based on what type it is (effect or music)
     */
    public static void updateVolume(GreenfootSound reference, String type){
        reference.setVolume((int)(reference.getVolume() * SaveFile.getInt(type) / 100.0));
    }
    
    /**
     * Method to update the volume of a multiple sounds
     * based on what type it is (effect or music)
     */
    public static void updateVolume(GreenfootSound[] references, String type){
        for(GreenfootSound s : references){
            s.setVolume((int)(s.getVolume() * SaveFile.getInt(type) / 100.0));   
        }
    }
    
    /**
     * Method to get int data in info with a 
     * based on the first token of each line
     */
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
    
    /**
     * Method to save data to the csv file
     */
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
    
    /**
     * Method to get the info arraylist
     */
    public static ArrayList<String> getInfo(){
        return info;
    }
}
