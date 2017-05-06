# Mobile Robotic Platform

include "virtual_laboratory.thrift"

namespace java pl.edu.agh.sr.rpc.laboratory.roboticplatform
namespace py laboratory.robotplatform

enum MovementType {
    FORWARDS = 1,
    BACKWARDS = 2,
    LEFT = 3,
    RIGHT = 4,
}

struct OrderStruct {
    1: MovementType movementType,
    2: i32 param
}

service MobileRoboticPlatform extends virtual_laboratory.Device {
    void goForwards(1:i32 distance);
    void goBackwards(1:i32 distance);
    void goRight(1:i32 angle);
    void goLeft(1:i32 angle);
}

service AdvancedMobileRoboticPlatform extends MobileRoboticPlatform {
    void doSequenceOfMovements(1:list<OrderStruct> orders) throws (1:virtual_laboratory.InvalidOperationException e);
}
