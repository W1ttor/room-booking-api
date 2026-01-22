package dev.abraao.roombookingapi.domain.room.repository;

import dev.abraao.roombookingapi.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
