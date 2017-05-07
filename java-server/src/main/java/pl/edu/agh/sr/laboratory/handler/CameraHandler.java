package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.camera.Camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraHandler implements Camera.Iface {
    private DeviceStruct deviceInfo;

    private boolean isAvailable;

    private final int maxZoom;
    private final int minZoom;

    private final int maxHorizontalAngle;
    private final int minHorizontalAngle;

    private final int maxVerticalAngle;
    private final int minVerticalAngle;

    private int currentZoom;
    private int currentHorizontalAngle;
    private int currentVerticalAngle;

    public CameraHandler(int id) {
        deviceInfo = new DeviceStruct();
        this.deviceInfo.setId(id);
        this.deviceInfo.setType("Camera");

        this.isAvailable = true;

        this.maxZoom = 32;
        this.minZoom = 0;

        this.maxHorizontalAngle = 90;
        this.minHorizontalAngle = -90;

        this.maxVerticalAngle = 90;
        this.minVerticalAngle = -90;

        this.currentZoom = 0;
        this.currentHorizontalAngle = 0;
        this.currentVerticalAngle = 0;
    }

    @Override
    public Status getStatus() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": getStatus()");
        if (isAvailable) {
            return Status.AVAILABLE;
        }
        else {
            return Status.NOT_AVAILABLE;
        }
    }

    @Override
    public List<String> getAvailableCommands() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": getAvailableCommands()");
        String[] commands = {
                "\\zoom_in [value]",
                "\\zoom_out [value]",
                "\\look_up [angle]",
                "\\look_down [angle]",
                "\\rotate_right [angle]",
                "\\rotate_left [angle]",
                "\\start_recording",
                "\\stop_recording",
                "\\commands",
                "\\end"
        };

        return new ArrayList<>(Arrays.asList(commands));
    }

    @Override
    public String acquireControl() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": acquireControl()");
        isAvailable = false;
        return "Camera #" + deviceInfo.getId() + " acquired.";
    }

    @Override
    public String releaseControl() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        return "Camera #" + deviceInfo.getId() + " released.";
    }

    @Override
    public void startMonitoring() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": startMonitoring()");
    }

    @Override
    public void stopMonitoring() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": stopMonitoring()");
    }

    @Override
    public String zoomIn(int value) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": zoomIn()");
        currentZoom = currentZoom + value;
        if (currentZoom > maxZoom) {
            currentZoom = maxZoom;
        }
        return "Camera #" + deviceInfo.getId() + ": current zoom value: " + currentZoom;
    }

    @Override
    public String zoomOut(int value) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": zoomOut()");
        currentZoom = currentZoom - value;
        if (currentZoom < minZoom) {
            currentZoom = minZoom;
        }
        return "Camera #" + deviceInfo.getId() + ": current zoom value: " + currentZoom;
    }

    @Override
    public String lookUp(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": lookUp()");
        currentVerticalAngle = currentVerticalAngle + angle;
        if (currentVerticalAngle > maxVerticalAngle) {
            currentVerticalAngle = maxVerticalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new vertical angle: " + currentVerticalAngle);
        return "Camera #" + deviceInfo.getId() + ": current vertical angle: " + currentVerticalAngle;
    }

    @Override
    public String lookDown(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": lookDown()");
        currentVerticalAngle = currentVerticalAngle - angle;
        if (currentVerticalAngle < minVerticalAngle) {
            currentVerticalAngle = minVerticalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new vertical angle: " + currentVerticalAngle);
        return "Camera #" + deviceInfo.getId() + ": current vertical angle: " + currentVerticalAngle;
    }

    @Override
    public String rotateRight(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": rotateRight()");
        currentHorizontalAngle = currentHorizontalAngle + angle;
        if (currentHorizontalAngle > maxHorizontalAngle) {
            currentHorizontalAngle = maxHorizontalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new horizontal angle: " + currentHorizontalAngle);
        return "Camera #" + deviceInfo.getId() + ": current horizontal angle: " + currentHorizontalAngle;
    }

    @Override
    public String rotateLeft(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": rotateLeft()");
        currentHorizontalAngle = currentHorizontalAngle - angle;
        if (currentHorizontalAngle < minHorizontalAngle) {
            currentHorizontalAngle = minHorizontalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new horizontal angle: " + currentHorizontalAngle);
        return "Camera #" + deviceInfo.getId() + ": current horizontal angle: " + currentHorizontalAngle;
    }

    @Override
    public String startRecording() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": startRecording()");
        return "Camera #" + deviceInfo.getId() + " started recording.";
    }

    @Override
    public String stopRecording() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": stopRecording()");
        return "Camera #" + deviceInfo.getId() + " stopped recording.";
    }

    public DeviceStruct getDeviceInfo() {
        return deviceInfo;
    }
}
