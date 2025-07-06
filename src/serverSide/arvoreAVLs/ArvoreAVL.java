package Trabalho01.src.serverSide.arvoreAVLs;

import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class ArvoreAVL{

    public NoAVL raiz = null;
    public String rotacao = ", não houve rotação";

    // CHAMA A FUNÇÃO DE INSERIR
    public void inserir(int chave, RegistroClimatico registro) {
        raiz = inserir(raiz, chave, registro);
    }

    // FAZ INSERÇÃO NA ARVORE
    public NoAVL inserir(NoAVL arv, int chave, RegistroClimatico registro){

        // inserção

        if(arv == null){
            // localizou posição disponível, é registrado um novo no
            return new NoAVL(chave, registro);
        }

        if (chave < arv.chave){
            arv.esquerda = inserir(arv.esquerda, chave, registro);

        }else if(chave > arv.chave){
            arv.direita = inserir(arv.direita, chave, registro);
        }else{
            // Em Chaves duplicadas as informações são substituidas
            arv.referencia = registro;
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
            rotacao = (", Rotação direita simples no nó " + arv.chave) ;
            return rotacaoDireitaSimples(arv);
        }

        // Rotação esquerda simples

        if(fb < -1 && fbSubArvDir <= 0 ){
            rotacao = (", Rotação esquerda simples no nó " + arv.chave)  ;
            return rotacaoEsquerdaSimples(arv);
        }

        // Rotação dupla direita

        if(fb > 1 && fbSubArvEsq < 0) {
            
        rotacao = (", Rotação dupla direita no nó " + arv.chave) ;

        arv.esquerda = rotacaoEsquerdaSimples(arv.esquerda);

        return rotacaoDireitaSimples(arv);
        }

        // Rotação dupla esquerda

        if(fb < -1 && fbSubArvDir > 0) {

        rotacao = (", Rotação dupla esquerda no nó " + arv.chave) ;

        arv.direita = rotacaoDireitaSimples(arv.direita);

        return rotacaoEsquerdaSimples(arv);

        }

        // retorna nó
        return arv;

    }

    // CHAMA FUNÇÃO DE REMOVER
    public void remover(int chave) {
        raiz = remover(raiz, chave);
    }
    
    // FAZ REMOÇÃO NA ARVORE
    private NoAVL remover(NoAVL arv, int chave){

        // remoção

        if(arv == null){
            return arv;
        }

        if(chave < arv.chave){
            arv.esquerda = remover(arv.esquerda, chave);
        }else if(chave > arv.chave){
            arv.direita = remover(arv.direita, chave);
        }else{
            // localizou nó 

            // em caso do nó não possuir filhos
            if((arv.esquerda == null && arv.direita == null)){
                arv = null;
            }
            // em caso do nó só ter filho a direita
            else if(arv.esquerda == null){
                NoAVL temp = arv;
                arv = temp.direita;
                temp = null;
            }

            // em caso do nó só ter filho a esquerda
            else if(arv.direita == null){
                NoAVL temp = arv;
                arv = temp.esquerda;
                temp = null;
            }else{
                // em caso do nó possuir dois filhos
                // pega menor valor da sub árvore direita e coloca no atual
                NoAVL temp = menorChave(arv.direita);

                // faz a "transferência" dos dados dos nós
                arv.chave = temp.chave;
                arv.referencia = temp.referencia;
                temp.chave = chave;

                // passa para remover o nó folha
                arv.direita = remover(arv.direita, temp.chave);
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
            if (rotacao.compareTo(", não houve rotação") == 0){
                rotacao = (", Rotação direita simples no nó " + arv.chave );
            }else {
                rotacao += (", Rotação direita simples no nó " + arv.chave ) ;            
            }

            return rotacaoDireitaSimples(arv);
        }

        // Rotação esquerda simples

        if(fb < -1 && fbSubArvDir <= 0 ){
            if (rotacao.compareTo(", não houve rotação") == 0){
                rotacao = (", Rotação esquerda simples no nó " + arv.chave)  ;
            }else {
                rotacao +=(", Rotação esquerda simples no nó " + arv.chave) ;            
            }

            return rotacaoEsquerdaSimples(arv);
        }

        // Rotação dupla direita

        if(fb > 1 && fbSubArvEsq < 0) {
            
            if (rotacao.compareTo(", não houve rotação") == 0){
                rotacao = (", Rotação dupla direita no nó " + arv.chave );
            }else {
                rotacao += (", Rotação dupla direita no nó " + arv.chave ) ;            
            }

            arv.esquerda = rotacaoEsquerdaSimples(arv.esquerda);

            return rotacaoDireitaSimples(arv);
        }

        // Rotação dupla esquerda

        if(fb < -1 && fbSubArvDir > 0) {
            if (rotacao.compareTo(", não houve rotação") == 0){
                rotacao = (", Rotação dupla esquerda no nó " + arv.chave );
            }else {
                rotacao += (", Rotação dupla esquerda no nó " + arv.chave ) ;            
            }

            arv.direita = rotacaoDireitaSimples(arv.direita);

            return rotacaoEsquerdaSimples(arv);

        }

        // retorna nó

        return arv;
    }

    // RETORNA NÓ BUSCADO 
    public NoAVL busca(NoAVL arv, int chave){

        NoAVL temp = arv;

        while (temp != null){
            if(temp.chave == chave){
                return temp;
            }else if(chave < temp.chave){
                temp = temp.esquerda;
            }else {
                temp = temp.direita;
            }
        }
        return null;
    }

    // RETORNA ALTURA
    int altura(NoAVL arv){

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
    private int obterFB(NoAVL arv){

        if(arv == null){
            return 0;
        }
        
        return altura(arv.esquerda) - altura(arv.direita);
    }

    // RETORNA O NÓ DO MENOR VALOR DA SUB ÁRVORE DIREITA
    private NoAVL menorChave(NoAVL arv){

    NoAVL temp = arv;

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
    private NoAVL rotacaoDireitaSimples(NoAVL y){

    //  y       x
    //x    ->     y
    //  z       z

    NoAVL x = y.esquerda; // guarda a subarvore esquerda da árvore original
    NoAVL z = x.direita; // subarvore direita da subarvore guardada

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
    private NoAVL rotacaoEsquerdaSimples(NoAVL x){

    //  x             y
    //    y    ->   x
    //  z             z

    NoAVL y = x.direita; // guarda subarvore direita da árvore original
    NoAVL z = y.esquerda; // subarvore esquerda da subarvore guardada

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
