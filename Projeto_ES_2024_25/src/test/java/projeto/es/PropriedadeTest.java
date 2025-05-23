package projeto.es;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;

import static org.junit.Assert.*;


/**
 * The type Propriedade test.
 */
public class PropriedadeTest {

    /**
     * Test propriedade creation and getters.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPropriedadeCreationAndGetters() throws Exception {
        // Teste de criação e getters da classe Propriedade

        // Cria um objeto Geometry para teste
        WKTReader wktReader = new WKTReader();
        Geometry testGeometry = wktReader.read("POINT(10 20)");

        // Cria uma instância de Propriedade diretamente (sem CSVRecord)
        Propriedade propriedade = new Propriedade();
        propriedade.setObjectId(1);
        propriedade.setPar_id(123.45f);
        propriedade.setShape_length(100.50);
        propriedade.setShape_area(500.25);
        propriedade.setGeometry(testGeometry);
        propriedade.setOwner(5);
        propriedade.setFreguesia("FreguesiaTeste");
        propriedade.setMunicipio("MunicipioTeste");
        propriedade.setIlha("IlhaTeste");

        // Verifica se os valores foram atribuídos corretamente
        assertEquals(1, propriedade.getObjectId());
        assertEquals(123.45f, propriedade.getPar_id(), 0.001);
        assertEquals(100.50, propriedade.getShape_length(), 0.001);
        assertEquals(500.25, propriedade.getShape_area(), 0.001);
        assertSame(testGeometry, propriedade.getGeometry());
        assertEquals(5, propriedade.getOwner());
        assertEquals("FreguesiaTeste", propriedade.getFreguesia());
        assertEquals("MunicipioTeste", propriedade.getMunicipio());
        assertEquals("IlhaTeste", propriedade.getIlha());


        // Cria uma instância de Propriedade por outra
        Propriedade propriedade2 = new Propriedade(propriedade);

        // Verifica se os valores foram atribuídos corretamente
        assertEquals(1, propriedade2.getObjectId());
        assertEquals(123.45f, propriedade2.getPar_id(), 0.001);
        assertEquals(100.50, propriedade2.getShape_length(), 0.001);
        assertEquals(500.25, propriedade2.getShape_area(), 0.001);
        assertSame(testGeometry, propriedade2.getGeometry());
        assertEquals(5, propriedade2.getOwner());
        assertEquals("FreguesiaTeste", propriedade2.getFreguesia());
        assertEquals("MunicipioTeste", propriedade2.getMunicipio());
        assertEquals("IlhaTeste", propriedade2.getIlha());
    }

    /**
     * Test to string.
     *
     * @throws Exception the exception
     */
    @Test
    public void testToString() throws Exception {
        // Teste do toString da classe Propriedade

        // Cria um objeto Geometry para teste
        WKTReader wktReader = new WKTReader();
        Geometry testGeometry = wktReader.read("POINT(10 20)");

        // Cria uma propriedade de teste
        Propriedade propriedade = new Propriedade();
        propriedade.setObjectId(1);
        propriedade.setPar_id(123.45f);
        propriedade.setGeometry(testGeometry);

        // Verifica se o toString contém as informações básicas
        String result = propriedade.toString();
        assertTrue(result.contains("Propriedade{"));
        assertTrue(result.contains("objectId=1"));
        assertTrue(result.contains("par_id=123.45"));
        assertTrue(result.contains("geometry="));
        assertTrue(result.contains("POINT"));
    }
}
