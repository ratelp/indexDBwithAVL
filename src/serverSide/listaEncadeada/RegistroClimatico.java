package Trabalho01.src.serverSide.listaEncadeada;


import java.time.LocalDateTime;

public class RegistroClimatico {

    static int count = 0;
    public int idRegistro;
    public String idDispositivo;
    public LocalDateTime dataHora;
    public double temperatura;
    public double umidade;
    public double pressao;
    public RegistroClimatico proximo;

    // CONSTRUTOR DE REGISTRO CLIMATICO
    public RegistroClimatico(String idDispositivo, double temperatura, double umidade, double pressao) {

        this.idRegistro = ++count;
        this.idDispositivo = idDispositivo;
        this.dataHora = LocalDateTime.now();
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.pressao = pressao;
        this.proximo = null;
    }

    
}
