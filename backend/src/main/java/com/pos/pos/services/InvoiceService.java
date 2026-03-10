package com.pos.pos.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.pos.entity.InvoiceEntity;
import com.pos.pos.entity.ProductEntity;
import com.pos.pos.exception.MultipleStockException;
import com.pos.pos.exception.ProductNotFoundException;
import com.pos.pos.exception.StockError;
import com.pos.pos.model.InvoiceDTO;
import com.pos.pos.model.ItemDTO;
import com.pos.pos.repository.InvoiceRepository;
import com.pos.pos.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          ProductRepository productRepository,
                          ObjectMapper objectMapper) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public InvoiceEntity createInvoice(InvoiceDTO dto) {
        // Validate and reduce stock
        List<StockError> insufficientStockList = new ArrayList<>();

        // Check stock for each item
        for (ItemDTO item : dto.getItems()) {
            Long productId = Long.parseLong(item.getProductId());
            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductName()));

            if (product.getQuantity() < item.getQuantity()) {
                insufficientStockList.add(new StockError(
                        product.getName(),
                        item.getQuantity(),
                        product.getQuantity()
                ));
            }
        }

        // If any product has insufficient stock → send list to frontend
        if (!insufficientStockList.isEmpty()) {
            throw new MultipleStockException(insufficientStockList);
        }

        // Deduct stock only if everything is available
        for (ItemDTO item : dto.getItems()) {
            Long productId = Long.parseLong(item.getProductId());
            ProductEntity product = productRepository.findById(productId).get();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        // Save invoice
        InvoiceEntity entity = new InvoiceEntity();
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setCustomerPhone(dto.getCustomerPhone());
        entity.setShowDiscountInRate(dto.getShowDiscountInRate());
        entity.setUnit(dto.getUnit());
        entity.setReceiverCompany(dto.getReceiverCompany());
        entity.setReceiverAddress(dto.getReceiverAddress());
        entity.setSubtotal(dto.getSubtotal());
        entity.setTotalDiscount(dto.getTotalDiscount());
        entity.setTotal(dto.getTotal());
        entity.setOrderRef(dto.getOrderRef());
        entity.setPayment(dto.getPayment());
        entity.setCreatedDate(LocalDateTime.now());

        try {
            entity.setItems(objectMapper.writeValueAsString(dto.getItems()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting items to JSON", e);
        }

        return invoiceRepository.save(entity);
    }

    public InvoiceDTO updateInvoice(Long id, InvoiceDTO dto) {
        InvoiceEntity existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));

        // Map updated values
        existingInvoice = mapDtoToEntity(dto, existingInvoice);

        InvoiceEntity updated = invoiceRepository.save(existingInvoice);
        return mapEntityToDto(updated);
    }

    private InvoiceEntity mapDtoToEntity(InvoiceDTO dto, InvoiceEntity entity) {
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setCustomerPhone(dto.getCustomerPhone());
        entity.setReceiverCompany(dto.getReceiverCompany());
        entity.setReceiverAddress(dto.getReceiverAddress());
        entity.setShowDiscountInRate(dto.getShowDiscountInRate());
        entity.setUnit(dto.getUnit());
        entity.setSubtotal(dto.getSubtotal());
        entity.setTotalDiscount(dto.getTotalDiscount());
        entity.setTotal(dto.getTotal());
        entity.setOrderRef(dto.getOrderRef());
        entity.setPayment(dto.getPayment());

        try {
            String itemsJson = objectMapper.writeValueAsString(dto.getItems());
            entity.setItems(itemsJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting items to JSON", e);
        }

        return entity;
    }

    private InvoiceDTO mapEntityToDto(InvoiceEntity entity) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerEmail(entity.getCustomerEmail());
        dto.setCustomerPhone(entity.getCustomerPhone());
        dto.setReceiverCompany(entity.getReceiverCompany());
        dto.setReceiverAddress(entity.getReceiverAddress());
        dto.setShowDiscountInRate(entity.getShowDiscountInRate());
        dto.setUnit(entity.getUnit());
        dto.setSubtotal(entity.getSubtotal());
        dto.setTotalDiscount(entity.getTotalDiscount());
        dto.setTotal(entity.getTotal());
        dto.setOrderRef(entity.getOrderRef());
        dto.setPayment(entity.getPayment());
        dto.setCreatedDate(entity.getCreatedDate());

        try {
            List<ItemDTO> items = objectMapper.readValue(
                    entity.getItems(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class)
            );

            for (ItemDTO item : items) {
                try {
                    Long productId = Long.parseLong(item.getProductId());
                    Optional<ProductEntity> optionalProduct = productRepository.findById(productId);

                    if (optionalProduct.isPresent()) {
                        ProductEntity product = optionalProduct.get();
                        int currentStock = product.getQuantity();

                        if (item.getQuantity() > item.getPreviousQuantity()) {
                            int difference = item.getQuantity() - item.getPreviousQuantity();
                            if(product.getQuantity() != 0){
                                product.setQuantity(currentStock - difference);
                            }
                        } else if (item.getQuantity() < item.getPreviousQuantity()) {
                            int difference = item.getPreviousQuantity() - item.getQuantity();
                            product.setQuantity(currentStock + difference);
                        }

                        productRepository.save(product);
                    } else {
                        System.err.println("Product not found for ID: " + item.getProductId());
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Invalid product ID in invoice item: " + item.getProductId());
                }
            }

            dto.setItems(items);

        } catch (Exception e) {
            throw new RuntimeException("Error reading items JSON", e);
        }

        return dto;
    }


    public InvoiceEntity createInvoiceAndFillStock(InvoiceDTO invoiceDTO){
        for (ItemDTO item : invoiceDTO.getItems()) {
            Long productId = Long.parseLong(item.getProductId());
            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductName()));

            if (product.getQuantity() < item.getQuantity()) {
                int additionalQnty = item.getQuantity() - product.getQuantity();
                product.setQuantity(product.getQuantity() + additionalQnty);
                productRepository.save(product);
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        // Convert DTO to entity
        InvoiceEntity entity = new InvoiceEntity();
        entity.setCustomerName(invoiceDTO.getCustomerName());
        entity.setCustomerEmail(invoiceDTO.getCustomerEmail());
        entity.setCustomerPhone(invoiceDTO.getCustomerPhone());
        entity.setReceiverCompany(invoiceDTO.getReceiverCompany());
        entity.setShowDiscountInRate(invoiceDTO.getShowDiscountInRate());
        entity.setReceiverAddress(invoiceDTO.getReceiverAddress());
        entity.setSubtotal(invoiceDTO.getSubtotal());
        entity.setUnit(invoiceDTO.getUnit());
        entity.setTotalDiscount(invoiceDTO.getTotalDiscount());
        entity.setTotal(invoiceDTO.getTotal());
        entity.setOrderRef(invoiceDTO.getOrderRef());
        entity.setPayment(invoiceDTO.getPayment());
        entity.setCreatedDate(LocalDateTime.now());

        try {
            entity.setItems(objectMapper.writeValueAsString(invoiceDTO.getItems()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting items to JSON", e);
        }

        return invoiceRepository.save(entity);
    }

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(entity -> {
                    InvoiceDTO dto = new InvoiceDTO();
                    dto.setId(entity.getId());
                    dto.setCustomerName(entity.getCustomerName());
                    dto.setCustomerEmail(entity.getCustomerEmail());
                    dto.setCustomerPhone(entity.getCustomerPhone());
                    dto.setReceiverCompany(entity.getReceiverCompany());
                    dto.setShowDiscountInRate(entity.getShowDiscountInRate());
                    dto.setUnit(entity.getUnit());
                    dto.setReceiverAddress(entity.getReceiverAddress());
                    dto.setSubtotal(entity.getSubtotal());
                    dto.setTotalDiscount(entity.getTotalDiscount());
                    dto.setTotal(entity.getTotal());
                    dto.setOrderRef(entity.getOrderRef());
                    dto.setPayment(entity.getPayment());
                    dto.setCreatedDate(entity.getCreatedDate());
                    try {
                        List<ItemDTO> items = objectMapper.readValue(entity.getItems(),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class));
                        dto.setItems(items);
                    } catch (Exception e) {
                        throw new RuntimeException("Error reading items JSON", e);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new NoSuchElementException("Invoice with ID " + id + " not found");
        }
        invoiceRepository.deleteById(id);
    }
}
