package Trabalho01.src.serverSide.arvoreAVLs;

import java.time.format.DateTimeFormatter;

import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class ArvoreAVLDispositivo {


    public NoAVLDispositivo raiz = null;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // CHAMA A FUNÇÃO DE INSERIR
    public void inserir(RegistroClimatico registro) {
        raiz = inserir(raiz, registro);
    }


    // FAZ INSERÇÃO NA ARVORE DE DISPOSITIVOS
    public NoAVLDispositivo inserir(NoAVLDispositivo arv, RegistroClimatico registro){

        // inserção

        if(arv == null){
            // localizou posição disponível, é registrado um novo no de dispositivo
            return new NoAVLDispositivo(registro);
        }

        // compara id do registro com id do nó 
        int compare = registro.idDispositivo.compareTo(arv.chaveString);

        if (compare < 0){
            // se id do registro for menor irá para esquerda
            arv.esquerda = inserir(arv.esquerda, registro);

        }else if(compare > 0) {
            // se id do registro for maior irá para direita
            arv.direita = inserir(arv.direita, registro);
        }else{
            // caso localize id igual, adiciona na lista e incrementa contador
            arv.listaDispositivos[arv.contadorDispositivos++] = registro;
            return arv;
        }

        // Atualiza altura

        arv.altura = 1 + maior(altura(arv.esquerda), altura(arv.direita));

        // Coleta fator de balanceamento do nó e de seus filhos

        int fb = obterFB(arv);
        int fbSubArvEsq = obterFB(arv.esquerda);
        int fbSubArvDir = obterFB(arv.direita);

        // Rotação direita simples

        if(fb > 1 && fbSubArvEsq >= 0){
            return rotacaoDireitaSimples(arv);
        }

        // Rotação esquerda simples

        if(fb < -1 && fbSubArvDir <= 0 ){
            return rotacaoEsquerdaSimples(arv);
        }


        // Rotação dupla direita

        if(fb > 1 && fbSubArvEsq < 0) {

        arv.esquerda = rotacaoEsquerdaSimples(arv.esquerda);

        return rotacaoDireitaSimples(arv);
        }

        // Rotação dupla esquerda

        if(fb < -1 && fbSubArvDir > 0) {

        arv.direita = rotacaoDireitaSimples(arv.direita);

        return rotacaoEsquerdaSimples(arv);

        }

        // retorna nó
        return arv;

    }

    // CHAMA FUNÇÃO DE REMOVER
    public void remover(String stringChave) {
        raiz = remover(raiz, stringChave);
    }

    // FAZ REMOÇÃO NA ARVORE DE DISPOSITIVOS
    private NoAVLDispositivo remover(NoAVLDispositivo arv, String stringChave){

        // remoção

        if(arv == null){
            // não localizou
            return arv;
        }

        // compara id passado com id do nó
        int compare = stringChave.compareTo(arv.chaveString);


        if (compare < 0) {
            // se id do registro for menor irá para esquerda
            arv.esquerda = remover(arv.esquerda, stringChave);
        }else if (compare > 0){
            // se id do registro for maior irá para direita
            arv.direita = remover(arv.direita, stringChave);
        }else{

            // localizou nó

            // caso possua mais de um dispositivo
            if (arv.contadorDispositivos > 0) {
                // remove cada dispositivo do vetor
                arv.contadorDispositivos--;
                arv.listaDispositivos[arv.contadorDispositivos] = null;
                return arv;
            }

            // em caso do nó não possuir filhos
            if((arv.esquerda == null && arv.direita == null)){
                arv = null;
            }
            // em caso do nó só ter filho a direita
            else if(arv.esquerda == null){
                NoAVLDispositivo temp = arv;
                arv = temp.direita;
                temp = null;
            }

            // em caso do nó só ter filho a esquerda
            else if(arv.direita == null){
                NoAVLDispositivo temp = arv;
                arv = temp.esquerda;
                temp = null;
            }else{
                // em caso do nó possuir dois filhos
                // pega menor valor da sub árvore direita e coloca no atual
                NoAVLDispositivo temp = menorChave(arv.direita);

                for (int i = 0; i < temp.contadorDispositivos; i++) {
                    // repassando cada dispositivo do nó que irá assumir posição do nó removido
                    arv.listaDispositivos[i] = temp.listaDispositivos[i];
                }

                // faz a "transferência" dos dados dos nós
                arv.chaveString = temp.chaveString;
                arv.referencia = temp.referencia;
                temp.chaveString = stringChave;

                // passa para remover o nó folha
                arv.direita = remover(arv.direita, temp.chaveString);
            }
        }

        // em caso do nó atual ser nulo, retorna ao nó anterior e realiza verificações

        if(arv == null){
            return arv;
        }


        // Atualiza altura

        arv.altura = 1 + maior(altura(arv.esquerda), altura(arv.direita));

        // Coleta fator de balanceamento do nó e de seus filhos

        int fb = obterFB(arv);
        int fbSubArvEsq = obterFB(arv.esquerda);
        int fbSubArvDir = obterFB(arv.direita);

        // Rotação direita simples
        if(fb > 1 && fbSubArvEsq >= 0){
            return rotacaoDireitaSimples(arv);
        }

        // Rotação esquerda simples

        if(fb < -1 && fbSubArvDir <= 0 ){
            return rotacaoEsquerdaSimples(arv);
        }


        // Rotação dupla direita

        if(fb > 1 && fbSubArvEsq < 0) {
            arv.esquerda = rotacaoEsquerdaSimples(arv.esquerda);

            return rotacaoDireitaSimples(arv);
        }

        // Rotação dupla esquerda

        if(fb < -1 && fbSubArvDir > 0) {
            arv.direita = rotacaoDireitaSimples(arv.direita);

            return rotacaoEsquerdaSimples(arv);

        }

        // retorna nó
        return arv;
    }

    // RETORNA LISTA COM OS QUE POSSUEM OS MESMO IDS DISPOSITIVOS 
    public RegistroClimatico[] busca(NoAVLDispositivo arv, String chaveString) {
        RegistroClimatico [] results = new RegistroClimatico [20]; // colocado limite de 20

        int index = 0; 
        // localiza nó que está com o id dispositivo
        NoAVLDispositivo no = buscaNo(arv, chaveString);
        if (no != null) {
            results[index++] = no.referencia;
            for (int i = 0; i < no.contadorDispositivos; i++) {
                results[index++] = no.listaDispositivos[i];
            }
        }
        return results;
    }

    // LOCALIZA NÓ COM A CHAVE PASSADA PASSADA EM BUSCA
    private NoAVLDispositivo buscaNo(NoAVLDispositivo arv, String chaveString) {
        if (arv == null)
            return null;

        // compara chave string com chave do nó
        int cmp = chaveString.compareTo(arv.chaveString);

        if (cmp < 0)
            return buscaNo(arv.esquerda, chaveString);
        else if (cmp > 0)
            return buscaNo(arv.direita, chaveString);
        else
            return arv;
    }
  
    // RETORNA ALTURA
    int altura(NoAVLDispositivo arv){

    if(arv == null){
        return -1;
    }

    return arv.altura;

    }

    // COMPARA ALTURAS PARA UTILIZAR A MAIOR
    private int maior(int a, int b){
        return (a > b) ? a : b;
    }
 
    // CALCULA FATOR DE BALANCEAMENTO
    private int obterFB(NoAVLDispositivo arv){

        if(arv == null){
            return 0;
        }
        
        return altura(arv.esquerda) - altura(arv.direita);
    }

    // RETORNA O NÓ DO MENOR VALOR DA SUB ÁRVORE DIREITA 
    private NoAVLDispositivo menorChave(NoAVLDispositivo arv){

    NoAVLDispositivo temp = arv;

    if(temp == null){
        return null;
    }

    // localiza nó com menor valor
    while(temp.esquerda != null){
        temp = temp.esquerda;
    }

    // retorna nó
    return temp;
    }

    // REALIZA A ROTAÇÃO DIREITA SIMPLES 
    private NoAVLDispositivo rotacaoDireitaSimples(NoAVLDispositivo y){

    //  y       x
    //x    ->     y
    //  z       z
    
    NoAVLDispositivo x = y.esquerda; // guarda a subarvore esquerda da árvore original
    NoAVLDispositivo z = x.direita; // subarvore direita da subarvore guardada

    // Executa rotação
    x.direita = y;
    y.esquerda = z;

    // Atualiza as alturas
    y.altura = maior(altura(y.esquerda), altura(y.direita)) + 1;
    x.altura = maior(altura(x.esquerda), altura(x.direita)) + 1;

    // Retorna a nova raiz
    return x;
    }

    // REALIZA A ROTAÇÃO ESQUERDA SIMPLES 
    private NoAVLDispositivo rotacaoEsquerdaSimples(NoAVLDispositivo x){

    //  x             y
    //    y    ->   x
    //  z             z    

    NoAVLDispositivo y = x.direita; // guarda subarvore direita da árvore original
    NoAVLDispositivo z = y.esquerda; // subarvore esquerda da subarvore guardada

    // executa rotação
    y.esquerda = x; 
    x.direita = z;  

    // Atualiza as alturas
    x.altura = maior(altura(x.esquerda), altura(x.direita)) + 1;
    y.altura = maior(altura(y.esquerda), altura(y.direita)) + 1;

    // Retorna a nova raiz
    return y;
    }

}

