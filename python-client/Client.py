from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TMultiplexedProtocol

from laboratory import Info
from laboratory.camera import Camera
from laboratory.camera import AdvancedCamera
from laboratory.roboticarm import RoboticArm
from laboratory.roboticarm import PreciseRoboticArm
from laboratory.robotplatform import MobileRoboticPlatform
from laboratory.robotplatform import AdvancedMobileRoboticPlatform
from laboratory.telescope import Telescope

from laboratory.ttypes import Status

port = 9090

devices = {}


def main():
    transport = TSocket.TSocket("localhost", port)
    transport = TTransport.TBufferedTransport(transport)

    protocol = TBinaryProtocol.TBinaryProtocol(transport)

    info = Info.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, "Info"))

    transport.open()

    numbers = info.getNumbers()
    initialize_clients(protocol, numbers)

    print("Welcome to the laboratory! Here's our equipment:")
    device_list = info.getDevices()
    for device in device_list:
        print("- " + device.type + " #" + str(device.id))

    while True:
        command = input("> ")
        if command == "\\list":
            device_list = info.getDevices()
            print("Devices in the laboratory:")
            for device in device_list:
                print("- " + device.type + " #" + str(device.id))

        elif command.startswith("\\use"):
            device_name = command[5:]
            if device_name not in devices:
                print("Invalid device name!")
            device = devices[device_name]
            status = device.getStatus()
            if status == Status.AVAILABLE:
                print(device.acquireControl())
                use_device(device_name, device)
            elif status == Status.NOT_AVAILABLE:
                print("Sorry, device is used by someone else.")
                answer = input("Do you want to start monitoring this device? (y/n) ")
                if answer == "y":
                    device.startMonitoring()

        elif command == "\\exit":
            transport.close()
            exit(0)


def use_device(name, device):
    commands = device.getAvailableCommands()
    print("Available commands:")
    _commands = []
    for command in commands:
        print("- " + command)
        _commands.append(command.split(" [", 1)[0])

    while True:
        command = input(name + "$ ")
        if command.find(" ") != -1:
            _command = command.split(" ", 1)
            arg = _command[1]
            if arg.find(" ") != -1:
                _arg = arg.split(" ", 1)
                arg1 = _arg[0]
                arg2 = _arg[1]
            command = _command[0]
        # TODO: commands
        if command not in _commands:
            print("Invalid command!")
        elif command == "\\end":
            print(device.releaseControl())
            return
        elif command == "\\adjust":
            print(device.adjust())
        elif command == "\\look_down":
            print(device.lookDown(arg))
        elif command == "\\look_up":
            print(device.lookUp(arg))
        elif command == "\\rotate_left":
            print(device.rotateLeft(arg))
        elif command == "\\rotate_right":
            print(device.rotateRight(arg))
        elif command == "\\take_photo":
            print(device.takePhoto())
        elif command == "\\zoom_in":
            print(device.zoomIn(arg))
        elif command == "\\zoom_out":
            print(device.zoomOut(arg))


def initialize_clients(protocol, numbers):
    cameras = 0
    advanced_cameras = 0
    mobile_robotic_platforms = 0
    advanced_mobile_robotic_platforms = 0
    robotic_arms = 0
    precise_robotic_arms = 0
    telescopes = 0

    if 'cameras' in numbers:
        cameras = numbers['cameras']
    if 'acameras' in numbers:
        advanced_cameras = numbers['acameras']
    if 'platforms' in numbers:
        mobile_robotic_platforms = numbers['platforms']
    if 'aplatforms' in numbers:
        advanced_mobile_robotic_platforms = numbers['aplatforms']
    if 'arms' in numbers:
        robotic_arms = numbers['arms']
    if 'parms' in numbers:
        precise_robotic_arms = numbers['parms']
    if 'telescopes' in numbers:
        telescopes = numbers['telescopes']

    _initialize_clients(protocol, cameras, "Camera")
    _initialize_clients(protocol, advanced_cameras, "Advanced Camera")
    _initialize_clients(protocol, mobile_robotic_platforms, "Mobile Robotic Platform")
    _initialize_clients(protocol, advanced_mobile_robotic_platforms, "Advanced Mobile Robotic Platform")
    _initialize_clients(protocol, robotic_arms, "Robotic Arm")
    _initialize_clients(protocol, precise_robotic_arms, "Precise Robotic Arm")
    _initialize_clients(protocol, telescopes, "Telescope")


def _initialize_clients(protocol, limit, name):
    for i in range(0, limit):
        _name = name + " #" + str(i)
        if name == "Camera":
            devices[_name] = Camera.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))
        elif name == "AdvancedCamera":
            devices[_name] = AdvancedCamera.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))
        elif name == "Mobile Robotic Platform":
            devices[_name] = MobileRoboticPlatform.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))
        elif name == "Advanced Mobile Robotic Platform":
            devices[_name] = AdvancedMobileRoboticPlatform \
                .Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))
        elif name == "Robotic Arm":
            devices[_name] = RoboticArm.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))
        elif name == "Precise Robotic Arm":
            devices[_name] = PreciseRoboticArm.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))
        elif name == "Telescope":
            devices[_name] = Telescope.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, _name))


if __name__ == "__main__":
    main()
