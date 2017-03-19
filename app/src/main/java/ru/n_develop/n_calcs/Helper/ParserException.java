package ru.n_develop.n_calcs.Helper;

import android.util.Log;

/**
 * Этот модуль сождержит рекурсивно-последовательный анализатор, который не использует переменные
 */


//Класс исключений для ошибок анализатора
public class ParserException extends Exception
{
    String errStr;
    public ParserException(String str)
    {
        errStr = str;
    }
    public String toString()
    {
        return errStr;
    }

}
