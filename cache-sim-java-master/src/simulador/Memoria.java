package simulador;

// Classe genérica de Memória
public class Memoria {
    protected String nome;
    protected int latencia;

    public Memoria(String nome, int latencia) {
        this.nome = nome;
        this.latencia = latencia;
    }

    public int acessar(int endereco) {
        System.out.println(nome + " acessada, latência: " + latencia + " ciclos");
        return latencia;
    }
}

// Classe Cache (subclasse de Memoria)
class Cache extends Memoria {
    private int tamanhoLinha;
    private String politicaEscrita;
    private int associatividade;
    private int conjuntos;
    private Memoria nivelInferior;

    public Cache(String nome, int latencia, int tamanhoLinha, String politicaEscrita, int associatividade,
            int conjuntos, Memoria nivelInferior) {
        super(nome, latencia);
        this.tamanhoLinha = tamanhoLinha;
        this.politicaEscrita = politicaEscrita;
        this.associatividade = associatividade;
        this.conjuntos = conjuntos;
        this.nivelInferior = nivelInferior;
    }

    @Override
    public int acessar(int endereco) {
        // Calcular índice e tag usando as funções de bits
        int offsetBits = (int) (Math.log(tamanhoLinha) / Math.log(2));
        int conjuntoBits = (int) (Math.log(conjuntos) / Math.log(2));

        int conjunto = Main.extract_bits(endereco, offsetBits, conjuntoBits);
        int tag = Main.extract_bits(endereco, offsetBits + conjuntoBits, 32 - offsetBits - conjuntoBits);

        System.out.println(nome + " acessada. Índice: " + conjunto + ", Tag: " + tag);
        Main.print_bits(endereco);
        System.out.println();

        // Simulação de hits e misses
        int hit = (int) (Math.random() * 2); // Simulação
        if (hit == 1) {
            System.out.println(nome + ": HIT");
            return latencia;
        } else {
            System.out.println(nome + ": MISS");
            return latencia + nivelInferior.acessar(endereco);
        }
    }
}