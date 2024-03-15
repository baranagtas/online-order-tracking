package com.example.onlineordertrackingsystem.service.impl;

import com.example.onlineordertrackingsystem.dto.OrderDto;
import com.example.onlineordertrackingsystem.dto.OrderItemDto;
import com.example.onlineordertrackingsystem.dto.PaymentDto;
import com.example.onlineordertrackingsystem.dto.ProductDto;
import com.example.onlineordertrackingsystem.enums.OrderStatus;
import com.example.onlineordertrackingsystem.model.Order;
import com.example.onlineordertrackingsystem.model.OrderItem;
import com.example.onlineordertrackingsystem.repository.OrderRepository;
import com.example.onlineordertrackingsystem.service.OrderService;
import com.example.onlineordertrackingsystem.service.PaymentService;
import com.example.onlineordertrackingsystem.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService,PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.paymentService=paymentService;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        // Sipariş bilgilerini al
        List<OrderItemDto> orderItems = orderDto.getOrderItems();
        // Yeni bir sipariş oluştur
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.NEW); // Yeni siparişin varsayılan durumu

        // Siparişteki her ürün için
        List<String> productUuids = new ArrayList<>(); // Ürün UUID'lerini tutacak liste
        for (OrderItemDto itemDto : orderItems) {
            ProductDto productDto = productService.getProductByUuid(itemDto.getProductUuid());
            // Ürünü kontrol et ve stoğu azalt
            if (productDto.getStockQuantity() < itemDto.getQuantity()) {
                // Stoğu yeterli değilse işlemi durdur ve hata fırlat
                throw new RuntimeException("Not enough stock for product: " + productDto.getName());
            }
            // Stoğu azalt
            productService.updateStockQuantity(productDto.getUuid(), productDto.getStockQuantity() - itemDto.getQuantity());
            // Sipariş öğesini oluştur ve siparişe ekle
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(productService.convertToEntity(productDto));
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(productDto.getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);

            // Ürünün UUID'sini listeye ekle
            productUuids.add(productDto.getUuid());
        }

        // Siparişi kaydet
        order = orderRepository.save(order);

        // Ödeme işlemini gerçekleştir
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setName(orderDto.getPaymentDto().getName());
        paymentDto.setCardNumber(orderDto.getPaymentDto().getCardNumber());
        paymentDto.setExpirationDate(orderDto.getPaymentDto().getExpirationDate());
        paymentDto.setCvc(orderDto.getPaymentDto().getCvc());
        paymentDto.setAmount(orderDto.getTotalAmount());
        paymentDto.setUserUuid(orderDto.getUserUuid());
        paymentDto.setProductUuids(productUuids.toString()); // Ürün UUID'lerini atama yap

        paymentService.doPaymentAndSaveCustomerDetails(paymentDto);

        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> getOrdersByUserUuid(String userUuid) {
        // Müşteriye ait tüm siparişleri getir
        return orderRepository.findByUserUuid(userUuid);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        // Sipariş durumunu güncelle
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        // Tüm siparişleri getir
        return orderRepository.findAll();
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        // Stokları geri al
        for (OrderItem item : order.getOrderItems()) {
            productService.updateStockQuantity(item.getProduct().getUuid(), item.getProduct().getStockQuantity() + item.getQuantity());
        }
        // Siparişi iptal et
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

}
