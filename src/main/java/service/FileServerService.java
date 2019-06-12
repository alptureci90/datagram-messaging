package service;

import java.io.IOException;
import java.nio.file.*;
import java.net.*;

public class FileServerService {

    private String inputString;
    private String path;
    private String[] inputArray;
    private DatagramSocket sc = null;
    InetAddress clientAddr = null;
    int clientPort = 0;

    public FileServerService(String input, DatagramSocket sock, InetAddress Addr, int cliPort){
        inputString = input;
        sc = sock;
        clientAddr = Addr;
        clientPort = cliPort;

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
            result = executeDelete();
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
        } else {
            return "Please provide valid FileNAme like asd.txt or sd.json";
        }

        return "";
    }
    private String executeRead() {

        String absFilePath = path + '/' +inputArray[1];

       // File fp = new File(path+newFileName);
        //BufferedReader br = new BufferedReader(new FileReader(fp));
        //byte[] buffer = new byte[(int)fp.length()];
        byte[] buffer = Files.readAllBytes(Paths.get(absFilePath));

        sc.send(new DatagramPacket(buffer, buffer.length, clientAddr, clientPort));
        return "";
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
