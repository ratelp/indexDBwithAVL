package Trabalho01.src.clientSide;

import java.time.LocalDateTime;
import java.util.Scanner;

import Trabalho01.src.serverSide.Servidor;
import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class Dispositivo {

    public String idDispositivo;
    
    // CONSTRUTOR DE DISPOSITIVO
    public Dispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    // CONSTRUTOR DO CONTROLADOR DE DISPOSITIVO
    public Dispositivo(){
    }

    // EFETUA CADASTRO QUINTUPLO (PARA TESTE DE AVALIAÇÃO)
    public void cadastroQuintuplo(Servidor server){
        RegistroClimatico reg1 = new RegistroClimatico("idA", 22, 15, 11);
        RegistroClimatico reg2 = new RegistroClimatico("idB", 41, 12, 2);
        RegistroClimatico reg3 = new RegistroClimatico("idC", 10, 13, 8);
        RegistroClimatico reg4 = new RegistroClimatico("idD", 21, 14, 12);
        RegistroClimatico reg5 = new RegistroClimatico("idE", 32, 11, 15);
        cadastrar(server, reg1);
        cadastrar(server, reg2);
        cadastrar(server, reg3);
        cadastrar(server, reg4);
        cadastrar(server, reg5);
    }

    // EFETUA ALTERAÇÃO QUINTUPLO (PARA TESTE DE AVALIAÇÃO)
    public void alteracaoQuintupla(Servidor server, Cliente cliente){
        Scanner scanner = new Scanner(System.in);

        cliente.busca(server, 20);
        alteracaoRegistro(server, 20, "datahora",LocalDateTime.now());
        cliente.busca(server, 20);

        System.out.println("\nPressione 'enter' para prosseguir...");
        scanner.nextLine();

        cliente.busca(server, 91);
        alteracaoRegistro(server, 91, "temperatura", 18.3);
        cliente.busca(server, 91);

        System.out.println("\nPressione 'enter' para prosseguir...");
        scanner.nextLine();

        cliente.busca(server, 2);
        alteracaoRegistro(server, 2, "pressao", 0.5);
        cliente.busca(server, 2);

        System.out.println("\nPressione 'enter' para prosseguir...");
        scanner.nextLine();

        cliente.busca(server, 99);
        alteracaoRegistro(server, 99, "umidade", 6.7);
        cliente.busca(server, 99);

        System.out.println("\nPressione 'enter' para prosseguir...");
        scanner.nextLine();

        cliente.busca(server, 104);
        alteracaoRegistro(server, 104, "temperatura", 88);
        cliente.busca(server, 104);

        System.out.println("\nPressione 'enter' para prosseguir...");
        scanner.nextLine();

    }

    // EFETUA CADASTRO CHAMANDO FUNÇÃO DO SERVIDOR PASSANDO REGISTRO
    void cadastrar(Servidor server, RegistroClimatico Registro){
        server.inserir(Registro);
    }

    // EFETUA ALTERAÇÃO CHAMANDO FUNÇÃO DO SERVIDOR PASSANDO DADOS NECESSÁRIOS PARA MUDANÇA
    void alteracaoRegistro(Servidor server, int chave, String campo, Object dado){
        server.alteracao(chave, campo, dado);
    }


}
