package com.byultudy.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(final String path) throws IOException {
        return lineReadTemplate(path, (line, value)-> value + Integer.parseInt(line), 0);
    }

    public Integer calcMultiply(final String path) throws IOException {
        return lineReadTemplate(path, (line, value)-> value * Integer.parseInt(line), 1);
    }


    public String concatenate(final String path) throws IOException {
        return lineReadTemplate(path, (line, value)-> value + Integer.parseInt(line), "");
    }

    public <T> T lineReadTemplate(String path, LineCallback<T> callBack, T initVal) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            T res = initVal;
            String line;
            while((line = br.readLine()) != null) {
                res = callBack.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
