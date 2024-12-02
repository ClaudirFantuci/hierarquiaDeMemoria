package simulador;

public class Processador {
    private Memoria nivelMaisProximo;

    public Processador(Memoria nivelMaisProximo) {
        this.nivelMaisProximo = nivelMaisProximo;
    }

    public void executarAcessos(int[] enderecos) {
        int tempoTotal = 0;
        for (int endereco : enderecos) {
            System.out.println("Processador requisitando endereço: " + endereco);
            tempoTotal += nivelMaisProximo.acessar(endereco);
        }
        System.out.println("Tempo total de execução: " + tempoTotal + " ciclos");
    }
}