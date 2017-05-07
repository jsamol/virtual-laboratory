import signal
import os

import Server

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

from laboratory.ttypes import InvalidOperationException
from laboratory.ttypes import DeviceInUseException
from laboratory.roboticarm.ttypes import ArmMovementType
from laboratory.robotplatform.ttypes import MovementType
from laboratory.robotplatform.ttypes import OrderStruct

port = 9090

address = "localhost"
return_port = 9091

devices = {}

info = None

transport = None
controlled_device = None


def sigint_handler(signal, frame):
    if controlled_device is not None:
        controlled_device.releaseControl()
    on_exit()


def main():
    global transport
    global info
    global controlled_device

    signal.signal(signal.SIGINT, sigint_handler)
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

        elif command == "\\observe":
            print("You are now in the observer mode.\nType '\\stop' to exit.")
            observe()

        elif command.startswith("\\use"):
            device_name = command[5:]
            if device_name not in devices:
                print("Invalid device name!")
            else:
                device = devices[device_name]
                try:
                    print(device.acquireControl())
                    controlled_device = device
                    use_device(device_name, device)
                except DeviceInUseException as e:
                    print(e.alert)
                    answer = input("Do you want to start monitoring this device? (y/n) ")
                    if answer == "y":
                        monitor(device)
        elif command.startswith("\\monitor"):
            device_name = command[9:]
            if device_name not in devices:
                print("Invalid device name!")
            else:
                device = devices[device_name]
                monitor(device)

        elif command == "\\exit":
            on_exit()


def observe():
    global info

    info.setClientParams(address, return_port)
    while True:
        command = input("")
        if command == "\\stop":
            info.removeNotifier(address, return_port)
            print("You are not in the observer mode anymore.")
            return


def monitor(device):
    device.startMonitoring(address, return_port)
    print("Type '\\stop' to exit.")
    while True:
        command = input("")
        if command == "\\stop":
            device.stopMonitoring(address, return_port)
            return


def use_device(name, device):
    commands = device.getAvailableCommands()
    print("Available commands:")
    _commands = []
    for command in commands:
        print("- " + command)
        _commands.append(command.split(" [", 1)[0])

    while True:
        global controlled_device

        args = []
        command = input(name + "$ ")
        if command.find(" ") != -1:
            _command = command.split(" ", 1)
            args = _command[1].split(" ")
            command = _command[0]
        if command not in _commands:
            print("Invalid command!")
        elif command == "\\end":
            print(device.releaseControl())
            controlled_device = None
            return
        elif command == "\\commands":
            print("Available commands:")
            for command in commands:
                print("- " + command)
        elif command == "\\adjust":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.adjustFocus(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\backwards":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.goBackwards(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\do":
            if len(args) < 2 and len(args) % 2 != 0:
                print("More arguments expected")
            else:
                _args = []
                for i in range(0, len(args), 2):
                    order = OrderStruct()
                    if args[i] == "forwards":
                        order.movementType = MovementType.FORWARDS
                    elif args[i] == "backwards":
                        order.movementType = MovementType.BACKWARDS
                    elif args[i] == "right":
                        order.movementType = MovementType.RIGHT
                    elif args[i] == "left":
                        order.movementType = MovementType.LEFT
                    try:
                        order.param = int(args[i + 1])
                    except ValueError:
                        print("Integer expected")
                    _args.append(order)
                try:
                    print(device.doSequenceOfMovements(_args))
                except InvalidOperationException as e:
                    print(e)
        elif command == "\\forwards":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.goForwards(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\grab":
            print(device.grab())
        elif command == "\\left":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.goLeft(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\look_down":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.lookDown(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\look_up":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.lookUp(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\move":
            if len(args) < 1:
                print("More arguments expected")
            else:
                if args[0] == "raise":
                    _arg = ArmMovementType.RAISE
                elif args[0] == "drop":
                    _arg = ArmMovementType.DROP
                elif args[0] == "right":
                    _arg = ArmMovementType.ROTATE_RIGHT
                elif args[0] == "left":
                    _arg = ArmMovementType.ROTATE_LEFT
                else:
                    print("Invalid type")
                try:
                    print(device.move(_arg))
                except InvalidOperationException as e:
                    print(e)
        elif command == "\\night_vision_off":
            print(device.turnOffNightVision())
        elif command == "\\night_vision_on":
            print(device.turnOnNightVision())
        elif command == "\\p_move":
            if len(args) < 2:
                print("More arguments expected")
            else:
                _arg1 = 0
                if args[0] == "raise":
                    _arg1 = ArmMovementType.RAISE
                elif args[0] == "drop":
                    _arg1 = ArmMovementType.DROP
                elif args[0] == "right":
                    _arg1 = ArmMovementType.ROTATE_RIGHT
                elif args[0] == "left":
                    _arg1 = ArmMovementType.ROTATE_LEFT
                else:
                    print("Invalid type")
                try:
                    _arg2 = int(args[1])
                    print(device.movePrecisely(_arg1, _arg2))
                except InvalidOperationException as e:
                    print(e)
                except ValueError:
                    print("Integer expected")
        elif command == "\\release":
            print(device.release())
        elif command == "\\right":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.goRight(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\rotate_left":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.rotateLeft(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\rotate_right":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.rotateRight(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\start_recording":
            print(device.startRecording())
        elif command == "\\stop_recording":
            print(device.stopRecording())
        elif command == "\\take_photo":
            print(device.takePhoto())
        elif command == "\\thermography_off":
            print(device.turnOffThermographyMode())
        elif command == "\\thermography_on":
            print(device.turnOnThermographyMode())
        elif command == "\\zoom_in":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.zoomIn(_arg))
                except ValueError:
                    print("Integer expected")
        elif command == "\\zoom_out":
            if len(args) < 1:
                print("More arguments expected")
            else:
                try:
                    _arg = int(args[0])
                    print(device.zoomOut(_arg))
                except ValueError:
                    print("Integer expected")


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
        elif name == "Advanced Camera":
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


def on_exit():
    if transport is not None:
        transport.close()
        os._exit(0)

if __name__ == "__main__":
    server = Server.Server(return_port)
    server.start()

    main()


