package ua.aleks4ay.kiyv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.aleks4ay.kiyv.domain.model.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

/*    @Query("DELETE FROM Order o WHERE o.id=?1")
    int delete(int id);*/

    void deleteById(int id);

}
