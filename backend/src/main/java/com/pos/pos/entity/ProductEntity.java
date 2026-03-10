package com.pos.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String code;
        private String name;
        private String unit;
        private double cost;
        private double mrp;
        private int quantity;
        private LocalDateTime createdDate;
        private  LocalDateTime updatedDate;
        // Getters and Setters
        public long getId() { return id; }
        public void setId(long id) { this.id = id; }

        public String getUnit() { return unit;}
        public void setUnit(String unit) {this.unit = unit;}

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Double getMrp() { return mrp; }
        public void setMrp(Double mrp) { this.mrp = mrp; }

        public Double getCost() {return  cost;}
        public void setCost(Double cost){this.cost = cost;}

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public LocalDateTime getCreatedDate() { return createdDate; }
        public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

        public LocalDateTime getUpdatedDate() { return updatedDate; }
        public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
