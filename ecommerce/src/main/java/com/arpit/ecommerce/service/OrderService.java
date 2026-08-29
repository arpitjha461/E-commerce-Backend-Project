package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.request.UpdateOrderStatusRequestDTO;
import com.arpit.ecommerce.dto.response.OrderItemResponseDTO;
import com.arpit.ecommerce.dto.response.OrderResponseDTO;
import com.arpit.ecommerce.entity.*;
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

    @Transactional
    public OrderResponseDTO placeOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email: "+email));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()-> new CartEmptyException("Cart not found"));

        if (cart.getCartItems().isEmpty()){
            throw new CartEmptyException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem:cart.getCartItems()){
            OrderItem orderItem= new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            BigDecimal subTotal =cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(subTotal);
            order.getOrderItems().add(orderItem);
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        cartService.clearCart(user.getId());

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(order.getId());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItem orderItem: order.getOrderItems()){
            OrderItemResponseDTO itemDto = new OrderItemResponseDTO();

            itemDto.setProductId(orderItem.getProduct().getId());
            itemDto.setProductName(orderItem.getProduct().getName());
            itemDto.setQuantity(orderItem.getQuantity());
            itemDto.setPrice(orderItem.getPrice());

            BigDecimal subTotal = orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            itemDto.setSubtotal(subTotal);
            items.add(itemDto);
        }
        responseDTO.setItems(items);
        return responseDTO;
    }

    public List<OrderResponseDTO> getMyOrders(){
        Authentication authentication = SecurityContextHolder.getContext()
                                            .getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);
        List<OrderResponseDTO> responseList =new ArrayList<>();

        for (Order order : orders){
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrderId(order.getId());
            orderResponseDTO.setStatus(order.getStatus());
            orderResponseDTO.setCreatedAt(order.getCreatedAt());
            orderResponseDTO.setTotalAmount(order.getTotalAmount());

            List<OrderItemResponseDTO> items = new ArrayList<>();
            for (OrderItem orderItem: order.getOrderItems()){
                    OrderItemResponseDTO responseDTO = new OrderItemResponseDTO();
                    responseDTO.setProductId(orderItem.getProduct().getId());
                    responseDTO.setProductName(orderItem.getProduct().getName());
                    responseDTO.setQuantity(orderItem.getQuantity());
                    responseDTO.setPrice(orderItem.getPrice());

                    BigDecimal subTotal = orderItem.getPrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

                    responseDTO.setSubtotal(subTotal);
                    items.add(responseDTO);
            }
            orderResponseDTO.setItems(items);
            responseList.add(orderResponseDTO);
        }
        return responseList;
    }

    public OrderResponseDTO getOrderById(Long orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email: "+ email));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with Id: "+orderId));

        if (!order.getUser().getId().equals(user.getId())){
            throw new OrderNotFoundException("Order not found with id: "+ orderId);
        }

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(order.getId());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setCreatedAt(order.getCreatedAt());
        responseDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDTO> items = new ArrayList<>();

        for (OrderItem orderItem: order.getOrderItems()){
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();

            itemDTO.setProductId(orderItem.getProduct().getId());
            itemDTO.setProductName(orderItem.getProduct().getName());
            itemDTO.setQuantity(orderItem.getQuantity());
            itemDTO.setPrice(orderItem.getPrice());

            BigDecimal subtotal = orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            itemDTO.setSubtotal(subtotal);

            items.add(itemDTO);
        }
        responseDTO.setItems(items);
        return responseDTO;
    }

    public OrderResponseDTO cancelOrder(Long orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email: "+ email));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with order id : "+orderId));

        if (!order.getUser().getId().equals(user.getId())){
            throw new OrderNotFoundException("order not found with id: "+orderId);
        }

        if (!OrderStatus.PENDING.equals(order.getStatus())){
            throw new InvalidOrderStatusException("Order cannot be cancelled in status: "+order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(order.getId());
        responseDTO.setCreatedAt(order.getCreatedAt());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItem orderItem: order.getOrderItems()){
            OrderItemResponseDTO itemDTO =new OrderItemResponseDTO();
            itemDTO.setProductId(orderItem.getProduct().getId());
            itemDTO.setProductName(orderItem.getProduct().getName());
            itemDTO.setQuantity(orderItem.getQuantity());
            itemDTO.setPrice(orderItem.getPrice());

            BigDecimal subTotal = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            itemDTO.setSubtotal(subTotal);

            items.add(itemDTO);
        }
        responseDTO.setItems(items);
        return responseDTO;
    }

    private boolean isValidStatusTransition(OrderStatus currentStatus,OrderStatus newStatus){
        return switch (currentStatus){
            case PENDING -> newStatus == OrderStatus.PROCESSING;
            case PROCESSING -> newStatus == OrderStatus.CONFIRMED;
            case CONFIRMED -> newStatus == OrderStatus.SHIPPED;
            case SHIPPED -> newStatus == OrderStatus.OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> newStatus == OrderStatus.DELIVERED;
            default -> false;
        };
    }

    public OrderResponseDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequestDTO requestDTO){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with id: "+orderId));

        if (!isValidStatusTransition(order.getStatus(),requestDTO.getStatus())){
            throw new InvalidOrderStatusException(
                    "Invalid status transition from "+order.getStatus() + " to "+requestDTO.getStatus()
            );
        };
        order.setStatus(requestDTO.getStatus());
        orderRepository.save(order);


    }

    private OrderResponseDTO mapToOrderResponseDTO(Order order){
        OrderResponseDTO responseDTO = new OrderResponseDTO();

        responseDTO.setOrderId(order.getId());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setCreatedAt(order.getCreatedAt());
        responseDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDTO> items = new ArrayList<>();

        for

    }
}













