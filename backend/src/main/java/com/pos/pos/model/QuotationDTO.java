package com.pos.pos.model;

import java.time.LocalDateTime;
import java.util.List;

public class QuotationDTO {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String receiverCompany;
    private String receiverAddress;
    private List<ItemDTO> items;
    private double subtotal;
    private double totalDiscount;
    private double total;
    private String orderRef;
    private String unit;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private  Boolean showDiscountInRate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUnit() {return  unit;}
    public void setUnit(String unit) {this.unit = unit;}

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getReceiverCompany() { return receiverCompany; }
    public void setReceiverCompany(String receiverCompany) { this.receiverCompany = receiverCompany; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public List<ItemDTO> getItems() { return items; }
    public void setItems(List<ItemDTO> items) { this.items = items; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(double totalDiscount) { this.totalDiscount = totalDiscount; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getOrderRef() { return orderRef; }
    public void setOrderRef(String orderRef) { this.orderRef = orderRef; }

    public Boolean getShowDiscountInRate() {return  showDiscountInRate;}
    public void setShowDiscountInRate(Boolean showDiscountInRate) {this.showDiscountInRate = showDiscountInRate;}

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}