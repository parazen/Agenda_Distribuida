package server;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.json.JSONObject;

public class ServerConnection extends Thread {
    private DatagramPacket packet;
    private DatagramSocket socket;
    private Despachante despachante;
    private HashMap<Integer, byte[]> historicoMensagens;

    public ServerConnection(DatagramSocket socket) {
        this.socket = socket;
        this.despachante = new Despachante();
        this.historicoMensagens = new HashMap<>(); 
    }

    public byte[] empacotaMensagem(JSONObject mensagem, String args) {
		JSONObject resposta = new JSONObject();
		resposta.put("type", 1);
        resposta.put("id", (int) mensagem.get("id"));
		resposta.put("objReference", (String) mensagem.get("objReference"));
		resposta.put("methodId", (String) mensagem.get("methodId"));
		resposta.put("arguments", args);

        return resposta.toString().getBytes();
    }
    
    public static JSONObject desempacotaMensagem(byte[] resposta) {
		String resposta_string = new String(resposta, StandardCharsets.UTF_8);
        return new JSONObject(resposta_string);
    }

    public void run() {
        while(true) {
            byte[] m = receive();

            JSONObject mensagem = desempacotaMensagem(m);

            if(mensagem.get("arguments").toString().equals("Finaliza")) {
                this.historicoMensagens = new HashMap<>(); 
                continue;
            }
            
            int id = (int) mensagem.get("id");

            if(this.historicoMensagens.containsKey(id)) {
                send(this.historicoMensagens.get(id));
                continue;
            }

            String resultado = despachante.selecionaEsqueleto(mensagem);

            byte[] buf = empacotaMensagem(mensagem, resultado);    
            
            this.historicoMensagens.put(id, buf);
            
            // TESTAR RETRANSMISSAO
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            send(buf);
        }
    }

    public byte[] receive(){
        byte[] buf = new byte[1024];
        packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Limpar o lixo
        byte[] aux = new byte[packet.getLength()];
        for (int i = 0; i < packet.getLength(); i++) {
            aux[i] = packet.getData()[i];
        }
        return aux;
    }

    public void send(byte[] buf) {
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        
        try {
            socket.send(packet);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}