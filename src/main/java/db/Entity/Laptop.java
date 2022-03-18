package db.Entity;

import javax.persistence.*;

@Entity
@Table(name = "laptop", schema = "device", catalog = "")
public class Laptop {

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
    @JoinColumn(name = "cpu", referencedColumnName = "id", nullable = false)
    private Cpu cpu;

    @ManyToOne
    @JoinColumn(name = "video_card", referencedColumnName = "id", nullable = false)
    private VideoCard videoCard;

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

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public VideoCard getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(VideoCard videoCard) {
        this.videoCard = videoCard;
    }

    @Override
    public String toString() {
        return "Ноутбук {" +
                "Id = " + id +
                ", Производитель = " + brand.getName() +
                ", Диагональ = " + diagonal +
                ", ОЗУ = " + ram +
                ", Процессор = " + cpu.getName() +
                ", Видеокарта = " + videoCard.getName() +
                "}\n";
    }
}
