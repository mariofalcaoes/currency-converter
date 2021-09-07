package com.jaya.currency.converter.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Used to receive user requests.
 */
@Data
@Builder
@ToString(exclude = "userId")
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConversionRequest {
  @NotNull(message = "Destine coin should not be null or empty")
  @NotEmpty
  @Size(min = 3, max = 3)
  private String     destineCoin;
  @NotNull(message = "Origin coin should not be null or empty")
  @NotEmpty
  @Size(min = 3, max = 3)
  private String     originCoin;
  @NotNull(message = "Origin value should not be null or <= 0")
  @Positive
  private BigDecimal originValue;
  @ApiModelProperty(hidden = true)
  private Long       userId;
}
