package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    public static boolean writeToFile(String fullFilePath, String fileContent){
        // fullFilePath should be like
        // "c:/temp/samplefile1.txt"

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullFilePath));
            writer.write(fileContent);
            writer.close();
        } catch (IOException e){
            return false;
        }
      return true;
    }

}
