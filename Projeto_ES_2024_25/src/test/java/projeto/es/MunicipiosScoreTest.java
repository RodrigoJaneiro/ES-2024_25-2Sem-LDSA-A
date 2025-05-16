package projeto.es;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MunicipiosScoreTest {

    @Test
    public void testGetScoreForExistingMunicipios() {
        // Testa municípios com diferentes pontuações
        assertEquals(5, MunicipiosScore.getScore("Funchal"));
        assertEquals(5, MunicipiosScore.getScore("Calheta"));
        assertEquals(4, MunicipiosScore.getScore("Ponta do Sol"));
        assertEquals(3, MunicipiosScore.getScore("Câmara de Lobos"));
        assertEquals(2, MunicipiosScore.getScore("São Vicente"));
        assertEquals(1, MunicipiosScore.getScore("Santana"));
    }

    @Test
    public void testGetScoreForNonExistingMunicipio() {
        // Testa municípios que não existem no mapa
        assertEquals(0,MunicipiosScore.getScore("Município Inexistente"));
        assertEquals(0,MunicipiosScore.getScore(""));
    }

    @Test
    public void testGetScoreForBoundaryCases() {
        // Testa municípios com pontuação mínima e máxima
        assertEquals(1, MunicipiosScore.getScore("Santana"));  // Pontuação mínima
        assertEquals(5, MunicipiosScore.getScore("Funchal"));  // Pontuação máxima
    }

    @Test
    public void testGetScoreForAllMunicipios() {
        // Testa todos os municípios definidos na classe
        assertEquals(5, MunicipiosScore.getScore("Funchal"));
        assertEquals(5, MunicipiosScore.getScore("Calheta"));
        assertEquals(4, MunicipiosScore.getScore("Ponta do Sol"));
        assertEquals(4, MunicipiosScore.getScore("Ribeira Brava"));
        assertEquals(3, MunicipiosScore.getScore("Câmara de Lobos"));
        assertEquals(3, MunicipiosScore.getScore("Santa Cruz"));
        assertEquals(3, MunicipiosScore.getScore("Machico"));
        assertEquals(2, MunicipiosScore.getScore("São Vicente"));
        assertEquals(1, MunicipiosScore.getScore("Santana"));
    }
}