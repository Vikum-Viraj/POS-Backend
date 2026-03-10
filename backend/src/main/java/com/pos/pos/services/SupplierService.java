package com.pos.pos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.pos.entity.SupplierEntity;
import com.pos.pos.model.SupplierDTO;
import com.pos.pos.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final ObjectMapper objectMapper;

    public SupplierService(SupplierRepository supplierRepository, ObjectMapper objectMapper) {
        this.supplierRepository = supplierRepository;
        this.objectMapper = objectMapper;
    }

    // ✅ Create Supplier
    public SupplierDTO createSupplier(SupplierDTO dto) {
        SupplierEntity entity = mapDtoToEntity(dto, new SupplierEntity());
        SupplierEntity saved = supplierRepository.save(entity);
        return mapEntityToDto(saved);
    }

    // ✅ Update Supplier
    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {
        SupplierEntity existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + id));

        SupplierEntity updatedEntity = mapDtoToEntity(dto, existingSupplier);
        SupplierEntity updated = supplierRepository.save(updatedEntity);

        return mapEntityToDto(updated);
    }

    // ✅ Convert DTO → Entity
    private SupplierEntity mapDtoToEntity(SupplierDTO dto, SupplierEntity entity) {
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setItemName(dto.getItemName());
        entity.setItemCode(dto.getItemCode());
        entity.setQuantity(dto.getQuantity());
        entity.setCost(dto.getCost());
        entity.setTotalCost(dto.getTotalCost());
        return entity;
    }

    // ✅ Convert Entity → DTO
    private SupplierDTO mapEntityToDto(SupplierEntity entity) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setItemName(entity.getItemName());
        dto.setItemCode(entity.getItemCode());
        dto.setQuantity(entity.getQuantity());
        dto.setCost(entity.getCost());
        dto.setTotalCost(entity.getTotalCost());
        return dto;
    }

    // ✅ Get all Suppliers
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    // ✅ Get single supplier by ID
    public SupplierDTO getSupplierById(Long id) {
        SupplierEntity entity = supplierRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Supplier not found with ID: " + id));
        return mapEntityToDto(entity);
    }

    // ✅ Delete Supplier
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new NoSuchElementException("Supplier not found with ID: " + id);
        }
        supplierRepository.deleteById(id);
    }
}