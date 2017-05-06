# Telescope

include "virtual_laboratory.thrift"

namespace java pl.edu.agh.sr.rpc.laboratory.telescope
namespace py laboratory.telescope

service Telescope extends virtual_laboratory.OpticalDevice {
    i32 adjustFocus(1:i32 value);

    string takePhoto();
}
