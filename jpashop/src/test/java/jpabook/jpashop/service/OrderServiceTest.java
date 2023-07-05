package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.sevice.OrderService;

@Transactional
@SpringBootTest
public class OrderServiceTest {
 
	@Autowired
	EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void 상품주문() throws Exception {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		em.persist(member);
		
		Book book = new Book();
		book.setName("시골 jpa");
		book.setPrice(10000);
		book.setStockQuantity(10);
		em.persist(book);
		
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		Order getOrder = orderRepository.findOne(orderId);
		
		Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
		Assertions.assertThat(1).isEqualTo(getOrder.getOrderItems().size());
		Assertions.assertThat(10000*orderCount).isEqualTo(getOrder.getTotalPrice());
		Assertions.assertThat(8).isEqualTo(book.getStockQuantity());
	}
	
	@Test 
	public void 상품주문_재고수량초과() throws Exception {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		em.persist(member);
		
		Item book = new Book();
		book.setName("시골 jpa");
		book.setPrice(10000);
		book.setStockQuantity(10);
		em.persist(book);
		
		int orderCount = 11;
		
		Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount))
												.isInstanceOf(NotEnoughStockException.class);
	}
	
	@Test
	public void 주문취소() throws Exception {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		em.persist(member);
		
		Item book = new Book();
		book.setName("시골 jpa");
		book.setPrice(10000);
		book.setStockQuantity(10);
		em.persist(book);
		
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		orderService.cancelOrder(orderId);
		
		Order getOrder = orderRepository.findOne(orderId);
		
		Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
		Assertions.assertThat(10).isEqualTo(book.getStockQuantity());
	}
}
