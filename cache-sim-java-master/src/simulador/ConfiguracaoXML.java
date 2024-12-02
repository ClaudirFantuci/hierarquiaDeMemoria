package simulador;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;

public class ConfiguracaoXML {
    public static HierarquiaMemoria carregarConfiguracao(String caminhoArquivo) {
        HierarquiaMemoria hierarquia = new HierarquiaMemoria();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(caminhoArquivo);
            doc.getDocumentElement().normalize();

            NodeList elements = doc.getElementsByTagName("element");
            for (int i = 0; i < elements.getLength(); i++) {
                Node node = elements.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String type = element.getAttribute("type");

                    if (type.equals("main_memory")) {
                        MemoriaConfig memoria = new MemoriaConfig();
                        memoria.nome = element.getElementsByTagName("name").item(0).getTextContent();
                        memoria.latencia = Integer
                                .parseInt(element.getElementsByTagName("latency").item(0).getTextContent());
                        hierarquia.memoriaPrincipal = memoria;
                    } else if (type.equals("cache")) {
                        CacheConfig cache = new CacheConfig();
                        cache.nome = element.getElementsByTagName("name").item(0).getTextContent();
                        cache.conjuntos = Integer
                                .parseInt(element.getElementsByTagName("sets").item(0).getTextContent());
                        cache.associatividade = Integer
                                .parseInt(element.getElementsByTagName("assoc").item(0).getTextContent());
                        cache.tamanhoLinha = Integer
                                .parseInt(element.getElementsByTagName("linesize").item(0).getTextContent());
                        cache.politicaEscrita = element.getElementsByTagName("writepolicy").item(0).getTextContent();
                        cache.latencia = Integer
                                .parseInt(element.getElementsByTagName("latency").item(0).getTextContent());
                        hierarquia.caches.add(cache);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hierarquia;
    }
}