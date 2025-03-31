// Команда для ввода цифры
class DigitCommand implements Command {
    private Calculator calculator;
    private int digit;

    public DigitCommand(Calculator calculator, int digit) {
        this.calculator = calculator;
        this.digit = digit;
    }

    @Override
    public void execute() {
        calculator.enterDigit(digit);
    }
}

// Команда для установки операции
class OperationCommand implements Command {
    private Calculator calculator;
    private char operation;

    public OperationCommand(Calculator calculator, char operation) {
        this.calculator = calculator;
        this.operation = operation;
    }

    @Override
    public void execute() {
        calculator.setOperation(operation);
    }
}

// Кастомная команда
class CustomCommand implements Command {
    private String action;

    public CustomCommand(String action) {
        this.action = action;
    }

    @Override
    public void execute() {
        System.out.println("Выполняется пользовательская команда: " + action);
    }
}
