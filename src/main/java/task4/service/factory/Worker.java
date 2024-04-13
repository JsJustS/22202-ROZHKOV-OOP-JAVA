package task4.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.AccessoryPart;
import task4.service.factory.part.BodyPart;
import task4.service.factory.part.Car;
import task4.service.factory.part.MotorPart;
import task4.service.factory.storage.*;
import task4.service.threadpool.ThreadPool;

public class Worker implements Runnable {
    private final World world;
    private final FabricController controller;

    private final CarsStorage carsStorage;
    private final BodyStorage bodyStorage;
    private final MotorStorage motorStorage;
    private final AccessoryStorage accessoryStorage;

    private final ThreadPool workersThreadPool;
    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);
    private final boolean shouldLog;

    public Worker(World world, FabricController controller, CarsStorage carsStorage, BodyStorage bodyStorage,
                  MotorStorage motorStorage, AccessoryStorage accessoryStorage, ThreadPool workerPool, boolean log) {
        this.world = world;
        this.controller = controller;
        this.carsStorage = carsStorage;
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
        this.workersThreadPool = workerPool;
        this.shouldLog = log;
    }

    @Override
    public void run() {
        this.craft();
    }

    private void craft() {
        this.controller.execute(FabricController.Operation.UPD_TASK_WAITING, this.world, this.workersThreadPool.getQueueSize());

        BodyPart bodyPart;
        MotorPart motorPart;
        AccessoryPart accessoryPart;
        synchronized (this.bodyStorage) {
            while (this.bodyStorage.isEmpty()) {
                try {this.bodyStorage.wait();} catch (InterruptedException ignored) {}
            }
            bodyPart = this.bodyStorage.grabFirst();
            this.bodyStorage.notifyAll();
        }

        synchronized (this.motorStorage) {
            while (this.motorStorage.isEmpty()) {
                try {this.motorStorage.wait();} catch (InterruptedException ignored) {}
            }
            motorPart = this.motorStorage.grabFirst();
            this.motorStorage.notifyAll();
        }

        synchronized (this.accessoryStorage) {
            while (this.accessoryStorage.isEmpty()) {
                try {this.accessoryStorage.wait();} catch (InterruptedException ignored) {}
            }
            accessoryPart = this.accessoryStorage.grabFirst();
            this.accessoryStorage.notifyAll();
        }

        this.controller.execute(FabricController.Operation.UPD_CAR_CRAFTED, this.world, 1);
        Car car = new Car(bodyPart, motorPart, accessoryPart);

        synchronized (this.carsStorage) {
            while (this.carsStorage.isFull()) {
                try{this.carsStorage.wait();} catch (InterruptedException ignored) {}
            }
            this.carsStorage.store(car);
            this.carsStorage.notifyAll();
        }
    }
}
