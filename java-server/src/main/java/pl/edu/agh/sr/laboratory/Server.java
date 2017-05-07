package pl.edu.agh.sr.laboratory;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import pl.edu.agh.sr.laboratory.handler.*;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Info;
import pl.edu.agh.sr.rpc.laboratory.camera.AdvancedCamera;
import pl.edu.agh.sr.rpc.laboratory.camera.Camera;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.PreciseRoboticArm;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.RoboticArm;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.AdvancedMobileRoboticPlatform;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.MobileRoboticPlatform;
import pl.edu.agh.sr.rpc.laboratory.telescope.Telescope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.thrift.server.TServer.*;

public class Server {
    private static final int port = 9090;
    private static final Map<String, Integer> numbers = new HashMap<>();

    private static List<DeviceStruct> devices;

    private TMultiplexedProcessor multiplexedProcessor;

    private Server() {
        numbers.put("cameras", 3);
        numbers.put("acameras", 2);
        numbers.put("platforms", 4);
        numbers.put("aplatforms", 2);
        numbers.put("arms", 2);
        numbers.put("parms", 2);
        numbers.put("telescopes", 3);

        devices = new ArrayList<>();

        multiplexedProcessor = new TMultiplexedProcessor();
    }

    private void start() throws TTransportException {
        setupEquipment();
        multiplexedProcessor.registerProcessor("Info", new Info.Processor<>(InfoHandler.getInstance()));

        TServerTransport serverTransport = new TServerSocket(port);
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

        TServer server = new TSimpleServer(
                new Args(serverTransport)
                .protocolFactory(protocolFactory)
                .processor(multiplexedProcessor)
        );

        System.out.println("Server running...");
        server.serve();

        // TODO: monitoring, complete handlers
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    private void setupEquipment() {
        for (int i = 0; i < numbers.get("cameras"); i++) {
            CameraHandler cameraHandler = new CameraHandler(i);
            devices.add(cameraHandler.getDeviceInfo());
            String name = "Camera #" + i;
            multiplexedProcessor.registerProcessor(name, new Camera.Processor<>(cameraHandler));
        }

        for (int i = 0; i < numbers.get("acameras"); i++) {
            AdvancedCameraHandler advancedCameraHandler = new AdvancedCameraHandler(i);
            devices.add(advancedCameraHandler.getDeviceInfo());
            String name = "Advanced Camera #" + i;
            multiplexedProcessor.registerProcessor(name, new AdvancedCamera.Processor<>(advancedCameraHandler));
        }

        for (int i = 0; i < numbers.get("platforms"); i++) {
            MobileRoboticPlatformHandler mobileRoboticPlatformHandler = new MobileRoboticPlatformHandler(i);
            devices.add(mobileRoboticPlatformHandler.getDeviceInfo());
            String name = "Mobile Robotic Platform #" + i;
            multiplexedProcessor
                    .registerProcessor(name, new MobileRoboticPlatform.Processor<>(mobileRoboticPlatformHandler));
        }

        for (int i = 0; i < numbers.get("aplatforms"); i++) {
            AdvancedMobileRoboticPlatformHandler advancedMobileRoboticPlatformHandler =
                    new AdvancedMobileRoboticPlatformHandler(i);
            devices.add(advancedMobileRoboticPlatformHandler.getDeviceInfo());
            String name = "Advanced Mobile Robotic Platform #" + i;
            multiplexedProcessor.registerProcessor(
                    name,
                    new AdvancedMobileRoboticPlatform.Processor<>(advancedMobileRoboticPlatformHandler)
            );
        }

        for (int i = 0; i < numbers.get("arms"); i++) {
            RoboticArmHandler roboticArmHandler = new RoboticArmHandler(i);
            devices.add(roboticArmHandler.getDeviceInfo());
            String name = "Robotic Arm #" + i;
            multiplexedProcessor.registerProcessor(name, new RoboticArm.Processor<>(roboticArmHandler));
        }

        for (int i = 0; i < numbers.get("parms"); i++) {
            PreciseRoboticArmHandler preciseRoboticArmHandler = new PreciseRoboticArmHandler(i);
            devices.add(preciseRoboticArmHandler.getDeviceInfo());
            String name = "Precise Robotic Arm #" + i;
            multiplexedProcessor.registerProcessor(name, new PreciseRoboticArm.Processor<>(preciseRoboticArmHandler));
        }

        for (int i = 0; i < numbers.get("telescopes"); i++) {
            TelescopeHandler telescopeHandler = new TelescopeHandler(i);
            devices.add(telescopeHandler.getDeviceInfo());
            String name = "Telescope #" + i;
            multiplexedProcessor.registerProcessor(name, new Telescope.Processor<>(telescopeHandler));
        }
    }

    public static Map<String, Integer> getNumbers() {
        return numbers;
    }

    public static List<DeviceStruct> getDevices() {
        return devices;
    }
}
