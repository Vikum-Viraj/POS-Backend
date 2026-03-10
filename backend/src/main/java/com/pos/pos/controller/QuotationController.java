package com.pos.pos.controller;

import com.pos.pos.entity.QuotationEntity;
import com.pos.pos.exception.ProductNotFoundException;
import com.pos.pos.model.QuotationDTO;
import com.pos.pos.services.QuotationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("/quotation")
    public ResponseEntity<?> createQuotation(@RequestBody QuotationDTO dto) {
        try {
            QuotationEntity quotation = quotationService.saveQuotation(dto);
            return ResponseEntity.ok(quotation); // Return the saved quotation with 200 OK
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the quotation.");
        }
    }


    @GetMapping("/quotations")
    public List<QuotationDTO> getAllQuotations() {
        return quotationService.getAll();
    }

    @PutMapping("/quotation/{id}")
    public QuotationDTO updateQuotation(@PathVariable Long id, @RequestBody QuotationDTO dto) {
        return quotationService.update(id, dto);
    }

    @GetMapping("/quotation/{id}")
    public QuotationDTO getQuotationById(@PathVariable Long id) {
        return quotationService.getById(id);
    }

    @DeleteMapping("/quotations/{id}")
    public ResponseEntity<String> deleteQuotation(@PathVariable Long id) {
        quotationService.deleteQuotation(id);
        return ResponseEntity.ok("Quotation deleted successfully");
    }

}
