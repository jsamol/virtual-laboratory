package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.laboratory.ClientNotifier;
import pl.edu.agh.sr.laboratory.Server;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.camera.Camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraHandler implements Camera.Iface {
    private DeviceStruct deviceInfo;

    private List<ClientNotifier> notifiers;

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

        this.notifiers = new ArrayList<>();

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
        String returnMessage =  "Camera #" + deviceInfo.getId() + " acquired.";
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
        System.out.println("Camera #" + deviceInfo.getId() + ": releaseControl()");
        isAvailable = true;
        String returnMessage =  "Camera #" + deviceInfo.getId() + " released.";
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
        System.out.println("Camera #" + deviceInfo.getId() + ": startMonitoring()");
        synchronized (this) {
            notifiers.add(new ClientNotifier(address, port));
        }
    }

    @Override
    public void stopMonitoring(String address, int port) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": stopMonitoring()");
        synchronized (this) {
            ClientNotifier toRemove = null;
            for (ClientNotifier notifier : notifiers) {
                if (notifier.getAddress().equals(address) && notifier.getPort() == port) {
                    toRemove = notifier;
                    break;
                }
            }
            notifiers.remove(toRemove);
        }
    }

    @Override
    public String zoomIn(int value) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": zoomIn()");
        currentZoom = currentZoom + value;
        if (currentZoom > maxZoom) {
            currentZoom = maxZoom;
        }
        String returnMessage =  "Camera #" + deviceInfo.getId() + ": current zoom value: " + currentZoom;
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String zoomOut(int value) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": zoomOut()");
        currentZoom = currentZoom - value;
        if (currentZoom < minZoom) {
            currentZoom = minZoom;
        }
        String returnMessage =  "Camera #" + deviceInfo.getId() + ": current zoom value: " + currentZoom;
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String lookUp(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": lookUp()");
        currentVerticalAngle = currentVerticalAngle + angle;
        if (currentVerticalAngle > maxVerticalAngle) {
            currentVerticalAngle = maxVerticalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new vertical angle: " + currentVerticalAngle);
        String returnMessage =  "Camera #" + deviceInfo.getId() + ": current vertical angle: " + currentVerticalAngle;
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String lookDown(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": lookDown()");
        currentVerticalAngle = currentVerticalAngle - angle;
        if (currentVerticalAngle < minVerticalAngle) {
            currentVerticalAngle = minVerticalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new vertical angle: " + currentVerticalAngle);
        String returnMessage =  "Camera #" + deviceInfo.getId() + ": current vertical angle: " + currentVerticalAngle;
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String rotateRight(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": rotateRight()");
        currentHorizontalAngle = currentHorizontalAngle + angle;
        if (currentHorizontalAngle > maxHorizontalAngle) {
            currentHorizontalAngle = maxHorizontalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new horizontal angle: " + currentHorizontalAngle);
        String returnMessage =  "Camera #" + deviceInfo.getId() + ": current horizontal angle: " + currentHorizontalAngle;
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String rotateLeft(int angle) throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": rotateLeft()");
        currentHorizontalAngle = currentHorizontalAngle - angle;
        if (currentHorizontalAngle < minHorizontalAngle) {
            currentHorizontalAngle = minHorizontalAngle;
        }
        System.out.println("> Camera #" + deviceInfo.getId() + ": new horizontal angle: " + currentHorizontalAngle);
        String returnMessage =  "Camera #" + deviceInfo.getId() + ": current horizontal angle: " + currentHorizontalAngle;
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String startRecording() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": startRecording()");
        String returnMessage =  "Camera #" + deviceInfo.getId() + " started recording.";
        for (ClientNotifier notifier : Server.getNotifiers()) {
            notifier.notify(returnMessage);
        }
        for (ClientNotifier notifier : notifiers) {
            notifier.notify(returnMessage);
        }

        return returnMessage;
    }

    @Override
    public String stopRecording() throws TException {
        System.out.println("Camera #" + deviceInfo.getId() + ": stopRecording()");
        String returnMessage =  "Camera #" + deviceInfo.getId() + " stopped recording.";
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
