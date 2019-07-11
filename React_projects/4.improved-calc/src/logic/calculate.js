function calculate(obj) {
  var op1 = Number(obj.operand1);
  var op2 = Number(obj.operand2);
  var op = obj.operator;

  if (isNaN(op1) || isNaN(op2)) {
    return NaN;
  }

  if (op === "+") {
    return op1 + op2;
  } else if (op === "-") {
    return op1 - op2;
  } else if (op === "*") {
    return op1 * op2;
  } else if (op === "/") {
    return Math.floor(op1 / op2);
  }
}

export default calculate;
