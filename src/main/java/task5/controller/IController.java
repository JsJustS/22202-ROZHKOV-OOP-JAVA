package task5.controller;

import task5.model.IModel;

public interface IController <O, M extends IModel>{
    <T> void execute(O operation, M model, T value);
}
