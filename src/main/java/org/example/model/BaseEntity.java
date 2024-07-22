package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(updatable = false, columnDefinition = "uuid")
    private final UUID id;

    public BaseEntity() {
        this(null);
    }

    @JsonCreator
    public BaseEntity(@JsonProperty("id") final UUID id) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
    }
}
