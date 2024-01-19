package server;

import org.json.JSONObject;

import contato.Contato;
import contato.JSONContato;

public class AgendaEsqueleto {
	
     private Agenda agenda;

	public AgendaEsqueleto() {
		this.agenda = new Agenda();
	}

	public String adicionarContato(String args) {
		System.out.println(args);
		Contato contato = JSONContato.jsonParaContato(args);
		boolean resultado;
		try {
			resultado = this.agenda.adicionarContato(contato);
		}
		catch(Exception e) {
			return e.getMessage();
		}
		return String.valueOf(resultado);
	}

	public String editarContato(String args) {
		JSONObject argumentos = new JSONObject(args);
		int id = (int) argumentos.get("id");
		Contato contato = JSONContato.jsonParaContato(argumentos.get("contato").toString());
		boolean resultado;
		try {
			resultado = this.agenda.editarContato(id, contato);
		}
		catch(Exception e) {
			return e.getMessage();
		}
		return String.valueOf(resultado);
	}

	public String listarContatos(String args) {
		return this.agenda.listarContatos();
	}

	public String buscarContato(String args){
		Contato[] contatos = this.agenda.buscarContato(args);
		if(contatos.length == 0) {
			return "[]";
		}
		StringBuilder contatos_str = new StringBuilder("[");
		for(Contato contato : contatos) {
			contatos_str.append(contato.toString() + ",");
		}
          return contatos_str
          .replace(contatos_str.length() - 1, contatos_str.length(), "]")
          .toString();
	}

	public String getCurrentId(String args) {
		return String.valueOf(this.agenda.getCurrentId());
	}

	public String removerContato(String args){
		return String.valueOf(this.agenda.removerContato(Integer.parseInt(args)));
	}

	public String limparAgenda(String args){
		this.agenda.limparAgenda();
		return "";
	}
}
