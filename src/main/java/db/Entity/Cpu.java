package db.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cpu", schema = "device", catalog = "")
public class Cpu {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "cpu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Laptop> laptopsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Laptop> getLaptopsById() {
        return laptopsById;
    }

    public void setLaptopsById(Collection<Laptop> laptopsById) {
        this.laptopsById = laptopsById;
    }

    @Override
    public String toString() {
        return "CpuEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
