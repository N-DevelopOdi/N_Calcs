package ru.n_develop.n_calcs.Helper;

import android.renderscript.Double2;
import android.util.Log;

/**
 * Этот модуль сождержит рекурсивно-последовательный анализатор, который не использует переменные
 */

public class ParserOld
{
    // Объявлем лексем
    final int NONE = 0;
    final int DELIMITER = 1;
    final int VARIABLE = 2;
    final int FUNCTION = 4;
    final int NUMBER = 3;

    //Объявляем констант синтаксических ошибок
    final int SYNTAX = 0;
    final int UNBALPARENS = 1;
    final int NOEXP = 2;
    final int DIVBYZERO = 3;

    //Лексема обозначает конец выражения
    final String EOE = "\0";

    private String exp;     // Ссылка на строку с выражением
    private int expIdx;     // Текущий индекс в выражении
    private String token;   // Сохранение текущей ликсемы
    private int tokType;    // Сохранение типа лексемы

    private double vars[] = new double[26];

    // Точка входа анализатора
    public  double evaluate (String expstr) throws ParserException
    {
        double result;
        exp = expstr;
        expIdx = 0;
        Log.e("Вызвалили в - ", "evaluate");
        getToken();

        Log.e("token - ", token);

        if (token.equals(EOE))
        {
            handleErr(NOEXP); // нет выражения
        }

        Log.e("пошли в - evalExp2 из ", "evaluate");

        // Анализ и вычесления выражений
        result = evalExp2();

        if (!token.equals(EOE))
        {
            // Послежгяя лексема должна быть EOE
            handleErr(SYNTAX); // нет выражения
        }
        return result;
    }

    // Сложить или вычесть 2 терма
    private double evalExp2() throws ParserException
    {
        char op;
        double result;
        double partialResult;

        result = evalExp3();

        while((op = token.charAt(0)) == '+' || op == '-')
        {
            getToken();

            partialResult = evalExp3();

            switch(op) {
                case '-':
                    result = result - partialResult;
                    break;
                case '+':
                    result = result + partialResult;
                    break;
            }
        }
        return result;
    }

    // Умножить или разделить 2 фактора
    private double evalExp3() throws ParserException
    {
        char op;
        double result;
        double partialResult;

        result = evalExp4();

        while((op = token.charAt(0)) == '*' || op == '/' || op == '%')
        {
            getToken();
            partialResult = evalExp4();
            switch(op)
            {
                case '*':
                    result = result * partialResult;
                    break;
                case '/':
                    if(partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result = result / partialResult;
                    break;
                case '%':
                    if(partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result = result % partialResult;
                    break;
            }
        }
        return result;
    }

    //Выполнить возведение в степень
    private double evalExp4() throws ParserException
    {
        double result;
        double partialResult;
        double ex;
        int t;

        result = evalExp5();

        if(token.equals("^"))
        {
            getToken();
            partialResult = evalExp4();
            ex = result;
            if(partialResult == 0.0)
            {
                result = 1.0;
            }
            else
            {
                for (t = (int) partialResult - 1; t > 0; t--)
                {
                    result = result * ex;
                }
            }
        }
        return result;
    }



    //Определить унарные + или -
    private double evalExp5() throws ParserException
    {
        double result;
        String  op;

        op = "";
        if((tokType == DELIMITER) && token.equals("+") || token.equals("-"))
        {
            op = token;
            getToken();
        }

        result = evalExp6();

        if(op.equals("-"))
        {
            result = -result;
        }

        return result;
    }

    //Обработать вырадение в скобка
    private double evalExp6() throws ParserException
    {
        double result;
        Log.e("Находимся в - ","evalExp6");

        if(token.equals("("))
        {
            getToken();
            result = evalExp2();
            if(!token.equals(")"))
            {
                handleErr(UNBALPARENS);
            }
            getToken();
        }
        else
        {
            result = atom();
        }

        return result;
    }

    // Получить значение числа
    private double atom() throws ParserException
    {
        double result = 0.0;

        switch(tokType)
        {
            case NUMBER:
                try
                {
                    result = Double.parseDouble(token);
                }
                catch (NumberFormatException exc)
                {
                    handleErr(SYNTAX);
                }

                getToken();
                break;

            case VARIABLE:
                result = findVar(token);
                getToken();
                break;

            case FUNCTION:
                result = findVar(token);
                getToken();
                break;

            default:
                handleErr(SYNTAX);
                break;
        }
        return result;
    }

    // Handler an error
    private void handleErr(int error) throws ParserException
    {
        String[] err = {
                "Syntax Error",
                "Unbalanced Parentheses",
                "No Expression Present",
                "Division by Zero"
        };

        throw new ParserException(err[error]);
    }

    // Получить следующию лексему
    private void  getToken()
    {
        tokType = NONE;
        token = "";

        Log.e("exp.length = ", Integer.toString(exp.length()));

        // Проверить окончание выражения
        if (expIdx == exp.length())
        {
            Log.e("Log - ", "окончание выражения");

            token = EOE;
            return;
        }
        //Пропустить пробелы
        while (expIdx < exp.length() && Character.isWhitespace(exp.charAt(expIdx)))
        {
            Log.e("Log - ", "Пропустить пробелы");

            ++expIdx;
        }
        // Проверить окончание выражения
        if (expIdx == exp.length())
        {
            Log.e("Log - ", "окончание выражения");

            token = EOE;
            return;
        }
        if (isDelim(exp.charAt(expIdx)))
        {

            //Оператор
            token += exp.charAt(expIdx);
            expIdx++;
            tokType = DELIMITER;
            Log.e("Log - ", "нашли оператор " + token);
        }
        else if (Character.isLetter(exp.charAt(expIdx)))
        {

            //Переменная
            while (!isDelim(exp.charAt(expIdx)))
            {
                token += exp.charAt(expIdx);
                expIdx++;
                if (expIdx >= exp.length()) break;
            }
            if(isFunc(token))
            {
                Log.e("Log - ", "нашли функцию " + token);
                tokType = FUNCTION;
            }
            else
            {
                Log.e("Log - ", "нашли переменную " + token);
                tokType = VARIABLE;
            }
        }
        else if (Character.isDigit(exp.charAt(expIdx)))
        {
            //Число

            while (!isDelim(exp.charAt(expIdx)))
            {
                token += exp.charAt(expIdx);
                expIdx++;
                if (expIdx >= exp.length()) break;
            }
            Log.e("Log - ", "нашли число " + token);
            tokType = NUMBER;
        }
        else
        {
            Log.e("Log - ", "ашли Неизвестный символ");

            // Неизвестный символ
            token = EOE;
            return;
        }
    }

    // Возвращает true если является разделителем
    private boolean isDelim(char c)
    {


        if ((" +-/*%^=()".indexOf(c) != -1))
        {
            return true;
        }
        return false;
    }

    // Возвращает true если является разделителем
    private boolean isFunc(String c)
    {
        String func = "sin cos";

        if ((func.contains(c)))
        {
            return true;
        }
        return false;
    }

    // Возвращает true если является тригонометрическими
    private double findVar(String vname) throws ParserException
    {
        double result;
        String func;

        if(tokType == FUNCTION)
        {
            func = token;
            getToken();
            Log.e("findVar",token);
            result = evalExp2();
            getToken();
        }
        else
        {
            result = atom();
        }

        return result;

//            switch (token)
//            {
//                case "sin":
//
////                    result = result + 41;
//                    Log.e("result = ", "sin");
//                    break;
//                default:
//                    break;
//            }
//
//        if (!Character.isLetter(vname.charAt(0)))
//        {
//            handleErr(SYNTAX);
//            return 0.0;
//        }
//        return  vars[Character.toUpperCase(vname.charAt(0)) - 'A'];
    }

}
