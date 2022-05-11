package aie.topolog;

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
     * @param topologyID
     * @param fileName
     * @return
     * @throws TopologyException
     */
    boolean writeTopology(String topologyID, String fileName) throws TopologyException;

}
