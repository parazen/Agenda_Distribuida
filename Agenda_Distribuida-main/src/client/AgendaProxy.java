package client;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import contato.Contato;
import contato.JSONContato;

public class AgendaProxy{

	private int requestId = 0;

	private UDPClient client;

	// O ideal seria solicitar os dados de conexao ao cliente
	// através de um nome de domínio (ex: www.ufc.br)
	
	public AgendaProxy() throws SocketException, UnknownHostException{
		client = new UDPClient();
	}

	public boolean adicionarContato(Contato contato) throws Exception {
		JSONObject contatoJson = new JSONObject(contato.toString());
		String str_res = null;
		try {
			str_res = doOperation("Agenda", "adicionarContato", contatoJson.toString()); 
		}
		catch (IOException e) {
			//
		}

		if(!str_res.equals("true") && !str_res.equals("false")) {
			throw new Exception(str_res);
		}
		return Boolean.parseBoolean(str_res);
	}

	public boolean editarContato(int id_contato, Contato contato) throws Exception {
		JSONObject contatoJson = new JSONObject(contato.toString());
		String str_res = null;
		try {
			String args = "{\"id\":" + id_contato + ",\"contato\":" + contatoJson.toString() + "}";
			str_res = doOperation("Agenda", "editarContato", args); 
		}
		catch (IOException e) {
			//
		}

		if(!str_res.equals("true") && !str_res.equals("false")) {
			throw new Exception(str_res);
		}
		return Boolean.parseBoolean(str_res);
	}

	public String listarContatos() {

		try {
			return doOperation("Agenda", "listarContatos", "");
		}
		catch (IOException e) {
			return "Erro!";
		}
	}
	
	public Contato[] buscarContato(String nome) {
		ArrayList<Contato> contatos = new ArrayList<>();
		String contatos_str = null;
		try{
			contatos_str = doOperation("Agenda", "buscarContato", nome);
		}
		catch (IOException e){
			
		}
		JSONArray contatos_json = new JSONArray(contatos_str);

		for(int i = 0; i < contatos_json.length(); i++) {
			contatos.add(JSONContato.jsonParaContato(contatos_json.getJSONObject(i).toString()));
		}

          Contato[] contatos_resultado = new Contato[contatos.size()];
          return contatos.toArray(contatos_resultado);
	}

	public boolean removerContato(int id_contato){
		boolean result = false;
		try{
			result = Boolean.parseBoolean(doOperation("Agenda", "removerContato", String.valueOf(id_contato)));
		}
		catch (IOException e){
			
		}
		return result;
	}

	public void limparAgenda(){
		try{
			doOperation("Agenda", "limparAgenda", "");
		}
		catch (IOException e){
			
		}
	}

	public int getCurrentId() {
		int id = 0;
		try{
			id = Integer.parseInt(doOperation("Agenda", "getCurrentId", ""));
		}
		catch (IOException e){
			
		}
		return id;
	}

	public String doOperation(String objectRef, String method, String args) throws IOException {
		byte[] mensagem = empacotaMensagem(objectRef, method, args);

		// RETRANSMISSÃO
		for(int i = 0; i < 100; i++) {
			// envio
			client.sendEcho(mensagem);

			try {
				// recebimento
				byte[] serverMessage = client.receiveEcho();
				JSONObject obj = desempacotaMensagem(serverMessage);
				if(((int) obj.get("id")) == (requestId - 1)) {
					return obj.get("arguments").toString();
				}
			}
			catch(SocketTimeoutException e) {

			}
			catch(IOException e) {

			}
		}
		return "Erro";
	}

	public void finaliza() {
		byte[] mensagem = empacotaMensagem("", "", "Finaliza");
		try {
			client.sendEcho(mensagem);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		client.close();
	}

    	public byte[] empacotaMensagem(String objectRef, String method, String args) {
		JSONObject mensagem = new JSONObject();
		mensagem.put("type", 0);
		mensagem.put("id", requestId++);
		mensagem.put("objReference", objectRef);
		mensagem.put("methodId", method);
		mensagem.put("arguments", args);

        	return mensagem.toString().getBytes();
    }

	public static JSONObject desempacotaMensagem(byte[] resposta) {
		String resposta_string = new String(resposta, StandardCharsets.UTF_8);
		return new JSONObject(resposta_string);
    	}

    	

}