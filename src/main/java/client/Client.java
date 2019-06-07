package client;

import java.io.IOException;
import java.net.*;

public class Client {


    public static void main(String[] args){
         /*DatagramSocket provides a socket to send and receive any datagram packet.
             It can send even broadcast message if network has that ability.
             We can manage that by SO_BROADCAST flag which is on by default.
         */

        DatagramSocket sc =null;

        try{
            /*Create DatgramSocket object and bind to any random socket chosen by OS.
            We can use sc.getLocalPort() to check the chosen port
             */
            sc = new DatagramSocket();

            //Server port number
            int portNum = 1234;

            //Create InetAddress object on localhost
            InetAddress ad = InetAddress.getByName("localhost");

            //Prepare byte array of message
            byte[] data = "Hello".getBytes();

              /*Create packet which contains our data and labeled to reach
            to the port and IP address mentioned
            */

            DatagramPacket dp = new DatagramPacket(data, data.length,ad,portNum);

            //Send the packet using datagram socket
            sc.send(dp);

            //Receive response from sent by process at the other end
            data = new byte[100];
            sc.receive(new DatagramPacket(data, data.length));

             /*Easy way to print a byte data into string format
            Ref: //https://www.mkyong.com/java/how-do-convert-byte-array-to-string-in-java/
             */
            System.out.println("Message Server: " + new String(dp.getData()));
            data = "quit".getBytes();

            sc.send(new DatagramPacket(data,data.length,ad,portNum));
        }
        catch (SocketException e){
            //Could be thrown by DatagramSocket
            System.out.println(e.getMessage());
        } catch (UnknownHostException e) {
            //Could be thrown by InetAddress
            e.printStackTrace();
        } catch (IOException e) {
            //Could be thrown while sending/receiving packets
            e.printStackTrace();
        } finally {
            sc.close();
        }

    }
}
