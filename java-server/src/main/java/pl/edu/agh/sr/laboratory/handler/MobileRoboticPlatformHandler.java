package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.laboratory.ClientNotifier;
import pl.edu.agh.sr.laboratory.Server;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.MobileRoboticPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobileRoboticPlatformHandler implements MobileRoboticPlatform.Iface {
    private DeviceStruct deviceInfo;

    private List<ClientNotifier> notifiers;

    private boolean isAvailable;

    public MobileRoboticPlatformHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Mobile Robotic Platform");

        this.notifiers = new ArrayList<>();

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
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": getAvailableCommands()");
        String[] commands = {
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
        System.out.println("Mobile Robotic Platform#" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        String returnMessage =  "Mobile Robotic Platform #" + deviceInfo.getId() + " acquired.";
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
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        String returnMessage =  "Mobile Robotic Platform #" + deviceInfo.getId() + " released.";
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
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": startMonitoring()");
        notifiers.add(new ClientNotifier(address, port));
    }

    @Override
    public void stopMonitoring(String address, int port) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": stopMonitoring()");
        for (ClientNotifier notifier : notifiers) {
            if (notifier.getAddress().equals(address) && notifier.getPort() == port) {
                notifiers.remove(notifier);
            }
        }
    }

    @Override
    public String goForwards(int distance) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goForwards()");
        String returnMessage =  "Mobile Robotic Platform #" + deviceInfo.getId() + " moved forwards for " + distance + " meters.";
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
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goBackwards()");
        String returnMessage =  "Mobile Robotic Platform #" + deviceInfo.getId() + " moved backwards for " + distance + " meters.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String goRight(int angle) throws TException {
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goRight()");
        String returnMessage =  "Mobile Robotic Platform #" + deviceInfo.getId() + " moved right for " + angle + " degrees.";
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
        System.out.println("Mobile Robotic Platform #" + deviceInfo.getId() + ": goLeft()");
        String returnMessage =  "Mobile Robotic Platform #" + deviceInfo.getId() + " moved left for " + angle + " degrees.";
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
