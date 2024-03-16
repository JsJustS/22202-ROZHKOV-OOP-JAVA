package task4.service.factory;

import task4.controller.FabricController;
import task4.model.World;
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
    private final FabricController controller;

    // storages
    private final CarsStorage carsStorage;
    private final BodyStorage bodyStorage;
    private final MotorStorage motorStorage;
    private final AccessoryStorage accessoryStorage;

    // suppliers
    private final ThreadPool bodySupplierThreadPool;
    private final ThreadPool motorSupplierThreadPool;
    private final ThreadPool accessorySupplierThreadPool;

    public Factory(World world, Config cfg) {
        this.controller = new FabricController();

        // Storages
        this.carsStorage = new CarsStorage(world, cfg.getStorageCarSize());
        this.bodyStorage = new BodyStorage(world, cfg.getStorageBodySize());
        this.motorStorage = new MotorStorage(world, cfg.getStorageMotorSize());
        this.accessoryStorage = new AccessoryStorage(world, cfg.getStorageAccessorySize());

        // ThreadPools
        this.bodySupplierThreadPool = new ThreadPool(cfg.getBodySuppliersCount(), "bodySupplier");
        this.bodySupplierThreadPool.addTaskForAll(new BodySupplier(this.bodyStorage, world));

        this.motorSupplierThreadPool = new ThreadPool(cfg.getMotorSuppliersCount(), "motorSupplier");
        this.motorSupplierThreadPool.addTaskForAll(new MotorSupplier(this.motorStorage, world));

        this.accessorySupplierThreadPool = new ThreadPool(cfg.getAccessorySuppliersCount(), "accessorySupplier");
        this.accessorySupplierThreadPool.addTaskForAll(new AccessorySupplier(this.accessoryStorage, world));
    }
}
