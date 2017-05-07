package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.laboratory.ClientNotifier;
import pl.edu.agh.sr.laboratory.Server;
import pl.edu.agh.sr.rpc.laboratory.DeviceInUseException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.InvalidOperationException;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.ArmMovementType;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.RoboticArm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoboticArmHandler implements RoboticArm.Iface {
    private DeviceStruct deviceInfo;

    private List<ClientNotifier> notifiers;

    private boolean isAvailable;

    public RoboticArmHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Robotic Arm");

        this.notifiers = new ArrayList<>();

        this.isAvailable = true;
    }

    @Override
    public List<String> getAvailableCommands() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": getAvailableCommands()");
        String[] commands = {
                "\\grab",
                "\\release",
                "\\move [raise|drop|right|left]",
                "\\commands",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws DeviceInUseException, TException {
        System.out.println("Robotic Arm#" + deviceInfo.getId() + ": acquireControl()");
        synchronized (this) {
            if (isAvailable) {
                isAvailable = false;
            }
            else {
                DeviceInUseException e = new DeviceInUseException();
                e.alert = "Someone else is already using Robotic Arm #" + deviceInfo.getId();
                throw e;
            }
        }
        String returnMessage =  "Robotic Arm #" + deviceInfo.getId() + " acquired.";
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
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        String returnMessage =  "Robotic Arm #" + deviceInfo.getId() + " released.";
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
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": startMonitoring()");
        notifiers.add(new ClientNotifier(address, port));
    }

    @Override
    public void stopMonitoring(String address, int port) throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": stopMonitoring()");
        for (ClientNotifier notifier : notifiers) {
            if (notifier.getAddress().equals(address) && notifier.getPort() == port) {
                notifiers.remove(notifier);
            }
        }
    }

    @Override
    public String grab() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": grab()");
        String returnMessage =  "Robotic Arm #" + deviceInfo.getId() + ": grabbed.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String release() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": release()");
        String returnMessage =  "Robotic Arm #" + deviceInfo.getId() + ": released.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String move(ArmMovementType armMovementType) throws InvalidOperationException, TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": move(" + armMovementType + ")");
        String returnMessage;
        switch(armMovementType) {
            case RAISE:
                returnMessage =  "Robotic Arm #" + deviceInfo.getId() + ": raised.";
                break;
            case DROP:
                returnMessage =  "Robotic Arm #" + deviceInfo.getId() + ": dropped.";
                break;
            case ROTATE_RIGHT:
                returnMessage =  "Robotic Arm #" + deviceInfo.getId() + ": rotated right.";
                break;
            case ROTATE_LEFT:
                returnMessage =  "Robotic Arm #" + deviceInfo.getId() + ": rotated left.";
                break;
            default:
                InvalidOperationException e = new InvalidOperationException();
                e.setWhatOp(armMovementType.getValue());
                e.setWhy("Invalid operation.");
                throw e;
        }
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
