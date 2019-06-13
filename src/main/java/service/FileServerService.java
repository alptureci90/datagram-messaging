package service;

import java.io.IOException;
import java.nio.file.*;
import java.net.*;
import java.util.List;

public class FileServerService {

    private String inputString;
    private String path;
    private String[] inputArray;

    public FileServerService(String input){
        inputString = input;

        // Do initial Checks
        Utilities.checkCreateFileServerFolder();

        // Declare path to File Folder
        path = Utilities.getPathAddress();

        // Parse the input for future business Logic
        parseInputString(inputString);

    }

    public String executeTheCommand(){
        String result ="";
        String opCode = inputArray[0].toLowerCase();

        if (opCode.equals("write")){
            result = executeWrite(inputArray);
        }
        else if (opCode.toLowerCase().equals("read")){
            result = executeRead();
        }
        else if (opCode.toLowerCase().equals("delete")){
            if(executeDelete()){
                return "Delete success";
            } else {
                return "Delete unsuccessful!";
            }
        }
        else {
            result = "First received command should be write/read/delete \n please provide proper syntax!";
        }

        return result;
    }

    private void parseInputString(String input){

        this.inputArray = input.split("\\s");

    }

    private String executeWrite(String[] inputs){

        String newFileName = inputs[1];

        // What is the content didn't seperated with \s characters. !!
        StringBuilder sb = new StringBuilder();

        if(Utilities.isValidFileName(newFileName)){
            for (int i = 2; i < inputs.length; i++){
                // white space is the default for now. this can be enhanced in the future.
                sb.append(inputs[i]).append(" ");
            }

            //Write to the file
            WriteFile.writeToFile(path + "/" + newFileName, sb.toString());
            return "Operation 'write' has succeeded";
        } else {
            return "Please provide valid FileNAme like asd.txt or sd.json";
        }
    }
    private String executeRead() {

        String absFilePath = path + '/' +inputArray[1];

       try {
           List<String> readLines = Files.readAllLines(Paths.get(absFilePath));
           StringBuilder sb = new StringBuilder();
           for (String s : readLines){
               sb.append(s + "\n");
           }
           return sb.toString();
       }
       catch (IOException e){
           return "Couldn't read files";
       }
    }

    private boolean executeDelete() {
        String absFilePath = path + '/' +inputArray[1];
        try{
            Files.deleteIfExists(Paths.get(absFilePath));
        }
        catch (NoSuchFileException e) {
            System.out.println("No such file exists !");
            return false;
        }
        catch(IOException e){
            System.out.println("Permission error");
            return false;
        }
        return true;
    }
}
