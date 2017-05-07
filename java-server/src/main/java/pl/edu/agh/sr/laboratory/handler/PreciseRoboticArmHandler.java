package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.laboratory.ClientNotifier;
import pl.edu.agh.sr.laboratory.Server;
import pl.edu.agh.sr.rpc.laboratory.DeviceInUseException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.InvalidOperationException;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.ArmMovementType;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.PreciseRoboticArm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreciseRoboticArmHandler implements PreciseRoboticArm.Iface {
    private DeviceStruct deviceInfo;

    private List<ClientNotifier> notifiers;

    private boolean isAvailable;

    private int maxVerticalAngle;
    private int minVerticalAngle;

    private int currentVerticalAngle;
    private int currentHorizontalAngle;

    public PreciseRoboticArmHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Precise Robotic Arm");

        this.notifiers = new ArrayList<>();

        this.isAvailable = true;

        this.maxVerticalAngle = 90;
        this.minVerticalAngle = -90;

        this.currentVerticalAngle = 0;
        this.currentHorizontalAngle = 0;
    }

    @Override
    public List<String> getAvailableCommands() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": getAvailableCom()");
        String[] commands = {
                "\\p_move [raise|drop|right|left] [angle]",
                "\\grab",
                "\\release",
                "\\move [raise|drop|right|left]",
                "\\commands",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws TException {
        System.out.println("Precise Robotic Arm#" + deviceInfo.getId() + ": acquireControl()");
        synchronized (this) {
            if (isAvailable) {
                isAvailable = false;
            }
            else {
                DeviceInUseException e = new DeviceInUseException();
                e.alert = "Someone else is already using Precise Robotic Arm #" + deviceInfo.getId();
                throw e;
            }
        }
        String returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + " acquired.";
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
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        String returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + " released.";
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
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": startMonitoring()");
        notifiers.add(new ClientNotifier(address, port));
    }

    @Override
    public void stopMonitoring(String address, int port) throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": stopMonitoring()");
        for (ClientNotifier notifier : notifiers) {
            if (notifier.getAddress().equals(address) && notifier.getPort() == port) {
                notifiers.remove(notifier);
            }
        }
    }

    @Override
    public String movePrecisely(ArmMovementType armMovementType, int angle)
            throws InvalidOperationException, TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": movePrecisely()");
        String returnMessage;
        switch(armMovementType) {
            case RAISE:
                currentVerticalAngle = currentVerticalAngle + angle;
                if (currentVerticalAngle > maxVerticalAngle) {
                    currentVerticalAngle = maxVerticalAngle;
                }
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current vertical angle: " + currentVerticalAngle;
                break;
            case DROP:
                currentVerticalAngle = currentVerticalAngle - angle;
                if (currentVerticalAngle < minVerticalAngle) {
                    currentVerticalAngle = minVerticalAngle;
                }
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current vertical angle: " + currentVerticalAngle;
                break;
            case ROTATE_RIGHT:
                currentHorizontalAngle = currentHorizontalAngle + angle;
                if (currentHorizontalAngle > 360) {
                    currentHorizontalAngle = currentHorizontalAngle - 360;
                }
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current horizontal angle: " + currentHorizontalAngle;
                break;
            case ROTATE_LEFT:
                currentHorizontalAngle = currentHorizontalAngle - angle;
                if (currentHorizontalAngle < 0) {
                    currentHorizontalAngle = 360 + currentHorizontalAngle;
                }
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current horizontal angle: " + currentHorizontalAngle;
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

    @Override
    public String grab() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": grab()");
        String returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + ": grabbed.";
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
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": release()");
        String returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + ": released.";
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
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": move(" + armMovementType + ")");
        String returnMessage;
        switch(armMovementType) {
            case RAISE:
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + ": raised.";
                break;
            case DROP:
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + ": dropped.";
                break;
            case ROTATE_RIGHT:
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + ": rotated right.";
                break;
            case ROTATE_LEFT:
                returnMessage =  "Precise Robotic Arm #" + deviceInfo.getId() + ": rotated left.";
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
