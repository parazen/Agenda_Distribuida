package client;

import java.io.IOException;
import java.net.*;

public class UDPClient{
    private DatagramSocket socket;
    private InetAddress address;

	//construtor
    public UDPClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        socket.setSoTimeout(1000);
    }

    public void sendEcho(byte[] buf) throws IOException {
		//enviou
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
		socket.send(packet);
    }

    public byte[] receiveEcho() throws IOException {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
        socket.receive(packet);
        
        byte[] aux = new byte[packet.getLength()];
		for (int i = 0; i < packet.getLength(); i++) {
			aux[i] = packet.getData()[i];
		}

        return aux;
    }

    public void close() {
        socket.close();
    }

}