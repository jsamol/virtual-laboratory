package pl.edu.agh.sr.laboratory.handler;

import org.apache.thrift.TException;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.camera.AdvancedCamera;

import java.util.List;

public class AdvancedCameraHandler implements AdvancedCamera.Iface {
    @Override
    public Status getStatus() throws TException {
        return null;
    }

    @Override
    public List<String> getAvailableCommands() throws TException {
        return null;
    }

    @Override
    public String acquireControl() throws TException {
        return null;
    }

    @Override
    public String releaseControl() throws TException {
        return null;
    }

    @Override
    public void startMonitoring() throws TException {

    }

    @Override
    public void stopMonitoring() throws TException {

    }

    @Override
    public int zoomIn(int value) throws TException {
        return 0;
    }

    @Override
    public int zoomOut(int value) throws TException {
        return 0;
    }

    @Override
    public int lookUp(int angle) throws TException {
        return 0;
    }

    @Override
    public int lookDown(int angle) throws TException {
        return 0;
    }

    @Override
    public int rotateRight(int angle) throws TException {
        return 0;
    }

    @Override
    public int rotateLeft(int angle) throws TException {
        return 0;
    }

    @Override
    public String turnOnNightVision() throws TException {
        return null;
    }

    @Override
    public String turnOffNightVision() throws TException {
        return null;
    }

    @Override
    public String turnOnThermographyMode() throws TException {
        return null;
    }

    @Override
    public String turnOffThermographyMode() throws TException {
        return null;
    }

    @Override
    public String startRecording() throws TException {
        return null;
    }

    @Override
    public String stopRecording() throws TException {
        return null;
    }
}
