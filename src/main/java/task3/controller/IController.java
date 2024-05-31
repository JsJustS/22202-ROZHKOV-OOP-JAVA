package task3.controller;

import task3.model.IModel;

public interface IController <O, M extends IModel>{
    <T> void execute(O operation, M model, T value);
}
