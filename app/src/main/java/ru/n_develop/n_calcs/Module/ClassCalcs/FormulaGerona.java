package ru.n_develop.n_calcs.Module.ClassCalcs;

import java.util.ArrayList;

/**
 * Created by dim90 on 17.03.2017.
 * S=sqrt(p*(p-a)*(p-b)*(p-c)) где p = 1/2(a+b+c)
 */

public class FormulaGerona extends Base
{
    public ArrayList<String> getVariables ()
    {
        ArrayList<String> varibales = new ArrayList<String>();
        varibales.add("a");
        varibales.add("b");
        varibales.add("c");

        return varibales;
    }
}
