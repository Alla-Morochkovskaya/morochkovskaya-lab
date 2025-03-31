public class Calculator {
    private int currentValue = 0;
    private int storedValue = 0;
    private char operation = ' ';

    public void enterDigit(int digit) {
        currentValue = currentValue * 10 + digit;
        System.out.println("Текущий ввод: " + currentValue);
    }

    public void setOperation(char op) {
        storedValue = currentValue;
        currentValue = 0;
        operation = op;
        System.out.println("Выбрана операция: " + operation);
    }

    public void calculate() {
        switch (operation) {
            case '+': currentValue = storedValue + currentValue; break;
            case '-': currentValue = storedValue - currentValue; break;
            case '*': currentValue = storedValue * currentValue; break;
            case '/': currentValue = storedValue / currentValue; break;
            default: System.out.println("Нет операции"); return;
        }
        System.out.println("Результат: " + currentValue);
    }
}
