package pps.gestorClub_api.dtos.reports.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeCollectionReportDto {
    private Long feeId;
    private String feeLabel;           // Ej: "Junio 2024"
    private Long issuedCount;          // Total pagos emitidos
    private Long paidCount;            // Pagos realizados
    private BigDecimal totalCollected; // Suma de pagos
    private BigDecimal expectedTotal;  // feeAmount * issuedCount
    private BigDecimal collectionRate; // % = totalCollected / expectedTotal * 100
}
