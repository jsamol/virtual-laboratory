package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.camera.AdvancedCamera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedCameraHandler implements AdvancedCamera.Iface {
    private DeviceStruct deviceInfo;

    private boolean isAvailable;

    private final int maxZoom;
    private final int minZoom;

    private final int maxVerticalAngle;
    private final int minVerticalAngle;

    private int currentZoom;
    private int currentHorizontalAngle;
    private int currentVerticalAngle;

    public AdvancedCameraHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Advanced Camera");

        this.isAvailable = true;

        this.maxZoom = 64;
        this.minZoom = 0;

        this.maxVerticalAngle = 90;
        this.minVerticalAngle = -90;

        this.currentZoom = 0;
        this.currentHorizontalAngle = 0;
        this.currentVerticalAngle = 0;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": getStatus()");
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
                "\\night_vision on",
                "\\night_vision off",
                "\\thermography on",
                "\\thermography off",
                "\\start_recording",
                "\\stop_recording",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        return "Advanced Camera #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Advanced Camera #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public int zoomIn(int value) throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": zoomIn()");
        if (currentZoom > maxZoom) {
            currentZoom = maxZoom;
        }
        return currentZoom;
    }

    @Override
    public int zoomOut(int value) throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": zoomOut()");
        currentZoom = currentZoom - value;
        if (currentZoom < minZoom) {
            currentZoom = minZoom;
        }
        return currentZoom;
    }

    @Override
    public int lookUp(int angle) throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": lookUp()");
        currentVerticalAngle = currentVerticalAngle + angle;
        if (currentVerticalAngle > maxVerticalAngle) {
            currentVerticalAngle = maxVerticalAngle;
        }
        System.out.println("> Advanced Camera #" + deviceInfo.getId() +
                ": new vertical angle: " + currentVerticalAngle);
        return currentVerticalAngle;
    }

    @Override
    public int lookDown(int angle) throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": lookDown()");
        currentVerticalAngle = currentVerticalAngle - angle;
        if (currentVerticalAngle < minVerticalAngle) {
            currentVerticalAngle = minVerticalAngle;
        }
        System.out.println("> Advanced Camera #" + deviceInfo.getId() +
                ": new vertical angle: " + currentVerticalAngle);
        return currentVerticalAngle;
    }

    @Override
    public int rotateRight(int angle) throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": rotateRight()");
        currentHorizontalAngle = currentHorizontalAngle + angle;
        if (currentHorizontalAngle > 360) {
            currentHorizontalAngle = currentVerticalAngle - 360;
        }
        System.out.println("> Advanced Camera #" + deviceInfo.getId() +
                ": new horizontal angle: " + currentHorizontalAngle);
        return currentHorizontalAngle;
    }

    @Override
    public int rotateLeft(int angle) throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": rotateLeft()");
        currentHorizontalAngle = currentHorizontalAngle - angle;
        if (currentHorizontalAngle < 0) {
            currentHorizontalAngle = 360 - currentHorizontalAngle;
        }
        System.out.println("> Advanced Camera #" + deviceInfo.getId() +
                ": new horizontal angle: " + currentHorizontalAngle);
        return currentHorizontalAngle;
    }

    @Override
    public String turnOnNightVision() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": turnOnNightVision()");
        return "Advanced Camera #" + deviceInfo.getId() + " is in a night vision mode now.";
    }

    @Override
    public String turnOffNightVision() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": turnOffNightVision()");
        return "Advanced Camera #" + deviceInfo.getId() + " is in a normal vision mode now.";
    }

    @Override
    public String turnOnThermographyMode() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": turnOnThermographyMode()");
        return "Advanced Camera #" + deviceInfo.getId() + " is in a thermography mode now.";
    }

    @Override
    public String turnOffThermographyMode() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": turnOffThermographyMode()");
        return "Advanced Camera #" + deviceInfo.getId() + " is in a normal vision mode now.";
    }

    @Override
    public String startRecording() throws TException {
        System.out.println("Advanced Camera #" + deviceInfo.getId() + ": startRecording()");
        return "Advanced Camera #" + deviceInfo.getId() + " started recording.";
    }

    @Override
    public String stopRecording() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": stopRecording()");
        return "Advanced Camera #" + deviceInfo.getId() + " stopped recording.";
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
