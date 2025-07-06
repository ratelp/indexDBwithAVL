package Trabalho01.src.serverSide.arvoreAVLs;

import Trabalho01.src.serverSide.listaEncadeada.RegistroClimatico;

public class NoAVLDispositivo {
    
    public RegistroClimatico[] listaDispositivos; // Armazena cópias da mesma chave
    public int contadorDispositivos;  // Quantidade de dados duplicados no nó
    public String chaveString; 
    public RegistroClimatico referencia;
    public NoAVLDispositivo esquerda;
    public NoAVLDispositivo direita;
    public int altura;


    // CONSTRUTOR DO NOAVL DO DISPOSITIVO
    public NoAVLDispositivo(RegistroClimatico referencia) {
        this.chaveString = referencia.idDispositivo;
        this.referencia = referencia;
        this.listaDispositivos = new RegistroClimatico[20];
        this.contadorDispositivos = 0;
    }

}
