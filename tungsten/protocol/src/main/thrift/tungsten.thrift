namespace cpp com.example.tungsten
namespace d com.example.tungsten
namespace java com.example.tungsten
namespace php com.example.tungsten
namespace perl com.example.tungsten
namespace as3 com.example.tungsten

enum TCircuit {
    PARALLEL,
    SERIES
}

exception TIllegalResistorValueException {
}

service TTungstenAlphaService {
    i32 resistance(1:TCircuit circuit, 2:i32 r1, 3:i32 r2) throws (1:TIllegalResistorValueException illegalResistorValue)
}