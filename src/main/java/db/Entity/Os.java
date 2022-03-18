package db.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "os", schema = "device", catalog = "")
public class Os {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "os", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Collection<Tablet> getTabletsById() {
        return tabletsById;
    }

    public void setTabletsById(Collection<Tablet> tabletsById) {
        this.tabletsById = tabletsById;
    }

    @Override
    public String toString() {
        return "OsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
