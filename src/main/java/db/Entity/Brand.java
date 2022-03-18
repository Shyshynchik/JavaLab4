package db.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "brand", schema = "device", catalog = "")
public class Brand {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Laptop> laptopsById;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Tablet> tabletsById;

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

    public Collection<Tablet> getTabletsById() {
        return tabletsById;
    }

    public void setTabletsById(Collection<Tablet> tabletsById) {
        this.tabletsById = tabletsById;
    }

    @Override
    public String toString() {
        return "BrandEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
