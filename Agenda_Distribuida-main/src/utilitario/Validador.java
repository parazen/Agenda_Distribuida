package utilitario;

public class Validador {

    /**
     * Valida um CEP.
     *
     * @param cep O número de CEP(pode ter ou não o traço).
     * @return "true" se o CEP é valido, e "false" caso
     *         contrário.
     */
    public static boolean validarCep(String cep) {
        return cep.matches("\\d{5}\\-?\\d{3}");
    }

    /**
      * Valida um número de telefone.
      *
      * @param telefone O número de telefone(vários formatos
      * suportados).
      * @return "true" se o telefone é valido, e "false" caso
      * contrário.
      */
    public static boolean validarTelefone(String telefone) {
        return telefone.matches("(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}\\-?\\d{4})");
    }

    /**
     * Valida um identificador regular, como um nome.
     */
    public static boolean validarIdentificador(String identificador) {
        return (identificador != null && !(identificador.isEmpty()));
   }

   /**
    * Valida um endereço de e-mail.
    *
    * @param email O endereço de e-mail.
    * @return "true" se o e-mail é valido, e "false" caso
    *         contrário.
    */
   public static boolean validarEmail(String email) throws IllegalArgumentException {
       if (email == null || email.isEmpty()) {
           throw new IllegalArgumentException("Informe um endereço de e-mail!");
       }
       return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
   }  
}
