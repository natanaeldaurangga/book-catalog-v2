package com.nael.catalog.domain;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "publisher")
public class Publisher extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_generator")
    @SequenceGenerator(name = "publisher_generator", sequenceName = "publisher_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "address")
    private String address;

    // karena kita sudah extend AbstractBaseEntity, maka kita tidak perlu lagi
    // @Column(name = "deleted", columnDefinition = "boolean default false")
    // private Boolean deleted;

    // jika relasi bersifat 2 arah (optional)
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
