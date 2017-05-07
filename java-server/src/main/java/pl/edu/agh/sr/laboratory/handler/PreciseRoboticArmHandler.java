package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
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

    private boolean isAvailable;

    private int maxVerticalAngle;
    private int minVerticalAngle;

    private int currentVerticalAngle;
    private int currentHorizontalAngle;

    public PreciseRoboticArmHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Precise Robotic Arm");

        this.isAvailable = true;

        this.maxVerticalAngle = 90;
        this.minVerticalAngle = -90;

        this.currentVerticalAngle = 0;
        this.currentHorizontalAngle = 0;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": getStatus()");
        if (isAvailable) {
            return Status.AVAILABLE;
        }
        else {
            return Status.NOT_AVAILABLE;
        }
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
        isAvailable = false;
        return "Precise Robotic Arm #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Precise Robotic Arm #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public String movePrecisely(ArmMovementType armMovementType, int angle) throws InvalidOperationException, TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": movePrecisely()");
        switch(armMovementType) {
            case RAISE:
                currentVerticalAngle = currentVerticalAngle + angle;
                if (currentVerticalAngle > maxVerticalAngle) {
                    currentVerticalAngle = maxVerticalAngle;
                }
                return "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current vertical angle: " + currentVerticalAngle;
            case DROP:
                currentVerticalAngle = currentVerticalAngle - angle;
                if (currentVerticalAngle < minVerticalAngle) {
                    currentVerticalAngle = minVerticalAngle;
                }
                return "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current vertical angle: " + currentVerticalAngle;
            case ROTATE_RIGHT:
                currentHorizontalAngle = currentHorizontalAngle + angle;
                if (currentHorizontalAngle > 360) {
                    currentHorizontalAngle = currentHorizontalAngle - 360;
                }
                return "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current horizontal angle: " + currentHorizontalAngle;
            case ROTATE_LEFT:
                currentHorizontalAngle = currentHorizontalAngle - angle;
                if (currentHorizontalAngle < 0) {
                    currentHorizontalAngle = 360 + currentHorizontalAngle;
                }
                return "Precise Robotic Arm #" + deviceInfo.getId() +
                        ": current horizontal angle: " + currentHorizontalAngle;
            default:
                InvalidOperationException e = new InvalidOperationException();
                e.setWhatOp(armMovementType.getValue());
                e.setWhy("Invalid operation.");
                throw e;
        }
    }

    @Override
    public String grab() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": grab()");
        return "Precise Robotic Arm #" + deviceInfo.getId() + ": grabbed.";
    }

    @Override
    public String release() throws TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": release()");
        return "Precise Robotic Arm #" + deviceInfo.getId() + ": released.";
    }

    @Override
    public String move(ArmMovementType armMovementType) throws InvalidOperationException, TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": move(" + armMovementType + ")");
        switch(armMovementType) {
            case RAISE:
                return "Precise Robotic Arm #" + deviceInfo.getId() + ": raised.";
            case DROP:
                return "Precise Robotic Arm #" + deviceInfo.getId() + ": dropped.";
            case ROTATE_RIGHT:
                return "Precise Robotic Arm #" + deviceInfo.getId() + ": rotated right.";
            case ROTATE_LEFT:
                return "Precise Robotic Arm #" + deviceInfo.getId() + ": rotated left.";
            default:
                InvalidOperationException e = new InvalidOperationException();
                e.setWhatOp(armMovementType.getValue());
                e.setWhy("Invalid operation.");
                throw e;
        }
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
