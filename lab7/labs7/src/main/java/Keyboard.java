import java.util.HashMap;
import java.util.Map;

public class Keyboard {
    private Map<String, Command> keys = new HashMap<>();

    public void setKey(String key, Command command) {
        keys.put(key, command);
    }

    public void pressKey(String key) {
        if (keys.containsKey(key)) {
            keys.get(key).execute();
        } else {
            System.out.println("Кнопка " + key + " не запрограммирована.");
        }
    }
}
