package projeto.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Geometry;

/**
 * The type Calculations.
 */
public class Calculations {
    /**
     * Encontrar vizinhos list.
     *
     * @param propriedades the propriedades
     * @return the list
     */
    public static List<List<Integer>> encontrarVizinhos(List<Propriedade> propriedades) {
        Set<Set<Integer>> paresUnicos = new HashSet<>();

        propriedades.forEach(propriedade -> {
            int id = propriedade.getObjectId();
            Geometry geometria = propriedade.getGeometry();

            propriedades.forEach(p -> {
                if (id != p.getObjectId() && geometria.touches(p.getGeometry())) {
                    paresUnicos.add(new HashSet<>(Arrays.asList(id, p.getObjectId())));
                }
            });
        });

        // Converter para List<List<Integer>>
        return paresUnicos.stream()
                .map(ArrayList::new)
                .collect(Collectors.toList());
    }
    /**
     * Agrupa propriedades adjacentes do mesmo proprietário
     * @param propriedades Lista de propriedades
     * @return Lista de propriedades agrupadas
     */
    public static List<Propriedade> agruparPropriedadesAdjacentes(List<Propriedade> propriedades) {
        List<Propriedade> propriedadesAgrupadas = new ArrayList<>();
        Set<Integer> propriedadesProcessadas = new HashSet<>();

        for (Propriedade prop : propriedades) {
            if (!propriedadesProcessadas.contains(prop.getObjectId())) {
                // Encontra todas as propriedades adjacentes do mesmo proprietário
                Set<Propriedade> grupo = new HashSet<>();
                encontrarGrupoAdjacente(prop, propriedades, grupo, propriedadesProcessadas);

                // Cria uma nova propriedade combinada
                if (!grupo.isEmpty()) {
                    propriedadesAgrupadas.add(combinarPropriedades(grupo));
                }
            }
        }
        return propriedadesAgrupadas;
    }

    private static void encontrarGrupoAdjacente(Propriedade atual, List<Propriedade> todas,
                                                Set<Propriedade> grupo, Set<Integer> processadas) {
        if (processadas.contains(atual.getObjectId())) return;

        grupo.add(atual);
        processadas.add(atual.getObjectId());

        for (Propriedade p : todas) {
            if (!processadas.contains(p.getObjectId()) &&
                    p.getOwner() == atual.getOwner() &&
                    atual.getGeometry().intersects(p.getGeometry())) {
                encontrarGrupoAdjacente(p, todas, grupo, processadas);
            }
        }
    }

    private static Propriedade combinarPropriedades(Set<Propriedade> grupo) {
        Propriedade combinada = new Propriedade();
        combinada.setOwner(grupo.iterator().next().getOwner());

        double areaTotal = grupo.stream().mapToDouble(Propriedade::getShape_area).sum();
        combinada.setShape_area(areaTotal);

        // Aqui você precisaria combinar as geometrias também
        // Esta é uma simplificação - na prática você precisaria usar JTS para unir as geometrias
        combinada.setGeometry(grupo.iterator().next().getGeometry());

        return combinada;
    }
}
