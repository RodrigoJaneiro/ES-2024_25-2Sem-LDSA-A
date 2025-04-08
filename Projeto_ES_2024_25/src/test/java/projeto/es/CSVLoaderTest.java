package projeto.es;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Csv loader test.
 */
public class CSVLoaderTest {

    /**
     * Test load propriedades success.
     *
     * @throws Exception the exception
     */
    @Test
    public void testLoadPropriedadesSuccess() throws Exception {
        // Teste com arquivo CSV válido
        List<Propriedade> propriedades = CSVLoader.LoadPropriedades("test_data");

        assertEquals(2, propriedades.size());

        // Verificar a primeira propriedade
        Propriedade p1 = propriedades.get(0);
        assertEquals(1, p1.getObjectId());
        assertEquals(12345.0f, p1.getPar_id());
        assertEquals(10.5, p1.getShape_length(), 0.001);
        assertEquals(20.3, p1.getShape_area(), 0.001);
        assertEquals(99, p1.getOwner());
        assertEquals("Teste Freguesia", p1.getFreguesia());
        assertNotNull(p1.getGeometry());

        // Verificar a segunda propriedade
        Propriedade p2 = propriedades.get(1);
        assertEquals(2, p2.getObjectId());
        assertEquals(67890.0f, p2.getPar_id());
    }

    /**
     * Test load propriedades file not found.
     */
    @Test
    public void testLoadPropriedadesFileNotFound() {
        // Teste com arquivo inexistente
        assertThrows(IOException.class, () -> {
            CSVLoader.LoadPropriedades("arquivo_inexistente");
        });
    }

    /**
     * Test load propriedades empty file.
     *
     * @throws Exception the exception
     */
    @Test
    public void testLoadPropriedadesEmptyFile() throws Exception {
        // Teste com arquivo sem informação
        List<Propriedade> propriedades = CSVLoader.LoadPropriedades("empty");
        assertTrue(propriedades.isEmpty());
    }
}