package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.InvalidOperationException;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.AdvancedMobileRoboticPlatform;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.MovementType;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.OrderStruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedMobileRoboticPlatformHandler implements AdvancedMobileRoboticPlatform.Iface {
    private DeviceStruct deviceInfo;

    private boolean isAvailable;

    public AdvancedMobileRoboticPlatformHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Advanced Mobile Robotic Platform");

        this.isAvailable = true;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": getStatus()");
        if (isAvailable) {
            return Status.AVAILABLE;
        }
        else {
            return Status.NOT_AVAILABLE;
        }
    }

    @Override
    public List<String> getAvailableCommands() throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": getAvailableCommands()");
        String[] commands = {
                "\\do [list of commands]]",
                "\\forwards [distance]",
                "\\backwards [distance]",
                "\\right [angle]",
                "\\left [angle]",
                "\\commands",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws TException {
        System.out.println("Advanced Mobile Robotic Platform#" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        return "Advanced Mobile Robotic Platform #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Advanced Mobile Robotic Platform #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public List<String> doSequenceOfMovements(List<OrderStruct> orders) throws InvalidOperationException, TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": doSequenceOfMovements()");
        List<String> result = new ArrayList<>();
        for (OrderStruct order : orders) {
            MovementType type = order.getMovementType();
            int param = order.getParam();
            switch (type) {
                case FORWARDS:
                    result.add(goForwards(param));
                    break;
                case BACKWARDS:
                    result.add(goBackwards(param));
                    break;
                case LEFT:
                    result.add(goLeft(param));
                    break;
                case RIGHT:
                    result.add(goRight(param));
                    break;
                default:
                    InvalidOperationException e = new InvalidOperationException();
                    e.setWhatOp(type.getValue());
                    e.setWhy("Unknown operation.");
                    throw e;
            }
        }
        return result;
    }

    @Override
    public String goForwards(int distance) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goForwards()");
        return "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved forwards for " + distance + " meters.";
    }

    @Override
    public String goBackwards(int distance) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goBackwards()");
        return "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved backwards for " + distance + " meters.";
    }

    @Override
    public String goRight(int angle) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goRight()");
        return "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved right for " + angle + " degrees.";
    }

    @Override
    public String goLeft(int angle) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goLeft()");
        return "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved left for " + angle + " degrees.";
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
