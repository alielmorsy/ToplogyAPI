package aie.topolog;

import java.io.IOException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws TopologyException, IOException {
        var api = API.createInstance();
        api.readTopology("t");
        api.writeTopology("top1","testWrite");
    }
}
