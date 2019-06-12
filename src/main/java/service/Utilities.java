package service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilities {

    private static final String DEFAULT_FILESERER_PATH_LINUX = "/home/fileServer";
    private static final String DEFAULT_FILESERER_PATH_MAC = "/Users/Shared/fileServer";
    // WINDOWS need to be fixed. I dont remember the path for Windows.
    private static final String DEFAULT_FILESERER_PATH_WIN = "C:/Users/Shared/fileServer";

    private static String getOSType(){
        String OS = System.getProperty("os.name").toLowerCase();
        return OS;
    }


    public static boolean checkCreateFileServerFolder(){
        String OS = getOSType();
        Path path = null;
        String path_addr = "";

        if (OS.indexOf("mac") >= 0){
            path = Paths.get(DEFAULT_FILESERER_PATH_MAC);
            path_addr = DEFAULT_FILESERER_PATH_MAC;
        }
        else if (OS.indexOf("win") >= 0){
            path = Paths.get(DEFAULT_FILESERER_PATH_WIN);
            path_addr = DEFAULT_FILESERER_PATH_WIN;
        }
        else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
            path = Paths.get(DEFAULT_FILESERER_PATH_LINUX);
            path_addr = DEFAULT_FILESERER_PATH_LINUX;
        }
        else {
            return false;
        }

        try{
            if (Files.exists(path)){
                return true;
            }
            else {
                createFolder(path_addr);
            }
        } catch (Exception e){

        }
        return true;
    }


    //To create a new
    private static boolean createFolder(String path_addr){
        return new File(path_addr).mkdir();
    }

    //Return Path
    public static String getPathAddress(){
        String OS = getOSType();
        String path = "";
        if (OS.indexOf("mac") >= 0){
            path = DEFAULT_FILESERER_PATH_MAC;
        }
        else if (OS.indexOf("win") >= 0){
            path = DEFAULT_FILESERER_PATH_WIN;
        }
        else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
            path = DEFAULT_FILESERER_PATH_LINUX;
        }
        else {
            path = "";
        }
        return path;
    }

    public static boolean isValidFileName(String fileName){
        //For now just assume file name is valid.
        return true;
    }


}
