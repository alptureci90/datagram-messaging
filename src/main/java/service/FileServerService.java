package service;

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

        if (inputArray[0].toLowerCase().equals("write")){
            result = executeWrite(inputArray);
        }
        else if (inputArray[0].toLowerCase().equals("read")){
            //result = executeRead(inputs);
        }
        else if (inputArray[0].toLowerCase().equals("delete")){
            //result = executeDelete(inputs);
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

}
