package aie.topolog;

import aie.topolog.models.Component;
import aie.topolog.models.NMOS;
import aie.topolog.models.Resistor;
import aie.topolog.models.Topology;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIImplementation implements API {

    private transient final Map<String, Topology> topologyMap = new HashMap<>();

    @Override
    public boolean readTopology(String fileName) throws TopologyException {
        try {
            byte[] b = Files.readAllBytes(Path.of(fileName));
            return jsonToTopology(new String(b));
        } catch (IOException e) {
            return false;
        }


    }

    @Override
    public void writeTopology(String topologyID, String fileName) throws TopologyException, IOException {
        var folder = new File("export");
        if (!folder.exists() || !folder.isDirectory())
            folder.mkdir();
        var topology = getTopologyOrThrow(topologyID);
        JSONObject t = new JSONObject();
        t.put("id", topology.id());
        var arr = new JSONArray();
        for (var c : topology.components()) {
            arr.put(c.toJson());
        }
        t.put("components", arr);
        Files.writeString(Path.of("out", fileName), t.toString());
    }

    @Override
    public List<Topology> queryTopologies() {
        return new ArrayList<>(topologyMap.values());
    }

    @Override
    public Topology getTopology(String id) {
        if (topologyMap.containsKey(id)) {
            return topologyMap.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteTopology(String topologyID) {
        if (!topologyMap.containsKey(topologyID)) return false;
        topologyMap.remove(topologyID);
        return true;
    }

    @Override
    public List<Component> getTopologyComponent(String topologyID) throws TopologyException {
        return getTopologyOrThrow(topologyID).components();
    }

    @Override
    public List<Component> getTopologyByConnectedNodes(String topologyID, String nodeID) throws TopologyException {
        ArrayList<Component> result = new ArrayList<>();
        var topology = getTopologyOrThrow(topologyID);
        for (Component component : topology.components()) {
            if (component.netList().containsKey(nodeID))
                result.add(component);
        }
        return result.isEmpty() ? null : result;
    }

    private boolean jsonToTopology(String text) throws TopologyException {
        JSONObject o;
        try {
            o = new JSONObject(text);
            var topology = new Topology();
            topology.setId(o.getString("id"));
            var components = o.getJSONArray("components");
            List<Component> componentsArr = new ArrayList<>();
            for (int i = 0; i < components.length(); i++) {
                var component = components.getJSONObject(i);
                switch (component.getString("type")) {
                    case "resistor":
                        componentsArr.add(handleResistorComponent(component));
                        break;
                    case "nmos":
                        componentsArr.add(handleNMOS(component));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + component.getString("type"));
                }
            }
            topology.setComponents(componentsArr);
            topologyMap.put(topology.id(), topology);
            return true;
        } catch (JSONException e) {
            throw new TopologyException("Invalid Topology File", e);
        }


    }

    private Component handleResistorComponent(JSONObject component) {
        Resistor resistor = new Resistor();
        var netList = component.getJSONObject("netlist");
        resistor.setNetList(netList.toMap());
        JSONObject props = component.getJSONObject("resistance");
        resistor.setProperties(props.toMap());

        resistor.setId(component.getString("id"));

        return resistor;
    }

    private Component handleNMOS(JSONObject component) {
        NMOS nmos = new NMOS();
        nmos.setId(component.getString("id"));
        JSONObject netList = component.getJSONObject("netlist");

        nmos.setNetList(netList.toMap());

        var props = component.getJSONObject("m(l)");
        nmos.setProperties(props.toMap());

        return nmos;
    }


    private Topology getTopologyOrThrow(String topologyID) throws TopologyException {
        if (!topologyMap.containsKey(topologyID)) {
            throw new TopologyException("No Topologies With ID: " + topologyID);
        }
        return topologyMap.get(topologyID);
    }
}
