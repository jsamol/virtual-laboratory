package pl.edu.agh.sr.laboratory;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import pl.edu.agh.sr.rpc.laboratory.Device;
import pl.edu.agh.sr.rpc.laboratory.DeviceStruct;
import pl.edu.agh.sr.rpc.laboratory.Info;
import pl.edu.agh.sr.rpc.laboratory.Status;
import pl.edu.agh.sr.rpc.laboratory.camera.AdvancedCamera;
import pl.edu.agh.sr.rpc.laboratory.camera.Camera;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.PreciseRoboticArm;
import pl.edu.agh.sr.rpc.laboratory.roboticarm.RoboticArm;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.AdvancedMobileRoboticPlatform;
import pl.edu.agh.sr.rpc.laboratory.roboticplatform.MobileRoboticPlatform;
import pl.edu.agh.sr.rpc.laboratory.telescope.Telescope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private static final int port = 9090;
    private Map<String, Device.Client> devices;

    private Info.Client info;

    private TProtocol protocol;

    private BufferedReader input;

    private Client() {
        devices = new HashMap<>();

        info = null;
        protocol = null;
        input = null;
    }

    private void start() {
        TTransport transport = null;

        try {
            transport = new TSocket("localhost", port);
            protocol = new TBinaryProtocol(transport, true, true);


//            info = new Info.Client(new TMultiplexedProtocol(protocol, "Info"));

            transport.open();

            Map<String, Integer> numbers = info.getNumbers();
            initializeClients(numbers);

            System.out.println("Welcome to the laboratory! Here's our equipment:");
            List<DeviceStruct> deviceList = info.getDevices();
            for (DeviceStruct device : deviceList) {
                System.out.println("- " + device.getType() + " #" + device.getId());
            }

            input = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            try {
                while (true) {
                    System.out.print("> ");
                    System.out.flush();
                    String command = input.readLine();
                    if ("\\list".equals(command)) {
                        deviceList = info.getDevices();
                        System.out.println("Devices in the laboratory:");
                        for (DeviceStruct device : deviceList) {
                            System.out.println("- " + device.getType() + " #" + device.getId());
                        }
                    }
                    else if (command != null && command.startsWith("\\use")) {
                        String deviceName = command.split(" ", 2)[1];
                        if (!devices.containsKey(deviceName)) {
                            System.out.println("Incorrect device name!");
                        }
                        else {
                            Device.Client device = devices.get(deviceName);
                            Status status = device.getStatus();
                            System.out.println(status);
//                            if (status == Status.AVAILABLE) {
//                                //controlDevice(device);
//                            }
//                            else if (status ==)
                        }
                    }
                    else if ("\\exit".equals(command)) {
                        break;
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

        } catch(TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void controlDevice(Device.Client device) {

    }

    private void initializeClients(Map<String, Integer> numbers) {
        int _cameras = 0;
        int _advancedCameras = 0;
        int _mobilePlatforms = 0;
        int _advancedMobilePlatforms = 0;
        int _roboticArms = 0;
        int _preciseRoboticArms = 0;
        int _telescopes = 0;

        if (numbers.containsKey("cameras")) {
            _cameras = numbers.get("cameras");
        }
        if (numbers.containsKey("acameras")) {
            _advancedCameras = numbers.get("acameras");
        }
        if (numbers.containsKey("platforms")) {
            _mobilePlatforms = numbers.get("platforms");
        }
        if (numbers.containsKey("aplatforms")) {
            _advancedMobilePlatforms = numbers.get("aplatforms");
        }
        if (numbers.containsKey("arms")) {
            _roboticArms = numbers.get("arms");
        }
        if (numbers.containsKey("parms")) {
            _preciseRoboticArms = numbers.get("parms");
        }
        if (numbers.containsKey("telescopes")) {
            _telescopes = numbers.get("telescopes");
        }

        _initializeClients(_cameras, "Camera");
        _initializeClients(_advancedCameras, "Advanced Camera");
        _initializeClients(_mobilePlatforms, "Mobile Robotic Platform");
        _initializeClients(_advancedMobilePlatforms, "Advanced Mobile Robotic Platform");
        _initializeClients(_roboticArms, "Robotic Arm");
        _initializeClients(_preciseRoboticArms, "Precise Robotic Arm");
        _initializeClients(_telescopes, "Telescope");
    }

    private void _initializeClients(int max, String name) {
//        for (int i = 0; i < max; i++) {
//            String _name = name + " #" + i;
//            switch (name) {
//                case "Camera":
//                    devices.put(_name, new Camera.Client(new TMultiplexedProtocol(protocol, _name)));
//                    break;
//                case "Advanced Camera":
//                    devices.put(_name, new AdvancedCamera.Client(new TMultiplexedProtocol(protocol, _name)));
//                    break;
//                case "Mobile Robotic Platform":
//                    devices.put(_name, new MobileRoboticPlatform.Client(new TMultiplexedProtocol(protocol, _name)));
//                    break;
//                case "Advanced Mobile Robotic Platform":
//                    devices.put(
//                            _name,
//                            new AdvancedMobileRoboticPlatform.Client(new TMultiplexedProtocol(protocol, _name))
//                    );
//                    break;
//                case "Robotic Arm":
//                    devices.put(_name, new RoboticArm.Client(new TMultiplexedProtocol(protocol, _name)));
//                    break;
//                case "Precise Robotic Arm":
//                    devices.put(
//                            _name,
//                            new PreciseRoboticArm.Client(new TMultiplexedProtocol(protocol, _name))
//                    );
//                    break;
//                case "Telescope":
//                    devices.put(_name, new Telescope.Client(new TMultiplexedProtocol(protocol, _name)));
//                    break;
//                default:
//                    break;
//            }
//        }
    }
}


