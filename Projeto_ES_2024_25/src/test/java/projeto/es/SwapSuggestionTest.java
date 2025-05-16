package projeto.es;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SwapSuggestionTest {

    @Test
    void testConstructorAndGetters() {
        // Criar propriedades mock
        Propriedade prop1 = new Propriedade(1, 1.0f, 10.0, 100.0, null, 101, "FreguesiaA", "MunicipioA", "IlhaA");
        Propriedade prop2 = new Propriedade(2, 2.0f, 20.0, 200.0, null, 102, "FreguesiaB", "MunicipioB", "IlhaB");

        // Criar sugestão de troca
        SwapSuggestion suggestion = new SwapSuggestion(
                prop1, prop2,
                101, 102,
                85.5,
                150.0, 160.0,
                250.0, 240.0
        );

        // Testar getters
        assertEquals(prop1, suggestion.getProp1());
        assertEquals(prop2, suggestion.getProp2());
        assertEquals(101, suggestion.getOwner1Id());
        assertEquals(102, suggestion.getOwner2Id());
        assertEquals(85.5, suggestion.getPotentialScore(), 0.001);
        assertEquals(150.0, suggestion.getOwner1InitialAvgArea(), 0.001);
        assertEquals(160.0, suggestion.getOwner1FinalAvgArea(), 0.001);
        assertEquals(250.0, suggestion.getOwner2InitialAvgArea(), 0.001);
        assertEquals(240.0, suggestion.getOwner2FinalAvgArea(), 0.001);
    }

}