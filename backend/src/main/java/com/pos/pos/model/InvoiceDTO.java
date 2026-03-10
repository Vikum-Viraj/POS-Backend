package com.pos.pos.model;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDTO {
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
    private String payment;
    private String unit;
    private LocalDateTime createdDate;
    private Boolean showDiscountInRate;
    private String noStock;
    public  String getNoStock(){return noStock;}
    public void setNoStock(String noStock){this.noStock = noStock;}

    public String getUnit(){return unit;}
    public void setUnit(String unit){this.unit=unit;}
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Boolean getShowDiscountInRate() {return showDiscountInRate;}
    public void setShowDiscountInRate(Boolean showDiscountInRate) {this.showDiscountInRate = showDiscountInRate;}

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(double totalDiscount) { this.totalDiscount = totalDiscount; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getOrderRef() { return orderRef; }
    public void setOrderRef(String orderRef) { this.orderRef = orderRef; }

    public String getPayment() { return payment; }
    public void setPayment(String payment) { this.payment = payment; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
