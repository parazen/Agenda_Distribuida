package client;

import java.io.IOException;
import java.util.Random;
import contato.Contato;
import contato.Data;
import contato.Endereco;

public class User {

    public static int idGenerator(String name){

        char[] vec = name.toCharArray();
        int id = 0;
        Random rnd = new Random();

        for(char aux : vec){
            id += (int) aux + rnd.nextInt(512);
            id = (id * (int)aux)%512;
        }
        return id;
    }

    public static Contato createContato(int id){ 
        String nome = null;
        while(true) {
            nome = (System.console().readLine("Nome: "));
            if(nome == null || nome.isEmpty()) {
                System.out.println("Nome deve ser preenchido!");
                continue;
            }
            else {
                break;
            }
        }       

        String ultimonome = (System.console().readLine("Sobrenome: "));
        if(ultimonome.equals("")){
            ultimonome = null;
        }

        String telefone1 = null;
        while(true) {
            telefone1 = (System.console().readLine("Telefone: "));
            if(telefone1 == null || telefone1.isEmpty()) {
                System.out.println("Telefone 01 deve ser preenchido!");
                continue;
            }
            else {
                break;
            }
        }  

        String telefone2 = (System.console().readLine("Segundo telefone: "));
        if(telefone2.equals("")){
            telefone2 = null;
        }

        String nasc = (System.console().readLine("Data de nascimento: "));
        Data n = null;
        if(!nasc.equals("")){
            n =  new Data(Integer.parseInt(nasc.split("/")[0]), 
                               Integer.parseInt(nasc.split("/")[1]), 
                               Integer.parseInt(nasc.split("/")[2]));
        }

        String email = (System.console().readLine("Email: "));
        if(email.equals("")){
            email = null;
        }

        String rua = (System.console().readLine("Rua: "));
        String bairro = (System.console().readLine("Bairro: "));
        String cidade = (System.console().readLine("Cidade: "));
        String cep = (System.console().readLine("CEP: "));
        Endereco end = null;
        if(!(rua.equals("") || bairro.equals("") || cidade.equals("") || cep.equals(""))){
            end = new Endereco(rua, bairro, cidade, cep);
       }

        return new Contato(id, nome, ultimonome, telefone1, telefone2, n, email, end);

    }

    public static Contato createContato(){ 
        String nome = null;
        while(true) {
            nome = (System.console().readLine("Nome: "));
            if(nome == null || nome.isEmpty()) {
                System.out.println("Nome deve ser preenchido!");
                continue;
            }
            else {
                break;
            }
        }       

        String ultimonome = (System.console().readLine("Sobrenome: "));
        if(ultimonome.equals("")){
            ultimonome = null;
        }

        String telefone1 = null;
        while(true) {
            telefone1 = (System.console().readLine("Telefone: "));
            if(telefone1 == null || telefone1.isEmpty()) {
                System.out.println("Telefone 01 deve ser preenchido!");
                continue;
            }
            else {
                break;
            }
        }  

        String telefone2 = (System.console().readLine("Segundo telefone: "));
        if(telefone2.equals("")){
            telefone2 = null;
        }

        String nasc = (System.console().readLine("Data de nascimento: "));
        Data n = null;
        if(!nasc.equals("")){
            n =  new Data(Integer.parseInt(nasc.split("/")[0]), 
                               Integer.parseInt(nasc.split("/")[1]), 
                               Integer.parseInt(nasc.split("/")[2]));
        }

        String email = (System.console().readLine("Email: "));
        if(email.equals("")){
            email = null;
        }

        String rua = (System.console().readLine("Rua: "));
        String bairro = (System.console().readLine("Bairro: "));
        String cidade = (System.console().readLine("Cidade: "));
        String cep = (System.console().readLine("CEP: "));
        Endereco end = null;
        if(!(rua.equals("") || bairro.equals("") || cidade.equals("") || cep.equals(""))){
            end = new Endereco(rua, bairro, cidade, cep);
       }

        return new Contato(idGenerator(nome), nome, ultimonome, telefone1, telefone2, n, email, end);

    }
 
    public static void main(String[] args) throws IOException {

        AgendaProxy agenda = new AgendaProxy();
        int option;

        do{

            Menu.menuPrincipal();
            option = Integer.parseInt(System.console().readLine("Opção -> "));

            switch(option){
                case 0:
                    System.out.println("Programa Finalizado!");
                    break;

                // OK!
                case 1:
                    System.out.print("\033[H\033[2J");
                    try {
                        agenda.adicionarContato(createContato());
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            
                // FALTA NÃO MEXER EM CAMPOS NÃO MEXIDOS
                case 2:
                    System.out.print("\033[H\033[2J");
                    int id = Integer.parseInt(System.console().readLine("ID do contato: "));
                    try {
                        System.out.println(agenda.editarContato(id, createContato(id)) ? "Sucesso!" : "Contato informado não existe. Contato adicionado!");
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 3:
                    System.out.print("\033[H\033[2J");
                    String nome = System.console().readLine("Nome do contato: ");
                    Contato[] contatos = agenda.buscarContato(nome);
                    if(contatos.length == 0) {
                        System.out.println("Sem resultados!");
                    }
                    for(Contato contato : contatos) {
                        System.out.println("ID:" + contato.getId() + ", Nome: " + contato.getPrimeiroNome());
                    }
                    break;

                case 4:
                    System.out.print("\033[H\033[2J");
                    agenda.removerContato(Integer.parseInt(System.console().readLine("ID do contato: ")));
                    break;

                case 5:
                    System.out.print("\033[H\033[2J");
                    System.out.println(agenda.listarContatos());
                    break;

                case 6:
                    System.out.print("\033[H\033[2J");
                    agenda.limparAgenda();
                    break;


                default:
                    System.out.println("Opção Inválida");
                    break;
            }


        }while(option != 0);

        agenda.finaliza();
    }
}