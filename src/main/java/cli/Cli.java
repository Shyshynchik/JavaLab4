package cli;

import converter.Converter;
import db.Dao.Impl.*;
import db.Entity.*;
import db.Utils.DropData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cli {
    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final static TabletDao tabletDao = new TabletDao();
    private final static LaptopDao laptopDao = new LaptopDao();
    private final static BrandDao brandDao = new BrandDao();
    private final static CpuDao cpuDao = new CpuDao();
    private final static OsDao osDao = new OsDao();
    private final static VideoCardDao videoCardDao = new VideoCardDao();



    public static void start() {
        System.out.println("Добро пожаловать в подобие командной строки");
        while (true) {
            try {
                System.out.println("""
                        Список команд:
                        1 : Работа с CSV
                        2 : Добавить данные
                        3 : Вывести все данные
                        4 : Вывести определенные данные
                        5 : Изменить объект
                        6 : Очистить БД""");

                int command = Integer.parseInt(reader.readLine());

                if (command == 1) {
                    workWithCSV();
                } else if (command == 2) {
                    addData();
                } else if (command == 3) {
                    findAllData();
                } else if (command == 4) {
                    findData();
                } else if (command == 5) {
//                    dirtyRead();
                } else if (command == 6) {
                    clearDb();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Неверная команда");
            }
        }
    }
    /**
     * 1 : Работа с CSV
     */
    private static void workWithCSV() {
        Converter converter = new Converter();
        converter.setCsvHandler();
        while (true) {
            try {
                System.out.println("""
                        Список команд:
                        1 : Перенести из CSV в БД
                        2 : Перенести из БД в CSV
                        3 : Завершить""");
                int command = Integer.parseInt(reader.readLine());
                if (command == 1) {
                    converter.fromCsvToSQL();
                    System.out.println("Запись завершена");
                } else if (command == 2) {
                    converter.fromSQLToCSV();
                    System.out.println("Запись завершена");
                } else {
                    converter.logClose();
                    System.out.println("Работа с csv завершена");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 2 : Добавить данные
     */
    private static void addData() {
        while (true) {
            try {
                System.out.println("""
                        Список команд:
                        1 : Добавить ноутбук
                        2 : Добавить планшет
                        3 : Завершить""");
                int command = Integer.parseInt(reader.readLine());
                if (command == 1) {
                    System.out.println(addLaptop());
                    System.out.println("Запись завершена");
                } else if (command == 2) {
                    System.out.println(addTablet());
                    System.out.println("Запись завершена");
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 2.1 : Добавить ноутбук
     */
    private static Laptop addLaptop() {
        System.out.println("""
                Введите через запятую следущие данные:
                Производителя
                Диагональ
                ОЗУ
                Процессор
                Видеокарту""");
        Laptop laptop = new Laptop();
        try {
            String[] command = reader.readLine().split(",");

            Brand brand = new Brand();
            brand.setName(command[0]);
            brand = brandDao.getOrAdd(brand);

            Cpu cpu = new Cpu();
            cpu.setName(command[3]);
            cpu = cpuDao.getOrAdd(cpu);

            VideoCard videoCard = new VideoCard();
            videoCard.setName(command[4]);
            videoCard = videoCardDao.getOrAdd(videoCard);

            laptop.setBrand(brand);
            laptop.setDiagonal(Integer.parseInt(command[1]));
            laptop.setRam(Integer.parseInt(command[2]));
            laptop.setCpu(cpu);
            laptop.setVideoCard(videoCard);

            laptopDao.save(laptop);
        } catch (IOException e) {
            System.out.println("Ошибка добавления");
        }

        return laptop;
    }

    /**
     * 2.2 : Добавить планшет
     */
    private static Tablet addTablet() {
        System.out.println("""
                Введите через запятую следущие данные:
                Производителя
                Диагональ
                ОЗУ
                Операционную систему
                Память""");
        Tablet tablet = new Tablet();
        try {
            String[] command = reader.readLine().split(",");

            Brand brand = new Brand();
            brand.setName(command[0]);
            brand = brandDao.getOrAdd(brand);

            Os os = new Os();
            os.setName(command[3]);
            os = osDao.getOrAdd(os);

            tablet.setBrand(brand);
            tablet.setDiagonal(Integer.parseInt(command[1]));
            tablet.setRam(Integer.parseInt(command[2]));
            tablet.setOs(os);
            tablet.setMemory(Integer.parseInt(command[4]));

            tabletDao.save(tablet);
        } catch (IOException e) {
            System.out.println("Ошибка добавления");
        }

        return tablet;
    }

    /**
     * 3 : Вывести все данные
     */
    private static void findAllData() {
        System.out.println(tabletDao.getAll());
        System.out.println(laptopDao.getAll());
    }

    /**
     * 4 : Вывести определенные данные
     */
    private static void findData() {
        try {
            System.out.println("""
                    Введите через запятую критерии поиска, если он не нужен введите -:
                    Брэнд
                    Процессор
                    Операционная система
                    Видеокарта
                    (Операционная система для планшетов, процессор и видеокарта для ноутбуков)""");
            String[] command = reader.readLine().split(",");
            if (command[1].equals("-") && command[2].equals("-") && command[3].equals("-")) {
                System.out.println(laptopDao.find(command[0], command[1], command[3]));
                System.out.println(tabletDao.find(command[0], command[2]));
            } else if (command[2].equals("-")) {
                System.out.println(laptopDao.find(command[0], command[1], command[3]));
            } else if (command[1].equals("-") && command[3].equals("-")) {
                System.out.println(tabletDao.find(command[0], command[2]));
            } else {
                System.out.println("Для подобного варианта не существует данных");
            }
        } catch (Exception e) {
            System.out.println("Ошибка вывода");
        }
    }

    /**
     * 5 : Изменить объект
     */
    private static void dirtyRead() {

        try {
            System.out.println("""
                    Что вы хотите изменить:
                    1 : Ноутбук
                    2 : Планшет
                    3 : Ничего""");
            int command = Integer.parseInt(reader.readLine());

            if (command == 1) {
                updateLaptop();
            } else if (command == 2) {
                updateTablet();
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5.1 Изменить ноутбук
     */
    private static void updateLaptop() {

    }

    /**
     * 5.2 Изменить планшет
     */
    private static void updateTablet() {

    }

    /**
     * 6 Очистить БД
     */
    private static void clearDb() {
        DropData.dropDataAndResetId();
    }

}
