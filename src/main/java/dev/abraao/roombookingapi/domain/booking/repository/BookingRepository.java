package dev.abraao.roombookingapi.domain.booking.repository;

import dev.abraao.roombookingapi.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
