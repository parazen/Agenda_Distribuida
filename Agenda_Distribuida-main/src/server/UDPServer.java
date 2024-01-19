package server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {

    public static void main(String[] args){
          try{
               DatagramSocket socket = new DatagramSocket(4445);
               ServerConnection server = new ServerConnection(socket);
               server.start();
          }
          catch(SocketException e) {
               System.out.println(e);
          }
     }
}