package db.Entity;

import javax.persistence.*;

@Entity
@Table(name = "tablet", schema = "device", catalog = "")
public class Tablet {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "brand", referencedColumnName = "id", nullable = false)
    private Brand brand;

    @Column(name = "diagonal", nullable = false)
    private int diagonal;

    @Column(name = "ram", nullable = false)
    private int ram;

    @ManyToOne
    @JoinColumn(name = "os", referencedColumnName = "id", nullable = false)
    private Os os;

    @Column(name = "memory", nullable = false)
    private int memory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(int diagonal) {
        this.diagonal = diagonal;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Планшет {" +
                "Id = " + id +
                ", Производитель = " + brand.getName() +
                ", Диагональ = " + diagonal +
                ", ОЗУ = " + ram +
                ", Операционная система = " + os.getName() +
                ", Память = " + memory +
                "}\n";
    }
}
