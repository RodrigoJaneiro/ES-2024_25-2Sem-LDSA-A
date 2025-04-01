package projeto.es;

import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class Propriedade {
    private int objectId;
    private float par_id;
    private int par_num;
    private double shape_length;
    private double shape_area;
    private Geometry geometry;
    private int owner;
    private String freguesia;
    private String municipio;
    private String ilha;

    public Propriedade(CSVRecord record) throws ParseException {
        WKTReader wktReader = new WKTReader();

        this.objectId = Integer.parseInt(record.get("OBJECTID"));
        this.par_id = Float.parseFloat(record.get("PAR_ID"));
        //this.par_num = Integer.parseInt(record.get("PAR_NUM"));
        this.shape_length = Double.parseDouble(record.get("Shape_Length"));
        this.shape_area = Double.parseDouble(record.get("Shape_Area"));
        this.geometry = wktReader.read(record.get("geometry"));
        this.owner = Integer.parseInt(record.get("OWNER"));
        this.freguesia = record.get("Freguesia");
        this.municipio = record.get("Municipio");
        this.ilha = record.get("Ilha");
    }

    public int getObjectId() {
        return objectId;
    }

    public float getPar_id() {
        return par_id;
    }

    public int getPar_num() {
        return par_num;
    }

    public double getShape_length() {
        return shape_length;
    }

    public double getShape_area() {
        return shape_area;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public int getOwner() {
        return owner;
    }

    public String getFreguesia() {
        return freguesia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getIlha() {
        return ilha;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public void setPar_id(float par_id) {
        this.par_id = par_id;
    }

    public void setPar_num(int par_num) {
        this.par_num = par_num;
    }

    public void setShape_length(double shape_length) {
        this.shape_length = shape_length;
    }

    public void setShape_area(double shape_area) {
        this.shape_area = shape_area;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    @Override
    public String toString() {
        return
            "Propriedade{" +
            "objectId=" + objectId +
            ", par_id=" + par_id +
            ", par_num=" + par_num +
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
