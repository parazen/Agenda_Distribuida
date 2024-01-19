package server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.JSONObject;

public class Despachante {

	public String selecionaEsqueleto(JSONObject request) {

		Class<?> objRef = null;
		Method method = null;
		String resposta = null;
		try {
			objRef = Class.forName("server."
					+ request.get("objReference")
                         + "Esqueleto");

			String methodName = (String) request.get("methodId");
			System.out.println("Executando: " + methodName);
			method = objRef.getMethod(methodName, String.class);
			resposta = (String) (method.invoke(objRef.newInstance(),
					((String) request.get("arguments"))));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return resposta;
	}
}