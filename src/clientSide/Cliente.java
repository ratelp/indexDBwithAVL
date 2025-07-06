package Trabalho01.src.clientSide;

import java.time.format.DateTimeFormatter;

import Trabalho01.src.serverSide.Servidor;
import Trabalho01.src.serverSide.arvoreAVLs.NoAVL;
import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class Cliente {

    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    

    // BUSCA PELO ID (INT)
    public void busca(Servidor server, int chave){
        RegistroClimatico referencia = server.busca(chave);
        System.out.println("--\nId Registro: " + referencia.idRegistro + "\nId Dispositivo: " + referencia.idDispositivo + "\nData: " + referencia.dataHora.format(dateFormatter) + "\nHora: " + referencia.dataHora.format(timeFormatter) + "\nTemperatura: " + referencia.temperatura + "\nUmidade: " + referencia.umidade + "\nPressão: " + referencia.pressao);
    }

    // BUSCA PELO ID (STRING)
    public void busca(Servidor server, String chave){
        RegistroClimatico lista[] = server.busca(chave);

        for(RegistroClimatico registro : lista){
            if(registro == null){
                break;
            }else{
                System.out.println("--\nId Registro: " + registro.idRegistro + "\nId Dispositivo: " + registro.idDispositivo + "\nData: " + registro.dataHora.format(dateFormatter) + "\nHora: " + registro.dataHora.format(timeFormatter) + "\nTemperatura: " + registro.temperatura + "\nUmidade: " + registro.umidade + "\nPressão: " + registro.pressao);
            }
        }

    }

    // CHAMA FUNÇÃO LISTAGEM PASSANDO NÓ RAIZ
    public void listar(Servidor server){
        listagem(server.arvoreDeIndices.raiz);
    }

    // FAZ LISTAGEM A PARTIR DO NÓ RAIZ PASSADO PELA FUNÇÃO LISTAR
    public void listagem(NoAVL no){
        if(no != null){
            listagem(no.esquerda);
            System.out.println("--\nId Registro: " + no.referencia.idRegistro + "\nId Dispositivo: " + no.referencia.idDispositivo + "\nData: " + no.referencia.dataHora.format(dateFormatter) + "\nHora: " + no.referencia.dataHora.format(timeFormatter) + "\nTemperatura: " + no.referencia.temperatura + "\nUmidade: " + no.referencia.umidade + "\nPressão: " + no.referencia.pressao);
            listagem(no.direita);
        }
    }

    // RETORNA A QUANTIDADE DE REGISTROS NO SERVIDOR
    public int quantidadeRegistros(Servidor server){
        return server.quantidadeRegistros;
    }

    // CHAMA FUNÇÃO DO SERVER PARA REMOVER A PARTIR DE UM ID (INT)
    boolean removerRegistro(Servidor server, int chave){
        boolean resultado = server.remover(chave);
        return resultado;
    }

    // EFETUA REMOÇÃO QUINTUPLA (PARA TESTE DE AVALIAÇÃO)
    public void remocaoQuintupla(Servidor server){
        
        removerRegistro(server, 105);
        removerRegistro(server, 100);
        removerRegistro(server, 102);
        removerRegistro(server, 96);
        removerRegistro(server, 77);
    }
}
