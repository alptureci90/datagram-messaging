package service;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {



    public static void main(String[] args) {
        DatagramSocket sc = null;

        try {
            sc = new DatagramSocket(1255);

            while (true) {
                byte[] data = new byte[100];
                DatagramPacket dp = new DatagramPacket(data, data.length);

                   /*We don't need IP address for server program just to receive any message,
                but while sending response
                 */

                sc.receive(dp);
                String clientdata = new String(dp.getData()).trim();

                //System.out.print(Objects.equals(clientdata,"quit                if (clientdata.equals("quit")) break;
                System.out.println("Message Client: " + clientdata);

                FileServerService fileServerService = new FileServerService(clientdata);
                fileServerService.executeTheCommand();

                 /*Send back data to client.
                Get client details from packet received previously
                 */
                data = "Hey".getBytes();
                sc.send(new DatagramPacket(data, data.length, dp.getAddress(), dp.getPort()));
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
