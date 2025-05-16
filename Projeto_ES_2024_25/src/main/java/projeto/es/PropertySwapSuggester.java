package projeto.es;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertySwapSuggester {

    /**
     * Calcula a área média das propriedades agrupadas para um proprietário específico.
     * (Lógica similar à alínea 5)
     */
    private static double calculateAverageGroupedAreaForOwner(int ownerId, List<Propriedade> allProperties) {
        List<Propriedade> ownerProperties = allProperties.stream()
                .filter(p -> p.getOwner() == ownerId)
                .collect(Collectors.toList());

        if (ownerProperties.isEmpty()) {
            return 0.0;
        }

        List<Propriedade> groupedProperties = Calculations.agruparPropriedadesAdjacentes(ownerProperties);

        if (groupedProperties.isEmpty()) {
            return 0.0;
        }

        return groupedProperties.stream()
                .mapToDouble(Propriedade::getShape_area)
                .average()
                .orElse(0.0);
    }

    public static List<SwapSuggestion> generateSuggestions(List<Propriedade> allOriginalProperties, int exerciseNumber) {
        List<SwapSuggestion> suggestions = new ArrayList<>();

        List<Propriedade> allPropsCopyForSimulation = allOriginalProperties.stream()
                .map(Propriedade::new)
                .collect(Collectors.toList());

        Map<Integer, List<Propriedade>> propsByOwner = allOriginalProperties.stream()
                .collect(Collectors.groupingBy(Propriedade::getOwner));

        List<Integer> ownerIds = new ArrayList<>(propsByOwner.keySet());

        for (int i = 0; i < ownerIds.size(); i++) {
            for (int j = i + 1; j < ownerIds.size(); j++) {
                int owner1Id = ownerIds.get(i);
                int owner2Id = ownerIds.get(j);

                List<Propriedade> owner1Props = propsByOwner.get(owner1Id);
                List<Propriedade> owner2Props = propsByOwner.get(owner2Id);

                double initialAvgAreaOwner1 = calculateAverageGroupedAreaForOwner(owner1Id, allOriginalProperties);
                double initialAvgAreaOwner2 = calculateAverageGroupedAreaForOwner(owner2Id, allOriginalProperties);

                for (Propriedade p1Original : owner1Props) {
                    for (Propriedade p2Original : owner2Props) {

                        Propriedade p1Sim = allPropsCopyForSimulation.stream()
                                .filter(p -> p.getObjectId() == p1Original.getObjectId()).findFirst().orElse(null);
                        Propriedade p2Sim = allPropsCopyForSimulation.stream()
                                .filter(p -> p.getObjectId() == p2Original.getObjectId()).findFirst().orElse(null);

                        if (p1Sim == null || p2Sim == null) continue; // Segurança

                        // --- SIMULAR TROCA ---
                        p1Sim.setOwner(owner2Id);
                        p2Sim.setOwner(owner1Id);

                        // Calcular estado pós-troca usando allPropsCopyForSimulation
                        double finalAvgAreaOwner1 = calculateAverageGroupedAreaForOwner(owner1Id, allPropsCopyForSimulation);
                        double finalAvgAreaOwner2 = calculateAverageGroupedAreaForOwner(owner2Id, allPropsCopyForSimulation);

                        p1Sim.setOwner(owner1Id);
                        p2Sim.setOwner(owner2Id);

                        if (finalAvgAreaOwner1 > initialAvgAreaOwner1 && finalAvgAreaOwner2 > initialAvgAreaOwner2) {

                            double areaDiff = Math.abs(p1Original.getShape_area() - p2Original.getShape_area());

                            double avgAreaOfPair = (p1Original.getShape_area() + p2Original.getShape_area()) / 2.0;
                            double normalizedAreaDiff = (avgAreaOfPair > 0.01) ? areaDiff / avgAreaOfPair : areaDiff; // Evitar div por zero
                            double potentialScore = 1.0 / (1.0 + normalizedAreaDiff) * 100;

                            switch (exerciseNumber){
                                case 7:
                                    potentialScore -= Math.pow(Math.abs(FreguesiaScore.getScore(p1Original.getFreguesia()) - FreguesiaScore.getScore(p2Original.getFreguesia())),1.5);
                                    potentialScore -= Math.pow(Math.abs(MunicipiosScore.getScore(p1Original.getMunicipio()) - MunicipiosScore.getScore(p2Original.getMunicipio())),1.5);
                                    break;
                            }

                            suggestions.add(new SwapSuggestion(
                                    p1Original, p2Original, owner1Id, owner2Id,
                                    potentialScore,
                                    initialAvgAreaOwner1, finalAvgAreaOwner1,
                                    initialAvgAreaOwner2, finalAvgAreaOwner2
                            ));
                        }
                    }
                }
            }
        }

        // Ordenar sugestões pelo score combinado (as melhores primeiro)
        suggestions.sort(Comparator.comparingDouble(SwapSuggestion::getPotentialScore).reversed());

        // Retornar, por exemplo, as top 10 ou todas se forem poucas
        return suggestions.stream().limit(100).collect(Collectors.toList()); // Limitar para não sobrecarregar a UI
    }
}