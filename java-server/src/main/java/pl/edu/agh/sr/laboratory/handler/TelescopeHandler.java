package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.telescope.Telescope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TelescopeHandler implements Telescope.Iface {
    private DeviceStruct deviceInfo;

    private boolean isAvailable;

    private final int maxZoom;
    private final int minZoom;

    private final int maxVerticalAngle;
    private final int minVerticalAngle;

    private final int maxFocusWheelPosition;
    private final int minFocusWheelPosition;

    private int currentZoom;
    private int currentHorizontalAngle;
    private int currentVerticalAngle;
    private int currentFocusWheelPosition;

    public TelescopeHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Telescope");

        this.isAvailable = true;

        this.maxZoom = 190;
        this.minZoom = 0;

        this.maxVerticalAngle = 90;
        this.minVerticalAngle = -90;

        this.maxFocusWheelPosition = 180;
        this.minFocusWheelPosition = 0;

        this.currentZoom = 0;
        this.currentHorizontalAngle = 0;
        this.currentVerticalAngle = 0;
        this.currentFocusWheelPosition = 0;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": getStatus()");
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
                "\\zoom_in [value]",
                "\\zoom_out [value]",
                "\\look_up [angle]",
                "\\look_down [angle]",
                "\\rotate_right [angle]",
                "\\rotate_left [angle]",
                "\\adjust",
                "\\take_photo",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        return "Telescope #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Telescope #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public int zoomIn(int value) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": zoomIn()");
        if (currentZoom > maxZoom) {
            currentZoom = maxZoom;
        }
        return currentZoom;
    }

    @Override
    public int zoomOut(int value) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": zoomOut()");
        currentZoom = currentZoom - value;
        if (currentZoom < minZoom) {
            currentZoom = minZoom;
        }
        return currentZoom;
    }

    @Override
    public int lookUp(int angle) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": lookUp()");
        currentVerticalAngle = currentVerticalAngle + angle;
        if (currentVerticalAngle > maxVerticalAngle) {
            currentVerticalAngle = maxVerticalAngle;
        }
        System.out.println("> Telescope #" + deviceInfo.getId() + ": new vertical angle: " + currentVerticalAngle);
        return currentVerticalAngle;
    }

    @Override
    public int lookDown(int angle) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": lookDown()");
        currentVerticalAngle = currentVerticalAngle - angle;
        if (currentVerticalAngle < minVerticalAngle) {
            currentVerticalAngle = minVerticalAngle;
        }
        System.out.println("> Telescope #" + deviceInfo.getId() + ": new vertical angle: " + currentVerticalAngle);
        return currentVerticalAngle;
    }

    @Override
    public int rotateRight(int angle) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": rotateRight()");
        currentHorizontalAngle = currentHorizontalAngle + angle;
        if (currentHorizontalAngle > 360) {
            currentHorizontalAngle = currentVerticalAngle - 360;
        }
        System.out.println("> Telescope #" + deviceInfo.getId() + ": new horizontal angle: " + currentHorizontalAngle);
        return currentHorizontalAngle;
    }

    @Override
    public int rotateLeft(int angle) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": rotateLeft()");
        currentHorizontalAngle = currentHorizontalAngle - angle;
        if (currentHorizontalAngle < 0) {
            currentHorizontalAngle = 360 - currentHorizontalAngle;
        }
        System.out.println("> Telescope #" + deviceInfo.getId() + ": new horizontal angle: " + currentHorizontalAngle);
        return currentHorizontalAngle;
    }

    @Override
    public int adjustFocus(int value) throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": adjustFocus()");
        currentFocusWheelPosition = currentFocusWheelPosition + value;
        if (currentFocusWheelPosition < minFocusWheelPosition) {
            currentFocusWheelPosition = minFocusWheelPosition;
        }
        else if (currentFocusWheelPosition > maxFocusWheelPosition) {
            currentFocusWheelPosition = maxFocusWheelPosition;
        }
        System.out.println("> Telescope #" +
                deviceInfo.getId() + ": new focus wheel position: " + currentFocusWheelPosition);
        return currentFocusWheelPosition;
    }

    @Override
    public String takePhoto() throws TException {
        System.out.println("Telescope #" + deviceInfo.getId() + ": takePhoto()");
        return "Telescope #" + deviceInfo.getId() + " photo saved.";
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
