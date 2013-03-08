
package nl.meine.gpf.entities;

/**
 *
 * @author Meine Tonen
 */
public enum GeoserviceType {
    WMS("WMS"),WMTS("WMTS");
    
    private String description;

    GeoserviceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static int fromName(String d) {
        for (GeoserviceType c: GeoserviceType.values()) {
            if (c.toString().equals(d)) {
                return c.ordinal();
            }
        }
        throw new IllegalArgumentException("No enum GeoserviceType found for name: " + d);
    }
    
    
}
