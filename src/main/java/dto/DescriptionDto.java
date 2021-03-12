package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionDto {
    private String name;
    private Double amount;
    private Double average;
    private Double shouldPay;
    private Double shouldReceive;
}
