package simulador;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int extract_bits(int source, int bstart, int blength) {
        int mask = (1 << blength) - 1;
        return ((source >> bstart) & mask);
    }

    public static int set_bits(int source, int bstart, int blength, int value) {
        int mask = (1 << blength) - 1;
        int shifted_mask = mask << bstart;
        int safe_value = value & mask;

        return (source & ~shifted_mask) | (safe_value << bstart);
    }

    public static void print_bits(int value) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((value >> i) & 0x01);
        }
    }

    public static void main(String[] args) {
        // Carregar a configuração do arquivo XML
        HierarquiaMemoria hierarquia = ConfiguracaoXML.carregarConfiguracao(
                "C:\\Users\\claudir\\Desktop\\dudu\\3-TRIMESTRE\\cache-sim-java-master\\src\\recursos\\exampleXML.xml");

        if (hierarquia == null || hierarquia.memoriaPrincipal == null) {
            System.out.println("Erro ao carregar a configuração da hierarquia de memória.");
            return;
        }

        // Criar a memória principal
        Memoria memoriaPrincipal = new Memoria(
                hierarquia.memoriaPrincipal.nome,
                hierarquia.memoriaPrincipal.latencia);

        // Criar os níveis de cache
        Memoria nivelAnterior = memoriaPrincipal; // A memória principal é o nível mais baixo
        for (int i = hierarquia.caches.size() - 1; i >= 0; i--) {
            CacheConfig config = hierarquia.caches.get(i);
            nivelAnterior = new Cache(
                    config.nome,
                    config.latencia,
                    config.tamanhoLinha,
                    config.politicaEscrita,
                    config.associatividade,
                    config.conjuntos,
                    nivelAnterior);
        }

        // Conectar ao processador
        Processador cpu = new Processador(nivelAnterior);

        // Simulação de acessos
        int[] enderecos = { 0x1A2B3C4D, 0x1A2B3C4E, 0x3C4D5E6F };
        cpu.executarAcessos(enderecos);
    }
}