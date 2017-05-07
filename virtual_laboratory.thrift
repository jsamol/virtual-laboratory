# Virtual Laboratory

# This file defines an interface for a virtual laboratory.
# The laboratory is equipped with various:
#   - cameras
#   - telescopes
#   - robotic arms
#   - mobile robot platforms

namespace java pl.edu.agh.sr.rpc.laboratory
namespace py laboratory

exception InvalidOperationException {
    1: i32 whatOp,
    2: string why
}

exception DeviceInUseException {
    1: string alert
}

struct DeviceStruct {
    1: string type,
    2: i32 id
}

service Info {
    list<DeviceStruct> getDevices();
    map<string, i32> getNumbers ();
    oneway void setClientParams(1:string address, 2:i32 port);
    oneway void removeNotifier(1:string address, 2:i32 port);
}

service Device {
    list<string> getAvailableCommands();

    string acquireControl() throws (1:DeviceInUseException e);
    string releaseControl();

    oneway void startMonitoring(1:string address, 2:i32 port);
    oneway void stopMonitoring(1:string address, 2:i32 port);
}

service OpticalDevice extends Device {
    string zoomIn(1:i32 value);
    string zoomOut(1:i32 value);

    string lookUp(1:i32 angle);
    string lookDown(1:i32 angle);
    string rotateRight(1:i32 angle);
    string rotateLeft(1:i32 angle);
}

service Notifier {
    oneway void notify(1:string message)
}
