package task4.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.AccessoryPart;
import task4.service.factory.part.BodyPart;
import task4.service.factory.part.Car;
import task4.service.factory.part.MotorPart;
import task4.service.factory.storage.AccessoryStorage;
import task4.service.factory.storage.BodyStorage;
import task4.service.factory.storage.CarsStorage;
import task4.service.factory.storage.MotorStorage;
import task4.service.factory.supplier.AccessorySupplier;
import task4.service.factory.supplier.BodySupplier;
import task4.service.factory.supplier.MotorSupplier;
import task4.service.threadpool.ThreadPool;
import task4.util.Config;

public class Factory {
    private final World world;
    private final FabricController controller;

    private final boolean shouldLog;
    private static final Logger LOGGER = LoggerFactory.getLogger(Factory.class);


    // storages
    private final CarsStorage carsStorage;
    private final BodyStorage bodyStorage;
    private final MotorStorage motorStorage;
    private final AccessoryStorage accessoryStorage;

    // suppliers
    private final ThreadPool bodySupplierThreadPool;
    private final ThreadPool motorSupplierThreadPool;
    private final ThreadPool accessorySupplierThreadPool;

    // other
    private final Thread storageControllerThread;
    private final ThreadPool workersThreadPool;
    private final ThreadPool dealersThreadPool;

    public Factory(World world, Config cfg) {
        this.world = world;
        this.controller = new FabricController();
        this.shouldLog = cfg.isLogging();

        // Storages
        this.carsStorage = new CarsStorage(world, controller, cfg.getStorageCarSize());
        this.bodyStorage = new BodyStorage(world, controller,cfg.getStorageBodySize());
        this.motorStorage = new MotorStorage(world, controller, cfg.getStorageMotorSize());
        this.accessoryStorage = new AccessoryStorage(world, controller, cfg.getStorageAccessorySize());

        // ThreadPools
        this.bodySupplierThreadPool = new ThreadPool(cfg.getBodySuppliersCount(), "bodySupplier");
        for (int i = 0; i < cfg.getBodySuppliersCount(); ++i) {
            this.bodySupplierThreadPool.addTask(new BodySupplier(this.bodyStorage, world, controller));
        }

        this.motorSupplierThreadPool = new ThreadPool(cfg.getMotorSuppliersCount(), "motorSupplier");
        for (int i = 0; i < cfg.getMotorSuppliersCount(); ++i) {
            this.motorSupplierThreadPool.addTask(new MotorSupplier(this.motorStorage, world, controller));
        }

        this.accessorySupplierThreadPool = new ThreadPool(cfg.getAccessorySuppliersCount(), "accessorySupplier");
        for (int i = 0; i < cfg.getAccessorySuppliersCount(); ++i) {
            this.accessorySupplierThreadPool.addTask(new AccessorySupplier(this.accessoryStorage, world, controller));
        }

        this.workersThreadPool = new ThreadPool(cfg.getWorkersCount(), "worker");
        this.dealersThreadPool = new ThreadPool(cfg.getDealersCount(), "dealer");
        for (int i = 0; i < cfg.getDealersCount(); ++i) {
            this.dealersThreadPool.addTask(new Dealer<>(world, this.carsStorage, cfg.isLogging()));
        }

        this.storageControllerThread = new Thread(()->{

            while (Thread.currentThread().isAlive()) {
                synchronized (this.carsStorage) {
                    if (this.carsStorage.isEmpty()) {
                        for (int i = 0; i < cfg.getWorkersCount(); ++i) {
                            this.workersThreadPool.addTask(this::order);
                        }
                        this.controller.execute(FabricController.Operation.UPD_TASK_WAITING, this.world, this.workersThreadPool.getBusyness());
                    }
                    try {this.carsStorage.wait();} catch (InterruptedException ignored) {}
                }
            }
        }, "storageController");

    }

    public void start() {
        this.bodySupplierThreadPool.start();
        this.motorSupplierThreadPool.start();
        this.accessorySupplierThreadPool.start();

        this.workersThreadPool.start();
        this.dealersThreadPool.start();

        this.storageControllerThread.start();
    }

    private void order() {
        // This Runnable was pulled from taskQueue, update model
        this.controller.execute(FabricController.Operation.UPD_TASK_WAITING, this.world, this.workersThreadPool.getBusyness());

        BodyPart bodyPart;
        MotorPart motorPart = null;
        AccessoryPart accessoryPart = null;
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
