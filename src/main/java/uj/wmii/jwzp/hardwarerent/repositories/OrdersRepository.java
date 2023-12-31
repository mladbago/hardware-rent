package uj.wmii.jwzp.hardwarerent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.wmii.jwzp.hardwarerent.data.MyUser;
import uj.wmii.jwzp.hardwarerent.data.Order;
import uj.wmii.jwzp.hardwarerent.data.OrderDetails;
import uj.wmii.jwzp.hardwarerent.data.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository  extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(MyUser user);
    Optional<Order> findByUserAndId(MyUser user, Long id);

    List<Order> findAllByOrderStatusAndUser(OrderStatus orderStatus, MyUser user);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}
