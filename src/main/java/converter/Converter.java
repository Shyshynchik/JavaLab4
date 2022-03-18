package converter;

import csv_handler.CSVHandler;
import csv_handler.Impl.CSVHandlerImpl;
import db.Dao.Impl.*;
import db.Entity.*;
import device.DeviceImpl;
import device.impl.LaptopImpl;
import device.impl.TabletImpl;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    private CSVHandler csvHandler;
    private ArrayList<DeviceImpl> deviceArrayList;
    private final BrandDao brandDao;
    private final CpuDao cpuDao;
    private final LaptopDao laptopDao;
    private final OsDao osDao;
    private final TabletDao tabletDao;
    private final VideoCardDao videoCardDao;

    public Converter() {
        this.brandDao = new BrandDao();
        this.cpuDao = new CpuDao();
        this.laptopDao = new LaptopDao();
        this.osDao = new OsDao();
        this.tabletDao = new TabletDao();
        this.videoCardDao = new VideoCardDao();
    }

    public void setCsvHandler() {
        csvHandler = new CSVHandlerImpl();
        deviceArrayList = csvHandler.read();
    }

    public void fromCsvToSQL() {
        for (DeviceImpl el: deviceArrayList) {
            if (el.getType().equals("tablet")) {
                addTablet((TabletImpl) el);
            } else {
                addLaptop((LaptopImpl) el);
            }
        }
    }

    private void addTablet(TabletImpl tabletModel) {
        Brand brand = new Brand();
        brand.setName(tabletModel.getBrand());
        brand = brandDao.getOrAdd(brand);

        Os os = new Os();
        os.setName(tabletModel.getOs());
        os = osDao.getOrAdd(os);

        Tablet tablet = new Tablet();
        tablet.setBrand(brand);
        tablet.setDiagonal(Integer.parseInt(tabletModel.getDiagonal()));
        tablet.setRam(Integer.parseInt(tabletModel.getRam()));
        tablet.setOs(os);
        tablet.setMemory(Integer.parseInt(tabletModel.getRam()));
        tabletDao.save(tablet);
    }

    private void addLaptop(LaptopImpl laptopModel) {
        Brand brand = new Brand();
        brand.setName(laptopModel.getBrand());
        brand = brandDao.getOrAdd(brand);

        Cpu cpu = new Cpu();
        cpu.setName(laptopModel.getCpu());
        cpu = cpuDao.getOrAdd(cpu);

        VideoCard videoCard = new VideoCard();
        videoCard.setName(laptopModel.getVideoCard());
        videoCard = videoCardDao.getOrAdd(videoCard);

        Laptop laptop = new Laptop();
        laptop.setBrand(brand);
        laptop.setDiagonal(Integer.parseInt(laptopModel.getDiagonal()));
        laptop.setRam(Integer.parseInt(laptopModel.getRam()));
        laptop.setCpu(cpu);
        laptop.setVideoCard(videoCard);
        laptopDao.save(laptop);
    }

    public void fromSQLToCSV() {
        csvHandler.saveToCsv(fromSqlToArray());
    }

    private ArrayList<DeviceImpl> fromSqlToArray() {
        ArrayList<DeviceImpl> devices = new ArrayList<>();
        devices.addAll(getLaptopSQL());
        devices.addAll(getTabletSQL());
        return devices;
    }

    private ArrayList<TabletImpl> getTabletSQL() {
        List<Tablet> tablets = tabletDao.getAll();
        ArrayList<TabletImpl> arrayList = new ArrayList<>();

        for (Tablet tablet : tablets) {
            arrayList.add(convertTablet(tablet));
        }
        return arrayList;
    }

    private TabletImpl convertTablet(Tablet tablet) {
        TabletImpl tableImpl = new TabletImpl();

        tableImpl.setData(tablet);

        return tableImpl;
    }

    private ArrayList<LaptopImpl> getLaptopSQL() {
        List<Laptop> laptops = laptopDao.getAll();
        ArrayList<LaptopImpl> arrayList = new ArrayList<>();

        for (Laptop laptop : laptops) {
            arrayList.add(convertLaptop(laptop));
        }
        return arrayList;
    }

    private LaptopImpl convertLaptop(Laptop laptop) {
        LaptopImpl laptopImpl = new LaptopImpl();

        laptopImpl.setData(laptop);

        return laptopImpl;
    }

    public void logClose() {
        csvHandler.logClose();
    }
}
