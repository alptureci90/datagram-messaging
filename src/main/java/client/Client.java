package client;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;
import java.net.*;

public class Client
{
    /**
     *  Setup default port.
     *  Initialization of new Datagram instance variable.
     */
    private int defaultPort = 1255;
    private DatagramSocket clientSocket = null;

    /**
     *  Pattern object which define connect and exit command pattern.
     */
    private Pattern connectCommandPattern = Pattern.compile("^(CONNECT\\W*(?<ServerAddress>(\\d{1,3}[.]){3}\\d{1,3}\\b|localhost))$", Pattern.CASE_INSENSITIVE);
    private Pattern exitCommandPattern = Pattern.compile("^\\W*(EXIT)", Pattern.CASE_INSENSITIVE);

    /**
     * Starts endless loop of waiting and processing command from console input.
     */
    public void ReadCommands()
    {
        Scanner commandScanner = new Scanner(System.in);
        while (true) {
            String stringCommand = commandScanner.nextLine();
            ParseAndExecuteCommand(stringCommand);
        }
    }

    /**
     * Creates DatagramSocket on specified address if the remote host is available.
     *
     * @param address String address of remote host.
     */
    public void ConnectToRemoteServer(String address)
    {
        try {

            if (clientSocket != null) {
                System.out.println("Breaking current connection...");
                clientSocket.close();
            }

            System.out.println("Connecting to " + address);

            if (isReachable(address, defaultPort, 10)) {
                System.out.println("Host is available, connecting..");

                InetAddress serverAddress = InetAddress.getByName(address);
                clientSocket = new DatagramSocket();
                clientSocket.connect(serverAddress, defaultPort);

                System.out.println("Connected to " + serverAddress + ":" + defaultPort);

            } else {
                System.out.println("Host is unreachable");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends input command to remote host and outputs the response.
     *
     * @param command Command input.
     */
    public void SendServerCommandAndReceiveResponse(String command)
    {
        command = command.replace("^\\W*|\\W*$", "");
        command = command.replace("\\s+", "\t");

        try {
            //Prepare byte array of message
            byte[] data = command.getBytes();

            DatagramPacket dataPacket = new DatagramPacket(data, data.length);

            clientSocket.send(dataPacket);

            byte[] responseData = new byte[1024];
            DatagramPacket response = new DatagramPacket(responseData, responseData.length);

            clientSocket.receive(response);

            System.out.println("Received response: ");
            System.out.println(new String(response.getData()));

        } catch (Exception ex) {
            System.out.println("An error occurred");
            System.out.println(ex);
            clientSocket.close();
        }
    }

    /**
     * Parses incoming command input and executes it or sends to remote host.
     * EXIT to exit application.
     * CONNECT [address] to connect.
     * Other commands will be sent to remote host.
     *
     * @param command Command input.
     */
    public void ParseAndExecuteCommand(String command)
    {
        try {

            if (exitCommandPattern.matcher(command).matches()) {

                System.out.println("Exiting...");
                if (clientSocket != null) {
                    clientSocket.close();
                }
                System.exit(0);
            }

            Matcher connectCommandMatcher = connectCommandPattern.matcher(command);

            if (connectCommandMatcher.matches()) {

                String serverAddress = connectCommandMatcher.group("ServerAddress");
                ConnectToRemoteServer(serverAddress);
            } else {

                if (clientSocket != null) {
                    System.out.println("Sending command: " + command);
                    SendServerCommandAndReceiveResponse(command);
                } else {
                    System.out.println("Client is not connected.");
                }
            }
        } catch (Exception ex) {

            System.out.println("Hostname not found.");
        }

    }

    /**
     * Checks if remote host is available by creating a socket with specified address and port.
     *
     * @param address       Remote host address.
     * @param openPort      Remote host port.
     * @param timeOutMillis Socket timeout.
     * @return
     */
    private boolean isReachable(String address, int openPort, int timeOutMillis)
    {
        try {
            try (DatagramSocket soc = new DatagramSocket()) {
                soc.connect(new InetSocketAddress(address, openPort));
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}