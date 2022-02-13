package springdata.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.datajpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
