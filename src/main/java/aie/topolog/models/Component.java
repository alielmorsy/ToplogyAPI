package aie.topolog.models;


import org.json.JSONObject;


import java.util.Locale;
import java.util.Map;

public class Component {

    protected String id;
    protected ComponentType type;
    protected transient Map<String, Object> netList;
    protected transient Map<String, Object> properties;


    public Component setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public Component(ComponentType componentType) {
        this.type = componentType;
    }

    public Component setId(String id) {
        this.id = id;
        return this;
    }

    public Component setNetList(Map<String, Object> netList) {
        this.netList = netList;
        return this;
    }

    public ComponentType type() {
        return type;
    }

    public String id() {
        return id;
    }

    public Map<String, Object> netList() {
        return netList;
    }

    public Map<String, Object> properties() {
        return properties;
    }

    /**
     * A function to create a json object contains all component properties
     *
     * @return returns a {@code JSONObject} instance
     */
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("type", type.toString().toLowerCase(Locale.ROOT));

        object.put("netlist", netList);
        var type = this.type == ComponentType.Resistor ? "resistance" : "m(l)";
        object.put(type, properties);
        return object;
    }

    public enum ComponentType {
        Resistor,
        NMOS;

    }
}
