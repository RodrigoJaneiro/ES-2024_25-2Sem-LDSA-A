package projeto.es;

import java.util.List;
import java.util.stream.Collectors;

public class AreaCalculator {

    public static double calcularAreaMedia(List<Propriedade> propriedades, String tipoArea, String nomeArea) {
        List<Propriedade> propriedadesFiltradas = propriedades.stream()
                .filter(prop -> filtraPorArea(prop, tipoArea, nomeArea))
                .collect(Collectors.toList());

        if (propriedadesFiltradas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma propriedade encontrada para a área especificada.");
        }

        return propriedadesFiltradas.stream()
                .mapToDouble(Propriedade::getShape_area)
                .average()
                .orElse(0);
    }

    public static boolean filtraPorArea(Propriedade prop, String tipoArea, String nomeArea) {
        switch (tipoArea) {
            case "Freguesia": return prop.getFreguesia().equalsIgnoreCase(nomeArea);
            case "Municipio": return prop.getMunicipio().equalsIgnoreCase(nomeArea);
            case "Ilha": return prop.getIlha().equalsIgnoreCase(nomeArea);
            default: return false;
        }
    }
}