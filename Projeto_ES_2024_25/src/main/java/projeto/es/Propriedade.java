package projeto.es;

import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * The type Propriedade.
 */
public class Propriedade {
    private int objectId;
    private float par_id;
    private double shape_length;
    private double shape_area;
    private Geometry geometry;
    private int owner;
    private String freguesia;
    private String municipio;
    private String ilha;

    /**
     * Instantiates a new Propriedade.
     *
     * @param record the record
     * @throws ParseException the parse exception
     */
    public Propriedade(CSVRecord record) throws ParseException {
        WKTReader wktReader = new WKTReader();

        this.objectId = Integer.parseInt(record.get("OBJECTID"));
        this.par_id = Float.parseFloat(record.get("PAR_ID"));
        this.shape_length = Double.parseDouble(record.get("Shape_Length"));
        this.shape_area = Double.parseDouble(record.get("Shape_Area"));
        this.geometry = wktReader.read(record.get("geometry"));
        this.owner = Integer.parseInt(record.get("OWNER"));
        this.freguesia = record.get("Freguesia");
        this.municipio = record.get("Municipio");
        this.ilha = record.get("Ilha");
    }

    /**
     * Instantiates a new Propriedade.
     */
    public Propriedade() {
    }

    /**
     * Gets object id.
     *
     * @return the object id
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * Gets par id.
     *
     * @return the par id
     */
    public float getPar_id() {
        return par_id;
    }

    /**
     * Gets shape length.
     *
     * @return the shape length
     */
    public double getShape_length() {
        return shape_length;
    }

    /**
     * Gets shape area.
     *
     * @return the shape area
     */
    public double getShape_area() {
        return shape_area;
    }

    /**
     * Gets geometry.
     *
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Gets freguesia.
     *
     * @return the freguesia
     */
    public String getFreguesia() {
        return freguesia;
    }

    /**
     * Gets municipio.
     *
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Gets ilha.
     *
     * @return the ilha
     */
    public String getIlha() {
        return ilha;
    }

    /**
     * Sets object id.
     *
     * @param objectId the object id
     */
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    /**
     * Sets par id.
     *
     * @param par_id the par id
     */
    public void setPar_id(float par_id) {
        this.par_id = par_id;
    }

    /**
     * Sets shape length.
     *
     * @param shape_length the shape length
     */
    public void setShape_length(double shape_length) {
        this.shape_length = shape_length;
    }

    /**
     * Sets shape area.
     *
     * @param shape_area the shape area
     */
    public void setShape_area(double shape_area) {
        this.shape_area = shape_area;
    }

    /**
     * Sets geometry.
     *
     * @param geometry the geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(int owner) {
        this.owner = owner;
    }

    /**
     * Sets freguesia.
     *
     * @param freguesia the freguesia
     */
    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    /**
     * Sets municipio.
     *
     * @param municipio the municipio
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Sets ilha.
     *
     * @param ilha the ilha
     */
    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    @Override
    public String toString() {
        return
            "Propriedade{" +
            "objectId=" + objectId +
            ", par_id=" + par_id +
            ", shape_length=" + shape_length +
            ", shape_area=" + shape_area +
            ", geometry='" + geometry + '\'' +
            ", owner=" + owner +
            ", freguesia='" + freguesia + '\'' +
            ", municipio='" + municipio + '\'' +
            ", ilha='" + ilha + '\'' +
            '}';
    }
}
