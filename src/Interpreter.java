import java.util.*;

// Контекст
class Context {
    private Map<String, Integer> variables;

    public Context() {
        variables = new HashMap<>();
    }

    public void setVariable(String name, int value) {
        variables.put(name, value);
    }

    public int getVariable(String name) {
        return variables.getOrDefault(name, 0);
    }
}

// abstract class
interface Expression {
    int interpret(Context context);
}

// terminal exp
class NumberExpression implements Expression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret(Context context) {
        return number;
    }
}

// Нетерминальное выражение - бинарная операция сложения
class AdditionExpression implements Expression {
    private Expression leftOperand;
    private Expression rightOperand;

    public AdditionExpression(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public int interpret(Context context) {
        return leftOperand.interpret(context) + rightOperand.interpret(context);
    }
}

// non terminal
class SubtractionExpression implements Expression {
    private Expression leftOperand;
    private Expression rightOperand;

    public SubtractionExpression(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public int interpret(Context context) {
        return leftOperand.interpret(context) - rightOperand.interpret(context);
    }
}

public class Interpreter {
    public static void main(String[] args) {
        // Создаем контекст и устанавливаем значения переменных
        Context context = new Context();
        context.setVariable("x", 3);
        context.setVariable("y", 4);
        context.setVariable("z", 2);

        // Представляем выражение "3 + 4 - 2"
        Expression expression = new SubtractionExpression(
                new AdditionExpression(new NumberExpression(context.getVariable("x")), new NumberExpression(context.getVariable("y"))),
                new NumberExpression(context.getVariable("z"))
        );

        // Интерпретируем выражение и выводим результат
        int result = expression.interpret(context);
        System.out.println("Результат интерпретации: " + result);
    }
}

