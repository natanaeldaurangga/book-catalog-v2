package com.nael.catalog.domain;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
@Table(indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")// mengeset index baru untuk table author
})
@Where(clause = "deleted=false or deleted is null")
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
public abstract class AbstractBaseEntity implements Serializable {

    @Column(name = "secure_id", nullable = false, unique = true)
    private String secureId= UUID.randomUUID().toString();

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

}
