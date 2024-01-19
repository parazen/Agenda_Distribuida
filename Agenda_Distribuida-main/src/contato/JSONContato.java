package contato;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONContato {
     public static Contato jsonParaContato(String contato_json) {
		JSONObject arguments = new JSONObject(contato_json);

		int id = (int) arguments.get("id");
		String primeiroNome = (String) arguments.get("primeiroNome");
		String telefone01 = (String) arguments.get("telefone01");

		String ultimoNome = null;
		try {
			ultimoNome = (String) arguments.get("ultimoNome");
		}
		catch(JSONException e) {

		}

		String telefone02 = null; 
		try {
			telefone02 = (String) arguments.get("telefone02"); 
		}
		catch(JSONException e) {

		}

		Data diaAniversario = null;
		try {
			JSONObject data = new JSONObject(arguments.get("diaAniversario").toString());
			diaAniversario = new Data((int) data.get("dia"), (int) data.get("mes"), (int) data.get("ano"));
		}
		catch(JSONException e) {

		}

		String email = null;
		try {
			email = (String) arguments.get("email");
		}
		catch(JSONException e) {

		}

		Endereco enderecoCasa = null;
		try {
			JSONObject endereco = new JSONObject(arguments.get("enderecoCasa").toString());
			String rua = (String) endereco.get("rua");
			String bairro = (String) endereco.get("bairro");
			String cidade = (String) endereco.get("cidade");
			String cep = null;
			try {
				cep = (String) arguments.get("cep");
			}
			catch(JSONException e) {

			}
			enderecoCasa = new Endereco(rua, bairro, cidade, cep);
		}
		catch(JSONException e) {

		}

		return new Contato(id, primeiroNome, ultimoNome, telefone01, telefone02, diaAniversario, email, enderecoCasa);
    	}
}
