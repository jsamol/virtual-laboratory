# Robotic Arm

include "virtual_laboratory.thrift"

namespace java pl.edu.agh.sr.rpc.laboratory.roboticarm
namespace py laboratory.roboticarm

enum ArmMovementType {
    RAISE = 1,
    DROP = 2,
    ROTATE_RIGHT = 3,
    ROTATE_LEFT = 4
}

service RoboticArm extends virtual_laboratory.Device {
    string grab();
    string release();

    string move(1:ArmMovementType armMovementType) throws (1:virtual_laboratory.InvalidOperationException e);
}

service PreciseRoboticArm extends RoboticArm {
    i32 movePrecisely(1:ArmMovementType armMovementType, 2:i32 angle) throws (1:virtual_laboratory.InvalidOperationException e);
}
