package com.unichristus.libraryapi.domain.category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "categories")

public class Category {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
@Column(name = "id", nullable = false)
private UUID id;

@Column(name = "name", nullable = false, unique = true ,length = 120)
private String name;

@Column(name = "description", length = 255)
private String description;

@CreationTimestamp
@Column(name = "created_at", updatable = false, nullable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(name = "updated_at", nullable = false)
private LocalDateTime updatedAt;
}
