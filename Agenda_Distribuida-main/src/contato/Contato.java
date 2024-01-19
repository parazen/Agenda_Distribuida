package contato;

import java.io.Serializable;

import utilitario.Validador;

public class Contato implements Serializable {
    private final int id;
    private String primeiroNome;
    private String ultimoNome;
    private String telefone01;
    private String telefone02;
    private Data diaAniversario;
    private String email;
    private Endereco enderecoCasa;

    public Contato(int id, String primeiroNome, String telefone01) throws IllegalArgumentException {
        if(id < 0) {
            throw new IllegalArgumentException("Erro: Número de ID inválido!");
        }
        this.id = id;
        setPrimeiroNome(primeiroNome);
        setTelefone01(telefone01);
    }

    public Contato(int id, String primeiroNome, String ultimoNome, String telefone01, String telefone02, Data diaAniversario, String email, Endereco enderecoCasa) throws IllegalArgumentException {
        this(id, primeiroNome, telefone01);

        if(ultimoNome != null) {
            setUltimoNome(ultimoNome);
        }

        if(telefone02 != null) {
            setTelefone02(telefone02);
        }

        if(diaAniversario != null) {
            setDiaAniversario(diaAniversario);
        }

        if(email != null) {
            setEmail(email);
        }

        if(enderecoCasa != null) {
            setEnderecoCasa(enderecoCasa);
        }
    }

    // =========================================================================
    // GETTERS
    // =========================================================================

    public int getId() {
        return this.id;
    }

    public String getPrimeiroNome() {
        return this.primeiroNome;
    }

    public String getUltimoNome() {
        return this.ultimoNome;
    }

    public String getTelefone01() {
        return this.telefone01;
    } 

    public String getTelefone02() {
        return this.telefone02;
    } 

    public Data getDiaAniversario() {
        return this.diaAniversario;
    }

    public String getEmail() {
        return this.email;
    } 

    public Endereco getEnderecoCasa() {
        return this.enderecoCasa;
    } 

    // =========================================================================
    // SETTERS
    // =========================================================================

    private void setPrimeiroNome(String primeiroNome) throws IllegalArgumentException {
        if(!Validador.validarIdentificador(primeiroNome)) {
            throw new IllegalArgumentException("Erro: Primeiro Nome inválido!");
        }
        this.primeiroNome = primeiroNome;
    }

    private void setUltimoNome(String ultimoNome) throws IllegalArgumentException {
        if(!Validador.validarIdentificador(ultimoNome)) {
            throw new IllegalArgumentException("Erro: Último Nome inválido!");
        }
        this.ultimoNome = ultimoNome;
    }

    private void setTelefone01(String telefone01) throws IllegalArgumentException {
        if(!Validador.validarTelefone(telefone01)) {
            throw new IllegalArgumentException("Erro: Telefone 01 inválido!");
        }
        this.telefone01 = telefone01;
    }

    private void setTelefone02(String telefone02) throws IllegalArgumentException {
        if(!Validador.validarTelefone(telefone02)) {
            throw new IllegalArgumentException("Erro: Telefone 02 inválido!");
        }
        this.telefone02 = telefone02;
    }

    private void setEmail(String email) throws IllegalArgumentException {
        if(!Validador.validarEmail(email)) {
            throw new IllegalArgumentException("Erro: E-mail inválido!");
        }
        this.email = email;
    }

    private void setEnderecoCasa(Endereco enderecoCasa) {
        this.enderecoCasa = enderecoCasa;
    }

    private void setDiaAniversario(Data diaAniversario) {
        this.diaAniversario = diaAniversario;
    }

    // =========================================================================
    // PUBLIC FUNCTIONS
    // =========================================================================

    public String toString() {
        String contato_str = String.format("{\"id\":%d,\"primeiroNome\":\"%s\",\"telefone01\":\"%s\"",
                                            this.id, this.primeiroNome, this.telefone01);

        if(this.ultimoNome != null) {
            contato_str += String.format(",\"ultimoNome\":\"%s\"", this.ultimoNome);
        }

        if(this.telefone02 != null) {
            contato_str += String.format(",\"telefone02\":\"%s\"", this.telefone02);
        }
        
        if(this.email != null) {
            contato_str += String.format(",\"email\":\"%s\"", this.email);
        }
        
        if(this.diaAniversario != null) {
            contato_str += String.format(",\"diaAniversario\":%s", this.diaAniversario.toString());
        }
        
        if(this.enderecoCasa != null) {
            contato_str += String.format(",\"enderecoCasa\":%s", this.enderecoCasa.toString());
        }
                                        

        return contato_str + "}";    
    }
}