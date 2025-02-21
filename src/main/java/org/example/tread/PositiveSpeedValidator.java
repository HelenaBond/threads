package org.example.tread;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

class PositiveSpeedValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        int speed = Integer.parseInt(value);
        if (speed <= 0) {
            throw new ParameterException("Параметр " + name + " должен быть больше 0, но получено: " + value);
        }
    }
}
