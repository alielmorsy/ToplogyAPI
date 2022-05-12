package aie.topolog;

import aie.topolog.models.Component;
import aie.topolog.models.Topology;

import java.io.IOException;
import java.util.List;

public interface API {

    /**
     * To Read The topology from specific file and store in memory
     *
     * @param fileName the file name contains topology JSON
     * @return true if operation success otherwise false
     * @throws TopologyException if current topology has same id of another one in memory
     */

    boolean readTopology(String fileName) throws TopologyException;

    /**
     * To write topology from memory to file
     *
     * @param topologyID ID of topology stored in memory
     * @param fileName   name of the file where the data be stored
     * @throws TopologyException if there is no topologies with that ID
     * @throws IOException       if failed to save file or there is file with same name
     */
    void writeTopology(String topologyID, String fileName) throws TopologyException, IOException;

    /**
     * Return List of topologies stored in memory
     *
     * @return List of Topologies <pre>{@code List<Topology>}</pre>
     */
    List<Topology> queryTopologies();

    /**
     * Get A topology based on id
     * @param id of the topology
     * @return Topology instance of that id
     */
    Topology getTopology(String id) ;
    /**
     * Delete Specific Topology from memory based on ID
     *
     * @param topologyID ID of the topology
     * @return true if deleted otherwise is false
     */
    boolean deleteTopology(String topologyID);

    /**
     * Return list of all component stored under specific topology
     *
     * @param topologyID ID of topology we're searching about
     * @return List of Components in our topology
     * @throws TopologyException if there is no topologies' wit such id in memory
     */
    List<Component> getTopologyComponent(String topologyID) throws TopologyException;

    /**
     * @param topologyID The Topology ID To Retrieve its Devices
     * @param nodeID     The Node ID To Get Devices Connected To It
     * @return List of The Desired Topology's Devices And Connected With A Specific Node
     * @throws TopologyException if there is no topologies' wit such id in memory
     */

    List<Component> getTopologyByConnectedNodes(String topologyID, String nodeID) throws TopologyException;

    public static API createInstance() {
        return new APIImplementation();
    }
}
