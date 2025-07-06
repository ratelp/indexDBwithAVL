package Trabalho01.src.serverSide;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import Trabalho01.src.clientSide.Dispositivo;
import Trabalho01.src.serverSide.arvoreAVLs.ArvoreAVL;
import Trabalho01.src.serverSide.arvoreAVLs.ArvoreAVLDispositivo;
import Trabalho01.src.serverSide.arvoreAVLs.NoAVL;
import Trabalho01.src.serverSide.listaEncadeada.ListaCircular;
import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class Servidor {

    ListaCircular baseDeDados;
    public int quantidadeRegistros = 0;
    public ArvoreAVL arvoreDeIndices;
    public ArvoreAVLDispositivo arvoreDeIndicesDispositivo;
    public FileWriter writer;

    // CONSTRUTOR DO SERVIDOR
    public Servidor(ListaCircular baseDeDados){
        inicializacao(baseDeDados); // registra 100 dados solicitados pela avaliação
        this.baseDeDados =  new ListaCircular() ; // inicia arvore que irá indexar base de dados
        this.arvoreDeIndices = new ArvoreAVL() ; // vincula base de dados
        this.arvoreDeIndicesDispositivo = new ArvoreAVLDispositivo(); // inicia arvore que irá indexar dispositivos
        this.quantidadeRegistros = baseDeDados.ultimo.idRegistro; // coleta quantidade de registros
        try {
            this.writer = new FileWriter("./Trabalho01/log/serverLog.txt");
        } catch (IOException e){
            System.out.println("Erro ao criar arquivo.");
        }

        // pega ultimo para interar por todos os dados, comparar e verificar se já passou por todos de fato
        RegistroClimatico atual = baseDeDados.ultimo;

        if(atual != null){
            do{
                // Indexando banco de dados com a arvore avl usando id de registro
                arvoreDeIndices.raiz = arvoreDeIndices.inserir(arvoreDeIndices.raiz,atual.idRegistro, atual);

                // Indexando banco de dados com a arvore avl usando id de dispositivo
                arvoreDeIndicesDispositivo.raiz = arvoreDeIndicesDispositivo.inserir(arvoreDeIndicesDispositivo.raiz, atual);

                // passa para próximo registro da base de dados
                atual = atual.proximo;

                // intera até chegar no último registro
            }while(atual != baseDeDados.ultimo);
        }
    }
    
    // BUSCA ATRAVÉS DE ID (INT)
    public RegistroClimatico busca(int chave){
        NoAVL busca = arvoreDeIndices.busca(arvoreDeIndices.raiz, chave);
        if (busca == null){
            return null;
        }else{
            return busca.referencia;
        }
    }

    // BUSCA ATRAVÉS DE ID (STRING) RETORNANDO TODOS OS IDs JUNTOS EM UM VETOR
    public RegistroClimatico[] busca(String chave){
        RegistroClimatico lista[] = arvoreDeIndicesDispositivo.busca(arvoreDeIndicesDispositivo.raiz, chave);
        return lista;
    }

    // INSERE REGISTRO NA BASE DE DADOS E NAS ARVORES AVL
    public void inserir(RegistroClimatico registro){
        baseDeDados.inserir(registro);
        arvoreDeIndices.inserir(registro.idRegistro, registro);
        arvoreDeIndicesDispositivo.inserir(registro);

        // ESCREVE NO ARQUIVO
        try {
            writer.write("\nInserção: " + registro.idRegistro + "\nAltura: " + arvoreDeIndices.raiz.altura + arvoreDeIndices.rotacao + "\n");
            arvoreDeIndices.rotacao = ", não houve rotação";
        } catch (IOException e) {
            System.out.println("Erro ao escrever");
        }
        quantidadeRegistros++;
    }

    // EFETUA ALTERAÇÃO DE ACORDO COM CAMPO DESEJADO
    public void alteracao(int chave, String campo, Object dado){
        RegistroClimatico valorBuscado = busca(chave);
        if(valorBuscado == null){
            System.out.println("valor não encontrado");
        }else{
            switch (campo.toLowerCase()){
                case "datahora":
                    if(dado instanceof LocalDateTime){
                        valorBuscado.dataHora = (LocalDateTime)dado;
                    }
                    break;
                case "temperatura":
                    if (dado instanceof Number){
                        valorBuscado.temperatura = ((Number) dado).doubleValue();
                    }
                    break;
                case "umidade":
                    if(dado instanceof Number){
                        valorBuscado.umidade = ((Number)dado).doubleValue();
                    }
                    break;
                case "pressao":
                    if(dado instanceof Number){
                        valorBuscado.pressao = ((Number)dado).doubleValue();
                    }
                    break;
                default:
                    System.out.println("Campo inexistente");
            }   
        }
    }

    // REMOVE REGISTRO NA BASE DE DADOS E NAS ARVORES AVL
    public boolean remover(int chave){
        RegistroClimatico valorBuscado = busca(chave);
        if(valorBuscado == null){
            System.out.println("Valor não encontrado!");
            return false;
        }else{
            baseDeDados.remover(valorBuscado);
            arvoreDeIndices.remover(chave);
            arvoreDeIndicesDispositivo.remover(valorBuscado.idDispositivo);
        }

        // ESCREVE NO ARQUIVO
        try {
            writer.write("\nRemoção: " + chave + "\nAltura: " + arvoreDeIndices.raiz.altura + arvoreDeIndices.rotacao +"\n");
            arvoreDeIndices.rotacao = ", não houve rotação";
        } catch (IOException e) {
            System.out.println("Erro ao escrever");
        }

        quantidadeRegistros--;

        return true;
    }

    // INICIA SERVIDOR COM 100 REGISTROS E 10 DISPOSITIVOS
    public void inicializacao(ListaCircular lista){

        Dispositivo dispositivo1 = new Dispositivo("idA");
        Dispositivo dispositivo2 = new Dispositivo("idB");
        Dispositivo dispositivo3 = new Dispositivo("idC");
        Dispositivo dispositivo4 = new Dispositivo("idD");
        Dispositivo dispositivo5 = new Dispositivo("idE");
        Dispositivo dispositivo6 = new Dispositivo("idF");
        Dispositivo dispositivo7 = new Dispositivo("idG");
        Dispositivo dispositivo8 = new Dispositivo("idH");
        Dispositivo dispositivo9 = new Dispositivo("idI");
        Dispositivo dispositivo10 = new Dispositivo("idJ");

        Dispositivo listaDispo[] ={dispositivo1,dispositivo2,dispositivo3,dispositivo4,dispositivo5,dispositivo6,dispositivo7,dispositivo8,dispositivo9,dispositivo10};

        Random rand = new Random();

        int j = 0;

        for (int i = 1; i <  101; i++) { 
            RegistroClimatico registro = new RegistroClimatico(listaDispo[j].idDispositivo, rand.nextInt(0,41), rand.nextInt(10,41), rand.nextInt(1,11));
            lista.inserir(registro);
            j++;
            if(i % 10 == 0){
                j = 0;
            }
        }
    }
}



