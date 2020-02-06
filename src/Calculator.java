import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {

    private String operators = "+-*/";
    private String splitters = "() " + operators;


    private boolean isSplitter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < splitters.length(); i++) {
            if (token.charAt(0) == splitters.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private boolean isFunction(String token) {
        return token.equals("sqrt") || token.equals("pow2");
    }


    private int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    private List<String> parser(String infix) {
        ArrayList<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, splitters, true);
        String previous = "";
        String current = "";
        while (tokenizer.hasMoreTokens()) {
            current = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(current)) {
                System.out.println("некорректное выражение");
                return new ArrayList<>();
            }
            if (current.equals(" ")) continue;
            if (isFunction(current)) stack.push(current);
            else if (isSplitter(current)) {
                if (current.equals("(")) stack.push(current);
                else if (current.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("неправильно расставлены скобки");
                            return new ArrayList<>();
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } else {
                    if (current.equals("-") && previous.equals("") || isSplitter(previous) && !previous.equals(")")) {
                        current = "u-";
                    } else {
                        while (!stack.isEmpty() && priority(current) <= priority(stack.peek())) {
                            postfix.add(stack.pop());
                        }
                    }
                    stack.push(current);
                }

            } else {
                postfix.add(current);
            }
            previous = current;
        }
        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                System.out.println("Скобки не согласованны");
                return new ArrayList<>();
            }
        }
        return postfix;
    }

    public String calculate(List<String> postfix) {
        if (postfix.size() == 0) {
            return "некоректное выражение";
        }
        Stack<Double> stack = new Stack<>();
        for (String token : postfix) {
            if (isOperator(token)) {
                switch (token) {
                    case "*":
                        stack.push(stack.pop() * stack.pop());
                        break;
                    case "/":
                        Double b = stack.pop();
                        Double a = stack.pop();
                        stack.push(a / b);
                        break;
                    case "+":
                        stack.push(stack.pop() + stack.pop());
                        break;
                    case "-":
                        b = stack.pop();
                        a = stack.pop();
                        stack.push(a - b);
                        break;
                    case "u-":
                        stack.push(-stack.pop());
                        break;

                }
            } else if (isFunction(token)) {
                switch (token) {
                    case "pow2":
                        stack.push(Math.pow(stack.pop(), 2));
                        break;
                    case "sqrt":
                        stack.push(Math.sqrt(stack.pop()));
                        break;
                }
            } else {
                stack.push(Double.parseDouble(token));
            }
        }
        return new DecimalFormat("#.##").format(stack.pop());
    }

    public void start(String expression) {
        String result = calculate(parser(expression));
        System.out.println(expression + " = " + result);
    }

}
