package io.qiot.manufacturing.datacenter.plantmanager.domain.pojo;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Entity
@Cacheable
@Table(name = "factory", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "serial" }),
        @UniqueConstraint(columnNames = { "name" }) })
@RegisterForReflection
public class FactoryBean extends PanacheEntityBase {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Type(type = "pg-uuid")
    public UUID id;
    @Column(nullable = false, unique = true)
    public String serial;
    @Column(nullable = false, unique = true)
    public String name;
    @Column(nullable = false)
    public boolean active = true;
    @Column(name = "registered_on", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    public Instant registeredOn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "factory_id")
    public Set<MachineryBean> machineries;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FactoryBean other = (FactoryBean) obj;
        return id == other.id;
    }

}