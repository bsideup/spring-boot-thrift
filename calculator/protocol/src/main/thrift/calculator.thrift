namespace cpp com.example.calculator
namespace d com.example.calculator
namespace java com.example.calculator
namespace php com.example.calculator
namespace perl com.example.calculator
namespace as3 com.example.calculator

enum TOperation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}

exception TDivisionByZeroException {
}

service TCalculatorService {
   i32 calculate(1:i32 num1, 2:i32 num2, 3:TOperation op) throws (1:TDivisionByZeroException divisionByZero);
}