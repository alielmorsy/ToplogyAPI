package aie.topolog;

import org.junit.jupiter.api.Assertions;

public class APITest {

    @org.junit.jupiter.api.Test
    public void testRead() throws TopologyException {
        var api = API.createInstance();
        var t = api.getTopology("top1");
        Assertions.assertNull(t);
        api.readTopology("test");
        t = api.getTopology("top1");
        Assertions.assertNotNull(t);
    }

    @org.junit.jupiter.api.Test
    public void testDelete() throws TopologyException {
        var api = API.createInstance();
        api.readTopology("test");
        var t = api.getTopology("top1");
        Assertions.assertNotNull(t);
        api.deleteTopology(t.id());
        t = api.getTopology("top1");
        Assertions.assertNull(t);
    }
}
