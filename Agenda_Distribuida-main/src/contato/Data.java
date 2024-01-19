package contato;

import java.io.Serializable;

public class Data implements Serializable {
     private int dia;
     private int mes;
     private int ano;

     public Data(int dia, int mes, int ano) throws IllegalArgumentException {
          if(!validarData(dia, mes, ano)) {
               throw new IllegalArgumentException("Erro: Data inválida.");
          }
          this.dia = dia;
          this.mes = mes;
          this.ano = ano;
     }

     public int getDia() {
          return dia;
     }

     public int getMes() {
          return mes;
     }

     public int getAno() {
          return ano;
     }

     public static boolean validarData(int dia, int mes, int ano) {
          if ((mes <= 0 || mes > 12)) {
               return false;
          }
          if (dia > 0 && dia <= 31) {
               if (mes == 2) {
                    if (verificarBissexto(ano)) {
                         if (dia <= 29) {
                              return true;
                         }
                    }
                    else {
                         if (dia <= 28) {
                              return true;
                         }
                    }
                    return false;
               }
               else if ((mes < 8) && (mes % 2 == 0)) {
                    if (dia <= 30) {
                         return true;
                    }
               }
               else {
                    if (dia <= 31) {
                         return true;
                    }
               }
          }
          return false;
     }

     private static boolean verificarBissexto(int ano) {
          return (ano % 400 == 0) || ((ano % 4 == 0) && !(ano % 100 == 0));
     }

     public String toString() {
         return String.format("{\"dia\":%d,\"mes\":%d,\"ano\":%d}",
                                             this.dia, this.mes, this.ano);  
     }
}
