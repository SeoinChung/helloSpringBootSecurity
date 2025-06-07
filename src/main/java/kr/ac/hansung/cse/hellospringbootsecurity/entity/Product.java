package kr.ac.hansung.cse.hellospringbootsecurity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "상품명을 입력하세요.")
    @Size(min = 2, max = 100, message = "상품명은 2자 이상 100자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "브랜드를 입력하세요.")
    private String brand;

    @NotBlank(message = "제조국을 입력하세요.")
    private String madeIn;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private double price;

    public Product(String name, String brand, String madeIn, double price) {
        this.name = name;
        this.brand = brand;
        this.madeIn = madeIn;
        this.price = price;
    }
}