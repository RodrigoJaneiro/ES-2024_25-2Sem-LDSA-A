package projeto.es;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculationsTest {
    private final GeometryFactory geometryFactory = new GeometryFactory();

    // Método auxiliar para criar propriedades com todos os parâmetros
    private Propriedade createFullPropriedade(int objectId, int owner, Geometry geometry, double area) {
        return new Propriedade(
                objectId,      // objectId
                1.0f,          // par_id
                10.0,         // shape_length
                area,          // shape_area
                geometry,      // geometry
                owner,         // owner
                "Freguesia",   // freguesia
                "Municipio",   // municipio
                "Ilha"        // ilha
        );
    }

    // Método auxiliar para criar geometrias quadradas
    private Polygon createSquare(double x, double y, double size) {
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(x, y),
                new Coordinate(x + size, y),
                new Coordinate(x + size, y + size),
                new Coordinate(x, y + size),
                new Coordinate(x, y)
        };
        return geometryFactory.createPolygon(coordinates);
    }

    @Test
    void testEncontrarVizinhos() {
        // Criar propriedades com geometrias que se tocam
        Propriedade p1 = createFullPropriedade(1, 100, createSquare(0, 0, 1), 50.0);
        Propriedade p2 = createFullPropriedade(2, 100, createSquare(1, 0, 1), 75.0);
        Propriedade p3 = createFullPropriedade(3, 100, createSquare(2, 0, 1), 25.0); // Vizinho de p2
        Propriedade p4 = createFullPropriedade(4, 100, createSquare(10, 10, 1), 100.0); // Isolada

        List<Propriedade> propriedades = Arrays.asList(p1, p2, p3, p4);
        List<List<Integer>> vizinhos = Calculations.encontrarVizinhos(propriedades);

        assertEquals(2, vizinhos.size());
        assertTrue(vizinhos.contains(Arrays.asList(1, 2)));
        assertTrue(vizinhos.contains(Arrays.asList(2, 3)));
    }

    @Test
    void testEncontrarVizinhosProprietarios() {
        // Criar propriedades com diferentes proprietários
        Propriedade p1 = createFullPropriedade(1, 100, createSquare(0, 0, 1), 50.0);
        Propriedade p2 = createFullPropriedade(2, 200, createSquare(1, 0, 1), 75.0); // Vizinho com dono diferente
        Propriedade p3 = createFullPropriedade(3, 200, createSquare(2, 0, 1), 25.0); // Vizinho com mesmo dono
        Propriedade p4 = createFullPropriedade(4, 300, createSquare(0, 1, 1), 100.0); // Vizinho com dono diferente

        List<Propriedade> propriedades = Arrays.asList(p1, p2, p3, p4);
        List<List<Integer>> vizinhos = Calculations.encontrarVizinhosPoroprietarios(propriedades);

        assertEquals(3, vizinhos.size());
        assertTrue(vizinhos.contains(Arrays.asList(100, 200)));
        assertTrue(vizinhos.contains(Arrays.asList(200, 300)));
        assertTrue(vizinhos.contains(Arrays.asList(100, 300)));
    }

    @Test
    void testAgruparPropriedadesAdjacentes() {
        // Criar propriedades adjacentes do mesmo proprietário
        Propriedade p1 = createFullPropriedade(1, 100, createSquare(0, 0, 1), 50.0);
        Propriedade p2 = createFullPropriedade(2, 100, createSquare(1, 0, 1), 75.0); // Adjacente
        Propriedade p3 = createFullPropriedade(3, 100, createSquare(0, 1, 1), 25.0); // Adjacente
        Propriedade p4 = createFullPropriedade(4, 200, createSquare(2, 0, 1), 100.0); // Dono diferente
        Propriedade p5 = createFullPropriedade(5, 100, createSquare(10, 10, 1), 150.0); // Isolada

        List<Propriedade> propriedades = Arrays.asList(p1, p2, p3, p4, p5);
        List<Propriedade> agrupadas = Calculations.agruparPropriedadesAdjacentes(propriedades);

        assertEquals(3, agrupadas.size());
    }

    @Test
    void testAgruparPropriedadesSemAdjacentes() {
        // Propriedades não adjacentes
        Propriedade p1 = createFullPropriedade(1, 100, createSquare(0, 0, 1), 50.0);
        Propriedade p2 = createFullPropriedade(2, 100, createSquare(10, 10, 1), 75.0);
        Propriedade p3 = createFullPropriedade(3, 100, createSquare(20, 20, 1), 25.0);

        List<Propriedade> propriedades = Arrays.asList(p1, p2, p3);
        List<Propriedade> agrupadas = Calculations.agruparPropriedadesAdjacentes(propriedades);

        assertEquals(3, agrupadas.size()); // Nenhuma foi agrupada
    }

    @Test
    void testAgruparPropriedadesVazia() {
        List<Propriedade> propriedades = Arrays.asList();
        List<Propriedade> agrupadas = Calculations.agruparPropriedadesAdjacentes(propriedades);

        assertTrue(agrupadas.isEmpty());
    }
}