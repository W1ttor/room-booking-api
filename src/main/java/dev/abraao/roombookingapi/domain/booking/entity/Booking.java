package dev.abraao.roombookingapi.domain.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TABLE_BOOKING")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;


}
