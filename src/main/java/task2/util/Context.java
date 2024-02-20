package task2.util;

import task2.error.RuntimeContextException;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class Context {
    private final Stack<Double> STACK;
    private final HashMap<String, Double> varTable;

    public Context() {
        this.STACK = new Stack<>();
        this.varTable = new HashMap<>();
    }

    public void setVar(String name, double value) {
        this.varTable.put(name, value);
    }

    public boolean hasVar(String name) {
        return varTable.containsKey(name);
    }

    public double getVar(String name) {
        return this.varTable.get(name);
    }

    public void push(double number) {
        this.STACK.push(number);
    }

    public double pop() throws RuntimeContextException {
        try {
            return this.STACK.pop();
        } catch (EmptyStackException e) {
            throw new RuntimeContextException("Could not pop because stack is already empty.");
        }
    }

    public double peek() throws RuntimeContextException {
        try {
            return this.STACK.peek();
        } catch (EmptyStackException e) {
            throw new RuntimeContextException("Could not peek because stack is empty.");
        }
    }
}
