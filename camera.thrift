# Camera

include "virtual_laboratory.thrift"

namespace java pl.edu.agh.sr.rpc.laboratory.camera
namespace py laboratory.camera

service Camera extends virtual_laboratory.OpticalDevice {
    string startRecording();
    string stopRecording();
}

service AdvancedCamera extends Camera {
    string turnOnNightVision();
    string turnOffNightVision();

    string turnOnThermographyMode();
    string turnOffThermographyMode();
}
