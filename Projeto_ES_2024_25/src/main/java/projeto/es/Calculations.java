package projeto.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Geometry;

public class Calculations {
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
}
