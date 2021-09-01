package io.qiot.manufacturing.datacenter.plantmanager.domain.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MachineryDTO {
    public UUID id;
    @JsonProperty(value = "factory_id")
    public UUID factoryId;
    public String serial;
    public String name;
    @JsonProperty(value = "registered_on")
    public Instant registeredOn;

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
        MachineryDTO other = (MachineryDTO) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MachineryDTO [id=");
        builder.append(id);
        builder.append(", factoryId=");
        builder.append(factoryId);
        builder.append(", serial=");
        builder.append(serial);
        builder.append(", name=");
        builder.append(name);
        builder.append(", registeredOn=");
        builder.append(registeredOn);
        builder.append("]");
        return builder.toString();
    }

}