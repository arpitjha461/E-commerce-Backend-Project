package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.request.UpdateOrderStatusRequestDTO;
import com.arpit.ecommerce.dto.response.OrderItemResponseDTO;
import com.arpit.ecommerce.dto.response.OrderResponseDTO;
import com.arpit.ecommerce.entity.Cart;
import com.arpit.ecommerce.entity.CartItem;
import com.arpit.ecommerce.entity.Order;
import com.arpit.ecommerce.entity.OrderItem;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.enums.OrderStatus;
import com.arpit.ecommerce.exception.CartEmptyException;
import com.arpit.ecommerce.exception.InvalidOrderStatusException;
import com.arpit.ecommerce.exception.OrderNotFoundException;
import com.arpit.ecommerce.exception.UserNotFoundException;
import com.arpit.ecommerce.repository.CartRepository;
import com.arpit.ecommerce.repository.OrderRepository;
import com.arpit.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;


    // =========================
    // PLACE ORDER
    // =========================

    @Transactional
    public OrderResponseDTO placeOrder() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        ));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new CartEmptyException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            BigDecimal subtotal =
                    cartItem.getProduct()
                            .getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()
                                    )
                            );

            totalAmount = totalAmount.add(subtotal);

            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        cartService.clearCart(user.getId());

        return mapToOrderResponseDTO(order);
    }


    // =========================
    // GET MY ORDERS
    // =========================

    public List<OrderResponseDTO> getMyOrders() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(mapToOrderResponseDTO(order));
        }

        return responseList;
    }


    // =========================
    // GET ORDER BY ID
    // =========================

    public OrderResponseDTO getOrderById(Long orderId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        ));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with id: " + orderId
                        ));

        // Make sure the order belongs to the logged-in user
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderNotFoundException(
                    "Order not found with id: " + orderId
            );
        }

        return mapToOrderResponseDTO(order);
    }


    // =========================
    // CANCEL ORDER
    // =========================

    public OrderResponseDTO cancelOrder(Long orderId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        ));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with order id: " + orderId
                        ));

        // Make sure the order belongs to the logged-in user
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderNotFoundException(
                    "Order not found with id: " + orderId
            );
        }

        // Only PENDING orders can be cancelled
        if (!OrderStatus.PENDING.equals(order.getStatus())) {
            throw new InvalidOrderStatusException(
                    "Order cannot be cancelled in status: "
                            + order.getStatus()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return mapToOrderResponseDTO(order);
    }


    // =========================
    // VALID STATUS TRANSITION
    // =========================

    private boolean isValidStatusTransition(
            OrderStatus currentStatus,
            OrderStatus newStatus) {

        return switch (currentStatus) {

            case PENDING ->
                    newStatus == OrderStatus.PROCESSING;

            case PROCESSING ->
                    newStatus == OrderStatus.CONFIRMED;

            case CONFIRMED ->
                    newStatus == OrderStatus.SHIPPED;

            case SHIPPED ->
                    newStatus == OrderStatus.OUT_FOR_DELIVERY;

            case OUT_FOR_DELIVERY ->
                    newStatus == OrderStatus.DELIVERED;

            default ->
                    false;
        };
    }


    // =========================
    // UPDATE ORDER STATUS
    // =========================

    public OrderResponseDTO updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequestDTO requestDTO) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with id: " + orderId
                        ));

        if (!isValidStatusTransition(
                order.getStatus(),
                requestDTO.getStatus())) {
            throw new InvalidOrderStatusException("Invalid status transition from "+ order.getStatus()
                            + " to " + requestDTO.getStatus());
        }

        order.setStatus(requestDTO.getStatus());
        orderRepository.save(order);

        return mapToOrderResponseDTO(order);
    }


    // =========================
    // MAP ORDER → RESPONSE DTO
    // =========================

    private OrderResponseDTO mapToOrderResponseDTO(Order order) {

        OrderResponseDTO responseDTO = new OrderResponseDTO();

        responseDTO.setOrderId(order.getId());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setCreatedAt(order.getCreatedAt());
        responseDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDTO> items = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {

            OrderItemResponseDTO itemResponseDTO = new OrderItemResponseDTO();

            itemResponseDTO.setProductId( orderItem.getProduct().getId());

            itemResponseDTO.setProductName(
                    orderItem.getProduct().getName()
            );

            itemResponseDTO.setPrice(
                    orderItem.getPrice()
            );

            itemResponseDTO.setQuantity(
                    orderItem.getQuantity()
            );

            BigDecimal subtotal =
                    orderItem.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            orderItem.getQuantity()
                                    )
                            );

            itemResponseDTO.setSubtotal(subtotal);

            items.add(itemResponseDTO);
        }

        responseDTO.setItems(items);

        return responseDTO;
    }
}