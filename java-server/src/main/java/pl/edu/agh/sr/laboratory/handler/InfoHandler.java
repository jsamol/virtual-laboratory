package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.laboratory.Server;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Info;

import java.util.List;
import java.util.Map;

public class InfoHandler implements Info.Iface {
    private static InfoHandler instance = null;

    private InfoHandler() {

    }

    public static InfoHandler getInstance() {
        if (instance == null) {
            instance = new InfoHandler();
        }

        return instance;
    }

    @Override
    public List<DeviceStruct> getDevices() throws TException {
        System.out.println("Info: getDevices()");
        return Server.getDevices();
    }

    @Override
    public Map<String, Integer> getNumbers() throws TException {
        System.out.println("Info: getNumbers()");
        return Server.getNumbers();
    }
}
