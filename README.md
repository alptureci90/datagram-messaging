# Client

# Syntax description

* **isReachable(String address, int openPort, int timeOutMillis)** - Checks if remote host is available by creating a socket with specified address and port.
     * @param *address*       Remote host address.
     * @param *openPort*      Remote host port.
     * @param *timeOutMillis* Socket timeout.
     * @return
* **ConnectToRemoteServer(String address)** - Checks server availability. Creates DatagramSocket on specified server address if the remote host. 
     * @param *address* - string address of remote host.
* **ReadCommands()** - Starts endless loop. Wait, read and process command from console input.
* **ParseAndExecuteCommand(String command)** - Parses incoming command input and executes it or sends to remote host.
     * EXIT to exit application.
     * CONNECT *address* to connect.
     * Other commands will be sent to remote host.
     * @param *command* Command input.
* **SendServerCommandAndReceiveResponse(String command)** - Replaces symbols in message (path). Creates DatagramPacket from input message and sends it to server. Receives responce from server and parse it.


# Client-Server-Datagram
# datagram-messaging
