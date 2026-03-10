package com.pos.pos.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.pos.entity.QuotationEntity;
import com.pos.pos.model.ItemDTO;
import com.pos.pos.model.QuotationDTO;
import com.pos.pos.repository.QuotationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final ObjectMapper objectMapper;

    public QuotationService(QuotationRepository quotationRepository, ObjectMapper objectMapper) {
        this.quotationRepository = quotationRepository;
        this.objectMapper = objectMapper;
    }

    public QuotationEntity saveQuotation(QuotationDTO dto) {
        QuotationEntity quotation = new QuotationEntity();
        quotation.setCreatedDate(LocalDateTime.now());
        quotation.setUpdatedDate(LocalDateTime.now());
        quotation.setUnit(dto.getUnit());
        quotation.setShowDiscountInRate(dto.getShowDiscountInRate());
        quotation.setCustomerName(dto.getCustomerName());
        quotation.setCustomerEmail(dto.getCustomerEmail());
        quotation.setCustomerPhone(dto.getCustomerPhone());
        quotation.setReceiverCompany(dto.getReceiverCompany());
        quotation.setReceiverAddress(dto.getReceiverAddress());

        try {
            String itemsJson = objectMapper.writeValueAsString(dto.getItems());
            quotation.setItems(itemsJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting items to JSON", e);
        }

        quotation.setSubtotal(dto.getSubtotal());
        quotation.setTotalDiscount(dto.getTotalDiscount());
        quotation.setTotal(dto.getTotal());
        quotation.setOrderRef(dto.getOrderRef());

        return quotationRepository.save(quotation);
    }

    public List<QuotationDTO> getAll() {
        return quotationRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public QuotationDTO update(Long id, QuotationDTO dto) {
        Optional<QuotationEntity> optionalQuotation = quotationRepository.findById(id);
        if (!optionalQuotation.isPresent()) {
            throw new RuntimeException("Quotation not found with ID: " + id);
        }

        QuotationEntity quotation = mapDtoToEntity(dto, optionalQuotation.get());
        quotation.setId(id);
        quotation.setUpdatedDate(LocalDateTime.now());
        QuotationEntity updated = quotationRepository.save(quotation);
        return mapEntityToDto(updated);
    }
    private QuotationEntity mapDtoToEntity(QuotationDTO dto, QuotationEntity entity) {
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setCustomerPhone(dto.getCustomerPhone());
        entity.setReceiverCompany(dto.getReceiverCompany());
        entity.setUnit(dto.getUnit());
        entity.setShowDiscountInRate(dto.getShowDiscountInRate());
        entity.setReceiverAddress(dto.getReceiverAddress());

        try {
            String itemsJson = objectMapper.writeValueAsString(dto.getItems());
            entity.setItems(itemsJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting items to JSON", e);
        }

        entity.setSubtotal(dto.getSubtotal());
        entity.setTotalDiscount(dto.getTotalDiscount());
        entity.setTotal(dto.getTotal());
        entity.setOrderRef(dto.getOrderRef());

        return entity;
    }

    private QuotationDTO mapEntityToDto(QuotationEntity entity) {
        QuotationDTO dto = new QuotationDTO();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setUnit(entity.getUnit());
        dto.setShowDiscountInRate(entity.getShowDiscountInRate());
        dto.setCustomerEmail(entity.getCustomerEmail());
        dto.setCustomerPhone(entity.getCustomerPhone());
        dto.setReceiverCompany(entity.getReceiverCompany());
        dto.setReceiverAddress(entity.getReceiverAddress());

        try {
            List<ItemDTO> items = objectMapper.readValue(
                    entity.getItems(), new TypeReference<List<ItemDTO>>() {});
            dto.setItems(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading items JSON", e);
        }

        dto.setSubtotal(entity.getSubtotal());
        dto.setTotalDiscount(entity.getTotalDiscount());
        dto.setTotal(entity.getTotal());
        dto.setOrderRef(entity.getOrderRef());
        return dto;
    }

    public QuotationDTO getById(Long id) {
        return quotationRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new RuntimeException("Quotation not found with ID: " + id));
    }

    public void deleteQuotation(Long id) {
        if (!quotationRepository.existsById(id)) {
            throw new RuntimeException("Quotation not found with ID: " + id);
        }
        quotationRepository.deleteById(id);
    }

}