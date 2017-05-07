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

    public PreciseRoboticArmHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Precise Robotic Arm");

        this.isAvailable = true;
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
        String[] commands = {
                "\\move [type] [angle]",
                "\\grab",
                "\\release",
                "\\move [type]",
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
    public int movePrecisely(ArmMovementType armMovementType, int angle) throws InvalidOperationException, TException {
        System.out.println("Precise Robotic Arm #" + deviceInfo.getId() + ": movePrecisely()");
        return 0;
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
        return null;
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
