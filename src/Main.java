public class Main {
    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        calculator.start("-10*2-pow2(12+17)*173-285*sqrt((17+15)*(11-5)+7)");
        calculator.start("-10*2-(12+17)*173-285*((17+15)*(11-5)+7)");
        calculator.start("pow2(200+20-200+(10-26))");
        calculator.start("sqrt(16-12)");
    }
//    todo В классе калькулятор есть метод "parser", который принимает выражение, парсит его в обратную польскую
//     нотацию, а метод "calculate" принимает эту нотацию и считает результат. Функция "pow2" возводит число во вторую
//     степень, а функция "sqrt" возвращает корень квадратный из числа которого мы ей скормили. Также в приложении
//     реализован унарный минус
}
