package projeto.es;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FreguesiaScoreTest {

    @Test
    public void testGetScoreForExistingFreguesias() {
        assertEquals(3, FreguesiaScore.getScore("Água de Pena"));
        assertEquals(5, FreguesiaScore.getScore("Calheta"));
        assertEquals(4, FreguesiaScore.getScore("Santo António"));
        assertEquals(5, FreguesiaScore.getScore("São Martinho"));
        assertEquals(1, FreguesiaScore.getScore("Fajã do Penedo"));
        assertEquals(4, FreguesiaScore.getScore("Vila Baleira"));
    }

    @Test
    public void testGetScoreForNonExistingFreguesia() {
        assertEquals(0, FreguesiaScore.getScore("Freguesia Inexistente"));
        assertEquals(0, FreguesiaScore.getScore(""));
    }

    @Test
    public void testGetScoreForBoundaryCases() {
        // Testa freguesias com pontuação mínima e máxima
        assertEquals(1, FreguesiaScore.getScore("Terra Chã"));
        assertEquals(5, FreguesiaScore.getScore("Santa Maria Maior"));
        assertEquals(5, FreguesiaScore.getScore("Se"));
    }

    @Test
    public void testGetScoreForAllFreguesias() {
        // Testa uma amostra representativa de todas as freguesias
        assertEquals(2, FreguesiaScore.getScore("Água de São Jorge"));
        assertEquals(4, FreguesiaScore.getScore("Arco da Calheta"));
        assertEquals(3, FreguesiaScore.getScore("Camacha"));
        assertEquals(4, FreguesiaScore.getScore("Caniço"));
        assertEquals(2, FreguesiaScore.getScore("Curral das Freiras"));
        assertEquals(3, FreguesiaScore.getScore("Machico"));
        assertEquals(4, FreguesiaScore.getScore("Ponta do Sol"));
        assertEquals(3, FreguesiaScore.getScore("Porto da Cruz"));
        assertEquals(4, FreguesiaScore.getScore("Ribeira Brava"));
        assertEquals(3, FreguesiaScore.getScore("Santa Cruz"));
        assertEquals(3, FreguesiaScore.getScore("São Vicente"));
        assertEquals(3, FreguesiaScore.getScore("Canhas"));
        assertEquals(2, FreguesiaScore.getScore("Paul do Mar"));
    }
}