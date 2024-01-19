package server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.TreeMap;

import contato.Contato;

public class Agenda {
     private static int current_id = 0;
     private static final String ARQUIVO_AGENDA = "database/agenda.txt";
     private TreeMap<Integer, Contato> agenda;

     public Agenda() {
          this.agenda = new TreeMap<>();
     }

     public boolean adicionarContato(Contato contato) throws Exception {
          if(this.agenda.containsKey(contato.getId())) {
               return false;
          }
          if(contato.getPrimeiroNome().isEmpty() || contato.getTelefone01().isEmpty()) {
               throw new Exception("Campos 'Primeiro Nome' e 'Telefone 01' devem estar ambos preenchidos!");
          }

          lerArquivo();
          this.agenda.put(contato.getId(), contato);
          salvarArquivo();
          return true;
     }

     public int getCurrentId() {
          lerArquivo();
          current_id++;
          salvarArquivo();
          System.out.println(current_id);
          return current_id;
     }

     public String listarContatos() {
          lerArquivo();

          if(agenda.isEmpty()) {
               return "Agenda Vazia!";
          }

          StringBuilder contatos = new StringBuilder();
          for(Contato cont_agenda : agenda.values()) {
               contatos.append("===============================================\n");
               contatos.append("ID: " + cont_agenda.getId() + "\n");
               contatos.append("Nome: " + cont_agenda.getPrimeiroNome() + "\n");
               if(cont_agenda.getUltimoNome() != null) {
                    contatos.append("Sobrenome: " + cont_agenda.getUltimoNome() + "\n");
               }
               contatos.append("Telefone 01: " + cont_agenda.getTelefone01() + "\n");

               if(cont_agenda.getTelefone02() != null) {
                    contatos.append("Telefone 02: " + cont_agenda.getTelefone02() + "\n");
               }

               if(cont_agenda.getEmail() != null) {
                    contatos.append("E-mail: " + cont_agenda.getEmail() + "\n");
               }
               
               if(cont_agenda.getDiaAniversario() != null) {
                    contatos.append("Data de Aniversário: " + cont_agenda.getDiaAniversario().getDia() + "/" + cont_agenda.getDiaAniversario().getMes() + "/" + cont_agenda.getDiaAniversario().getAno() + "\n");
               }
               
               if(cont_agenda.getEnderecoCasa() != null) {
                    contatos.append("Rua: " + cont_agenda.getEnderecoCasa().getRua() + "\n");
                    contatos.append("Bairro: " + cont_agenda.getEnderecoCasa().getBairro() + "\n");
                    contatos.append("Cidade: " + cont_agenda.getEnderecoCasa().getCidade() + "\n");
                    if(cont_agenda.getEnderecoCasa().getCep() != null) {
                         contatos.append("CEP: " + cont_agenda.getEnderecoCasa().getCep() + "\n");
                    }
               }

               contatos.append("===============================================\n");
          }

          return contatos
          .replace(contatos.length() - 1, contatos.length(), "")
          .toString();
     }

     public Contato[] buscarContato(String nome){
          lerArquivo();

          ArrayList<Contato> contatos = new ArrayList<>();
          for (Contato cont_agenda : agenda.values()){
               if (cont_agenda.getPrimeiroNome().equals(nome)){
                    contatos.add(cont_agenda);
               }
          }
          Contato[] contatos_resultado = new Contato[contatos.size()];
          
          return contatos.toArray(contatos_resultado);
     }
     
     public boolean removerContato(int id_contato){
          lerArquivo();
          boolean cont = (agenda.remove(id_contato) != null);
          System.out.println(cont);
          salvarArquivo();
          return cont;
     }

     public void limparAgenda(){
          lerArquivo();
          agenda.clear();
          salvarArquivo();
     }

     public boolean editarContato(int id_contato, Contato novo_contato) throws Exception {
          if(!this.agenda.containsKey(id_contato)) {
               throw new Exception("O contato requerido não existe!");
          }
          if(novo_contato.getPrimeiroNome().isEmpty() || novo_contato.getTelefone01().isEmpty()) {
               throw new Exception("Campos 'Primeiro Nome' e 'Telefone 01' devem estar ambos preenchidos!");
          }
          lerArquivo();
          boolean resultado = (this.agenda.put(id_contato, novo_contato) != null);
          salvarArquivo();
          return resultado;
     }

     public void salvarArquivo() {
          try (
               FileOutputStream arquivoAgenda = new FileOutputStream(ARQUIVO_AGENDA);
               ObjectOutputStream agendaStream = new ObjectOutputStream(arquivoAgenda);
          ) {
               agendaStream.writeObject(current_id);
               for (Contato contato : this.agenda.values()) {
                    agendaStream.writeObject(contato);
               }
          }
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

     private void lerArquivo() {
          try (
               FileInputStream arquivoAgenda = new FileInputStream(ARQUIVO_AGENDA);
               ObjectInputStream agendaStream = new ObjectInputStream(arquivoAgenda);
          ) {
               Contato contatoTemporario;
               current_id = (int) agendaStream.readObject();
               while (true) {
                    try {
                         contatoTemporario = (Contato) agendaStream.readObject();
                         this.agenda.put(contatoTemporario.getId(), contatoTemporario);
                    }
                    catch (EOFException e) {
                         break;
                    }
               }
          } 
          // O arquivo não pôde ser encontrado
          catch(FileNotFoundException e) {
               System.out.println("Arquivo não encontrado. Criando arquivo...");
          }
          catch(ClassNotFoundException e) {
              System.out.println("Tentando ler um objeto de uma classe desconhecida.");
          }
          catch(StreamCorruptedException e) { // thrown by the constructor ObjectInputStream
              System.out.println("Formato do arquivo não é válido.");
          }
          // Arquivo sem cabeçalho/vazio
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

}
