package io.cucumber.cucumberexpressions;

import java.util.Arrays;

public class SingleTransformer<T> implements ParameterTransformer<T> {
    private final Function<String, T> function;

    public SingleTransformer(Function<String, T> function) {
        this.function = function;
    }

    @Override
    public T transform(String[] groupValues) {
        if (groupValues == null) return null;
        String arg = null;
        for (String groupValue : groupValues) {
            if (groupValue != null) {
                if (arg != null)
                    throw new CucumberExpressionException(String.format("Single transformer unexpectedly matched 2 values - \"%s\" and \"%s\"", arg, groupValue));
                arg = groupValue;
            }
        }
        if (arg == null) return null;
        try {
            return function.apply(arg);
        } catch (Throwable e) {
            throw new CucumberExpressionException(String.format("Single transformer could not transform \"%s\"", Arrays.toString(groupValues)), e);
        }
    }
}
