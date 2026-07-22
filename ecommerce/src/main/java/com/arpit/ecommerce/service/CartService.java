package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.CartItemResponseDTO;
import com.arpit.ecommerce.dto.CartResponseDTO;
import com.arpit.ecommerce.entity.Cart;
import com.arpit.ecommerce.entity.CartItem;
import com.arpit.ecommerce.entity.Product;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.exception.ProductNotFoundException;
import com.arpit.ecommerce.exception.UserNotFoundException;
import com.arpit.ecommerce.repository.CartItemRepository;
import com.arpit.ecommerce.repository.CartRepository;
import com.arpit.ecommerce.repository.ProductRepository;
import com.arpit.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public String addToCart(Long userId, Long productId, Integer quantity){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found having id: "+ userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found having id: "+ productId));
//        Cart cart = cartRepository.findByUser(user)
//                .orElseGet(()-> {
//                   Cart newCart =  new Cart();
//                   newCart.setUser(user);
//                   return cartRepository.save(newCart);
//                });
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        Cart cart;
        if (optionalCart.isPresent()){
            cart = optionalCart.get();
        }else {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart,product);
        CartItem cartItem;
        if (optionalCartItem.isPresent()){
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            cartItemRepository.save(cartItem);
        }else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
        return "Product added to the cart successfully";
    }

    public CartResponseDTO getCart(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found having id: "+userId));
        Optional<Cart> optionalCart =  cartRepository.findByUser(user);

        if (optionalCart.isEmpty()){
            CartResponseDTO responseDTO = new CartResponseDTO();
            responseDTO.setCartId(null);
            responseDTO.setTotalItems(0);
            responseDTO.setTotalAmount(0.0);
            responseDTO.setItems(new ArrayList<>());
            return responseDTO;
        }
        Cart cart = optionalCart.get();
        List<CartItem> cartItems = cart.getCartItems();

        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        List<CartItemResponseDTO> items = new ArrayList<>();

        int totalItems =0;
        double totalAmount =0.0;

        for (CartItem cartItem : cartItems){
            CartItemResponseDTO itemDTO = new CartItemResponseDTO();
            itemDTO.setProductId(cartItem.getId());
            itemDTO.setProductName(cartItem.getProduct().getName());
            itemDTO.setQuantity(cartItem.getQuantity());
            itemDTO.setPrice(cartItem.getProduct().getPrice());

        }


    }
}





















