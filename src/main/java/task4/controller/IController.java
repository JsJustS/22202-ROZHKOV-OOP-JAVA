package task4.controller;

import task4.model.World;

public interface IController <O, W extends World> {
    <T> void execute(O operation, W world, T value);
}
