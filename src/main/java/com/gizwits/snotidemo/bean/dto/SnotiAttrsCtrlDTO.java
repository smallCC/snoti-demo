package com.gizwits.snotidemo.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @author Jcxcc
 * @since 1.0
 */
@Data
@ApiModel
public class SnotiAttrsCtrlDTO {

    @ApiModelProperty("productKey, 产品key")
    @NotBlank(message = "productKey不能为空")
    private String productKey;

    @ApiModelProperty("设备mac地址")
    @NotBlank(message = "设备mac地址不能为空")
    private String mac;

    @ApiModelProperty("设备did")
    @NotBlank(message = "设备did不能为空")
    private String did;

    @NotEmpty(message = "数据点信息不能为空")
    @ApiModelProperty("需要下发的数据点")
    private Map<String, Object> attrs;
}
