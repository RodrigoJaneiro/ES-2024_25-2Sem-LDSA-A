package projeto.es;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por calcular a área média das propriedades de uma área geográfica/administrativa.
 */
public class AreaCalculator {

    /**
     * Calcula a área média das propriedades de uma área geográfica/administrativa.
     *
     * @param propriedades Lista de propriedades.
     * @param tipoArea Tipo de área geográfica ("Freguesia", "Municipio", "Ilha").
     * @param nomeArea Nome da área geográfica (ex: "Funchal" para Município).
     * @return A área média das propriedades na área especificada.
     */
    public static double calcularAreaMedia(List<Propriedade> propriedades, String tipoArea, String nomeArea) {
        List<Propriedade> propriedadesFiltradas = propriedades.stream()
                .filter(prop -> {
                    switch (tipoArea) {
                        case "Freguesia":
                            return prop.getFreguesia().equalsIgnoreCase(nomeArea);
                        case "Municipio":
                            return prop.getMunicipio().equalsIgnoreCase(nomeArea);
                        case "Ilha":
                            return prop.getIlha().equalsIgnoreCase(nomeArea);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());

        if (propriedadesFiltradas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma propriedade encontrada para a área especificada.");
        }

        double somaAreas = propriedadesFiltradas.stream()
                .mapToDouble(Propriedade::getShape_area)
                .sum();

        return somaAreas / propriedadesFiltradas.size();
    }
}