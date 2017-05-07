package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.laboratory.ClientNotifier;
import pl.edu.agh.sr.laboratory.Server;
import pl.edu.agh.sr.rpc.laboratory.DeviceInUseException;
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

    private List<ClientNotifier> notifiers;

    private boolean isAvailable;

    public AdvancedMobileRoboticPlatformHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Advanced Mobile Robotic Platform");

        this.notifiers = new ArrayList<>();

        this.isAvailable = true;
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
    public String acquireControl() throws DeviceInUseException, TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": acquireControl()");
        synchronized (this) {
            if (isAvailable) {
                isAvailable = false;
            }
            else {
                DeviceInUseException e = new DeviceInUseException();
                e.alert = "Someone else is already using Advanced Mobile Robotic Platform #" + deviceInfo.getId();
                throw e;
            }
        }
        String returnMessage =  "Advanced Mobile Robotic Platform #" + deviceInfo.getId() + " acquired.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        String returnMessage =  "Advanced Mobile Robotic Platform #" + deviceInfo.getId() + " released.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public void startMonitoring(String address, int port) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": startMonitoring()");
        notifiers.add(new ClientNotifier(address, port));
    }

    @Override
    public void stopMonitoring(String address, int port) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": stopMonitoring()");
        for (ClientNotifier notifier : notifiers) {
            if (notifier.getAddress().equals(address) && notifier.getPort() == port) {
                notifiers.remove(notifier);
            }
        }
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
        String returnMessage =  "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved forwards for " + distance + " meters.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String goBackwards(int distance) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goBackwards()");
        String returnMessage =  "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved backwards for " + distance + " meters.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String goRight(int angle) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goRight()");
        String returnMessage =  "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved right for " + angle + " degrees.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String goLeft(int angle) throws TException {
        System.out.println("Advanced Mobile Robotic Platform #" + deviceInfo.getId() + ": goLeft()");
        String returnMessage =  "Advanced Mobile Robotic Platform #" +
                deviceInfo.getId() + " moved left for " + angle + " degrees.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
