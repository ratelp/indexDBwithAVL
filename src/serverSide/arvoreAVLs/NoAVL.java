package Trabalho01.src.serverSide.arvoreAVLs;

import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class NoAVL {

    int chave;
    public RegistroClimatico referencia;
    public NoAVL esquerda;
    public NoAVL direita;
    public int altura;

    // CONSTRUTOR DO NOAVL
    public NoAVL(int chave, RegistroClimatico referencia) {
        this.chave = chave;
        this.referencia = referencia;
    }


}
