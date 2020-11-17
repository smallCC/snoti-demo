package com.gizwits.snotidemo.controller;

import com.gizwits.snotidemo.bean.dto.SnotiAttrsCtrlDTO;
import com.gizwits.snotidemo.common.web.ApiResponse;
import com.gizwits.snotidemo.service.SnotiBootstrapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Jcxcc
 * @since 1.0
 */
@RestController
@RequestMapping("/snoti/ctrl")
@Api(tags = "snoti设备控制接口")
public class SnotiCtrlController {

    @Autowired
    private SnotiBootstrapService snotiBootstrapService;

    @PostMapping
    @ApiOperation(value = "控制设备-数据点", notes = "使用数据点控制设备")
    public ApiResponse<Boolean> attrsCtrl(@Valid @RequestBody SnotiAttrsCtrlDTO params) {
        boolean res = snotiBootstrapService.ctrl(params.getProductKey(), params.getMac(), params.getDid(), params.getAttrs());
        return ApiResponse.ok(res);
    }
}
