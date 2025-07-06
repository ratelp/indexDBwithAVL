package Trabalho01.src.clientSide;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import Trabalho01.src.serverSide.Servidor;
import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class Menu {
    
    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    // MOSTRA MENU
    public void mostrarMenu(){
        System.out.println("MENU: ");
        System.out.println("1 - Listar");
        System.out.println("2 - Remover");
        System.out.println("3 - Buscar");
        System.out.println("4 - Cadastrar");
        System.out.println("5 - Alterar");
        System.out.println("6 - Quantidade Registros");
        System.out.println("7 - Teste Avaliação");
        System.out.println("8 - Sair");
        System.out.printf("Opção: ");
    }

    // INTERFACE EM QUE O CLIENTE (USUÁRIO) IRÁ INTERAGIR 
    public void interfaceCliente(Cliente cliente, Servidor server, Dispositivo dispositivo){
        
        Scanner scanner = new Scanner(System.in);

        int opcao;
        int chave;
        String chaveString;
        boolean encerrar = false;
        do{
            mostrarMenu();
            try{
                opcao = scanner.nextInt();
                switch(opcao){
                case 1:
                    cliente.listar(server);
                    break;
                case 2:
                    System.out.printf("Informe a chave: ");
                    chave = scanner.nextInt();
                    RegistroClimatico registro = server.busca(chave);
                    boolean resultado = cliente.removerRegistro(server, registro.idRegistro);
                    
                    if(resultado){
                        System.out.println("Removido com sucesso!");
                    }else{
                        System.out.println("Sem sucesso ao remover!");
                    }

                    break;
                case 3:
                    System.out.println("\n1 - IdRegistro\n2 - IdDispositivo" );
                    System.out.printf("Opção: ");
                    opcao = scanner.nextInt();
                    if(opcao == 1){
                        System.out.printf("Informe a chave: ");
                        chave = scanner.nextInt();
                        cliente.busca(server, chave);
                    }else if(opcao == 2){
                        System.out.printf("Informe a chave: ");
                        chaveString = scanner.next();
                        cliente.busca(server, chaveString);
                    }else{
                        System.out.println("Opção inválida!");
                    }
                    break;
                case 4:
                    String idDispositivo;
                    double temperatura, umidade, pressao;
                    System.out.printf("\nInforme o idDispositivo: ");
                    idDispositivo = scanner.next();
                    Dispositivo novoDispositivo = new Dispositivo(idDispositivo);
                    System.out.printf("Informe a temperatura: ");
                    temperatura = scanner.nextDouble();
                    System.out.printf("Informe a umidade: ");
                    umidade = scanner.nextDouble();    
                    System.out.printf("Informe a pressão: ");
                    pressao = scanner.nextDouble();
                    RegistroClimatico novo = new RegistroClimatico(novoDispositivo.idDispositivo, temperatura, umidade, pressao);
                    dispositivo.cadastrar(server, novo);
                    System.out.println("Cadastrado com sucesso!");
                break;
                
                case 5:
                    System.out.printf("\nInforme o ID: ");
                    chave = scanner.nextInt();
                    cliente.busca(server, chave);

                    System.out.println("\nOpções de alteração:");
                    System.out.println("1 - Data e hora");
                    System.out.println("2 - Temperatura");
                    System.out.println("3 - Umidade");
                    System.out.println("4 - Pressao");
                    System.out.printf("\nInforme o campo: ");
                    opcao = scanner.nextInt();

                    if(opcao == 1){
                        dispositivo.alteracaoRegistro(server,chave, "datahora", LocalDateTime.now());
                        System.out.println("Alterado para : " + LocalDateTime.now().format(dateFormatter) + " " + LocalDateTime.now().format(timeFormatter) + " (horário atual)");
                        System.out.println("\nRegistro Atualizado: ");
                        cliente.busca(server, chave);
                    }else if(opcao == 2){
                        System.out.printf("\nInforme a Temperatura: ");
                        opcao = scanner.nextInt();
                        dispositivo.alteracaoRegistro(server, chave, "temperatura", opcao);
                        System.out.println("\nRegistro Atualizado: ");
                        cliente.busca(server, chave);
                    }else if(opcao == 3){
                        System.out.printf("\nInforme a umidade: ");
                        opcao = scanner.nextInt();
                        dispositivo.alteracaoRegistro(server, chave, "umidade", opcao);
                        System.out.println("\nRegistro Atualizado: ");
                        cliente.busca(server, chave);
                    }else if(opcao == 4){
                        System.out.printf("\nInforme a pressao: ");
                        opcao = scanner.nextInt();
                        dispositivo.alteracaoRegistro(server, chave, "pressao", opcao);
                        System.out.println("\nRegistro Atualizado: ");
                        cliente.busca(server, chave);
                    }else{
                        System.out.println("Opção inválida!");
                    }
                    break;
                case 6:
                    System.out.println("Atualmente temos " + cliente.quantidadeRegistros(server) + " registros");
                    break;
                case 7:
                    System.out.println("\nBuscando pela chave '50'");
                    cliente.busca(server, 50);
                    System.out.println("\nBuscando pela chave 'idA'");
                    cliente.busca(server, "idA");
                    System.out.println("\nBuscando pela chave 'idH'");
                    cliente.busca(server, "idH");
                    System.out.println("\nPressione 'enter' para prosseguir...");
                    scanner.nextLine();
                    scanner.nextLine();
                    // cadastrando
                    dispositivo.cadastroQuintuplo(server);
                    cliente.listar(server);
                    System.out.println("\nPressione 'enter' para prosseguir...");
                    scanner.nextLine();
                    // alterando 2, 20, 90, 99, 101
                    dispositivo.alteracaoQuintupla(server,cliente);
                    cliente.listar(server);
                    System.out.println("\nPressione 'enter' para prosseguir...");
                    scanner.nextLine();
                    // removendo 77 96 100 102 105
                    cliente.remocaoQuintupla(server);
                    cliente.listar(server);
                    break;
                case 8:
                    encerrar = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println();
            }catch(InputMismatchException  e)
            {
                System.out.println("Entrada inválida!\n");
                scanner.nextLine();
            };
    
        }while(!encerrar);

        scanner.close();
    }
}
