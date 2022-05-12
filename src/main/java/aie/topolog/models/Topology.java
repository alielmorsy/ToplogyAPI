package aie.topolog.models;

import java.util.ArrayList;
import java.util.List;

public class Topology {
    private String id;
    private transient List<Component> components = new ArrayList<>();

    public Topology() {

    }

    public Topology(String id, List<Component> components) {
        this.id = id;
        this.components = components;
    }

    public String id() {
        return id;
    }

    public Topology setId(String id) {
        this.id = id;
        return this;
    }

    public List<Component> components() {
        return components;
    }

    public Topology setComponents(List<Component> components) {
        this.components = components;
        return this;
    }
@Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("{").append("Topology: ").append(id).append(",");
        builder.append("Components: ").append("[");
        for (var c : components) {
            builder.append(c.toString()).append(',');
        }
        builder.append("]}");
return builder.toString();
    }
}
