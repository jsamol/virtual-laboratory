package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
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

    private boolean isAvailable;

    public RoboticArmHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Robotic Arm");

        this.isAvailable = true;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": getStatus()");
        if (isAvailable) {
            return Status.AVAILABLE;
        }
        else {
            return Status.NOT_AVAILABLE;
        }
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
    public String acquireControl() throws TException {
        System.out.println("Robotic Arm#" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        return "Robotic Arm #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Robotic Arm #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public String grab() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": grab()");
        return "Robotic Arm #" + deviceInfo.getId() + ": grabbed.";
    }

    @Override
    public String release() throws TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": release()");
        return "Robotic Arm #" + deviceInfo.getId() + ": released.";
    }

    @Override
    public String move(ArmMovementType armMovementType) throws InvalidOperationException, TException {
        System.out.println("Robotic Arm #" + deviceInfo.getId() + ": move(" + armMovementType + ")");
        switch(armMovementType) {
            case RAISE:
                return "Robotic Arm #" + deviceInfo.getId() + ": raised.";
            case DROP:
                return "Robotic Arm #" + deviceInfo.getId() + ": dropped.";
            case ROTATE_RIGHT:
                return "Robotic Arm #" + deviceInfo.getId() + ": rotated right.";
            case ROTATE_LEFT:
                return "Robotic Arm #" + deviceInfo.getId() + ": rotated left.";
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
