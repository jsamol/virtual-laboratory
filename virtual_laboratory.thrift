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

enum Status {
    AVAILABLE = 1,
    NOT_AVAILABLE = 2
}

struct DeviceStruct {
    1: string type,
    2: i32 id
}

service Info {
    list<DeviceStruct> getDevices();
    map<string, i32> getNumbers ();
}

service Device {
    Status getStatus();

    list<string> getAvailableCommands();

    string acquireControl();
    string releaseControl();

    void startMonitoring();
    void stopMonitoring();
}

service OpticalDevice extends Device {
    i32 zoomIn(1:i32 value);
    i32 zoomOut(1:i32 value);

    i32 lookUp(1:i32 angle);
    i32 lookDown(1:i32 angle);
    i32 rotateRight(1:i32 angle);
    i32 rotateLeft(1:i32 angle);
}
