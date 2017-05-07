package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.MobileRoboticPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobileRoboticPlatformHandler implements MobileRoboticPlatform.Iface {
    private DeviceStruct deviceInfo;

    private boolean isAvailable;

    public MobileRoboticPlatformHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Mobile Robotic Platform");

        this.isAvailable = true;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": getStatus()");
        if (isAvailable) {
            return Status.AVAILABLE;
        }
        else {
            return Status.NOT_AVAILABLE;
        }
    }

    @Override
    public List<String> getAvailableCommands() throws TException {
        String[] commands = {
                "\\forwards [distance]",
                "\\backwards [distance]",
                "\\right [angle]",
                "\\left [angle]",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws TException {
        System.out.println("Mobile Robotic Platform#" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        return "Mobile Robotic Platform #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Mobile Robotic Platform #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public String goForwards(int distance) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goForwards()");
        return "Mobile Robotic Platform #" + deviceInfo.getId() + " moved forwards for " + distance + " meters.";
    }

    @Override
    public String goBackwards(int distance) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goBackwards()");
        return "Mobile Robotic Platform #" + deviceInfo.getId() + " moved backwards for " + distance + " meters.";
    }

    @Override
    public String goRight(int angle) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goRight()");
        return "Mobile Robotic Platform #" + deviceInfo.getId() + " moved right for " + angle + " degrees.";
    }

    @Override
    public String goLeft(int angle) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goLeft()");
        return "Mobile Robotic Platform #" + deviceInfo.getId() + " moved left for " + angle + " degrees.";
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
