package service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    private static int defaultPort = 1255;

    public static void main(String[] args) {
        DatagramSocket sc = null;

        try {
            System.out.println("Starting server on port: " + defaultPort);

            sc = new DatagramSocket(defaultPort);

            System.out.println("Server is running");

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
                String response = fileServerService.executeTheCommand();

                 /*Send back data to client.
                Get client details from packet received previously
                 */
                data = response.getBytes();
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
