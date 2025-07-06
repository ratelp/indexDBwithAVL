package Trabalho01.src;

import java.io.IOException;

import Trabalho01.src.clientSide.Cliente;
import Trabalho01.src.clientSide.Dispositivo;
import Trabalho01.src.clientSide.Menu;
import Trabalho01.src.serverSide.Servidor;
import Trabalho01.src.serverSide.listaEncadeada.ListaCircular;

public class Main {
    
    public static void main(String[] args) {
        
        Cliente cliente = new Cliente(); // inicializa cliente
        Dispositivo controleDispositivo = new Dispositivo(null);//inicializa controlador de dispositivo
        ListaCircular BancoDeDados = new ListaCircular(); // inicializa banco de dados
        Menu menu = new Menu(); // inicializa menu

        Servidor server = new Servidor(BancoDeDados); // inicializa server

        menu.interfaceCliente(cliente, server, controleDispositivo); // inicializa interface do cliente (usuário)

        // fecha arquivo
        try {
            server.writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar arquivo");
        }
    }

}
