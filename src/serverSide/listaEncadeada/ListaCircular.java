package Trabalho01.src.serverSide.listaEncadeada;


public class ListaCircular {

    public RegistroClimatico ultimo;

    // CONSTRUTOR DA LISTA ENCADEADA
    public ListaCircular(){
        ultimo = null;
    }

    // INSERE NA LISTA ENCADEADA
    public void inserir(RegistroClimatico registro){
        if (ultimo == null){
            ultimo = registro;
            ultimo.proximo = registro;
        }else{
            registro.proximo = ultimo.proximo;
            ultimo.proximo = registro;
            ultimo = registro;
        }
    }

    // REMOVE NA LISTA ENCADEADA
    public void remover(RegistroClimatico registro){
        
        // SE POSSUIR SOMENTE 1 NÓ 
        if (ultimo == null || ultimo == ultimo.proximo){
            ultimo = null;
            return;
        }

        // COLETA DADOS DO PRÓXIMO REGISTRO E APONTA PARA O PRÓXIMO DO PRÓXIMO
        registro.idRegistro = registro.proximo.idRegistro;
        registro.idDispositivo = registro.proximo.idDispositivo;
        registro.dataHora = registro.proximo.dataHora;
        registro.temperatura = registro.proximo.temperatura;
        registro.pressao = registro.proximo.pressao;
        registro.umidade = registro.proximo.umidade;
        registro.proximo = registro.proximo.proximo;

    }

}
