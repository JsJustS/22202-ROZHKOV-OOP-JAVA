package task4.service.factory;

import task4.model.World;
import task4.service.factory.storage.AccessoryStorage;
import task4.service.factory.storage.BodyStorage;
import task4.service.factory.storage.CarsStorage;
import task4.service.factory.storage.MotorStorage;
import task4.service.factory.supplier.BodySupplier;
import task4.service.factory.supplier.MotorSupplier;
import task4.util.Config;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Factory {
    // storages
    private final CarsStorage carsStorage;
    private final BodyStorage bodyStorage;
    private final MotorStorage motorStorage;
    private final AccessoryStorage accessoryStorage;

    // suppliers
    private final Thread bodySupplierThread;
    private final Thread motorSupplierThread;
    private final ThreadPoolExecutor accessorySuppliersPool;

    public Factory(World world, Config cfg) {

        this.carsStorage = new CarsStorage(world, cfg.getStorageCarSize());
        this.bodyStorage = new BodyStorage(world, cfg.getStorageBodySize());
        this.motorStorage = new MotorStorage(world, cfg.getStorageMotorSize());
        this.accessoryStorage = new AccessoryStorage(world, cfg.getStorageAccessorySize());

        this.bodySupplierThread = new Thread(new BodySupplier(this.bodyStorage, world), "bodySupplier");
        this.motorSupplierThread = new Thread(new MotorSupplier(this.motorStorage, world), "motorSupplier");
        this.accessorySuppliersPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(cfg.getAccessorySuppliersCount());
    }
}
