public class CustomCalculator {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Keyboard keyboard = new Keyboard();

        // Фиксированные кнопки
        keyboard.setKey("1", new DigitCommand(calculator, 1));
        keyboard.setKey("2", new DigitCommand(calculator, 2));
        keyboard.setKey("+", new OperationCommand(calculator, '+'));
        keyboard.setKey("=", () -> calculator.calculate());

        // Настраиваемая кнопка
        keyboard.setKey("C", new CustomCommand("Очистка экрана"));

        // Использование клавиатуры
        keyboard.pressKey("1");
        keyboard.pressKey("+");
        keyboard.pressKey("2");
        keyboard.pressKey("=");
        keyboard.pressKey("C");
    }
}
