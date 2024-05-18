package blimop.tech.challenge.repository;

import blimop.tech.challenge.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
}
