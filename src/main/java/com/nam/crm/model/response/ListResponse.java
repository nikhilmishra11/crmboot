package com.nam.crm.model.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListResponse<T> extends PageResponse {
  @ApiModelProperty(required = true, value = "")
  private List<T> items;
}
