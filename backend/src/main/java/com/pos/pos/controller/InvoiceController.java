package com.pos.pos.controller;

import com.pos.pos.entity.InvoiceEntity;
import com.pos.pos.model.InvoiceDTO;
import com.pos.pos.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/invoice")
    public InvoiceEntity createInvoice(@RequestBody InvoiceDTO dto) {
        return invoiceService.createInvoice(dto);
    }
    @PostMapping("/invoice/noStock")
    public InvoiceEntity createInvoiceAndFillStock(@RequestBody InvoiceDTO dto) {
        return invoiceService.createInvoiceAndFillStock(dto);
    }
    // 🔄 Update Invoice
    @PutMapping("/invoices/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO dto) {
        InvoiceDTO updatedInvoice = invoiceService.updateInvoice(id, dto);
        return ResponseEntity.ok(updatedInvoice);
    }
    @GetMapping("/invoices")
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted successfully");
    }
}
