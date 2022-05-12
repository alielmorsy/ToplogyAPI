package aie.topolog;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws TopologyException, IOException {
        var api = API.createInstance();
        api.readTopology("test");
        api.writeTopology("top1","testWrite");
    }
}
