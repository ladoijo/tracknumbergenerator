package com.hadyan.tracknumbergenerator.controller;

import com.hadyan.tracknumbergenerator.dto.ApiRespDto;
import com.hadyan.tracknumbergenerator.dto.TrackNumberReqDto;
import com.hadyan.tracknumbergenerator.service.TrackNumberService;
import com.hadyan.tracknumbergenerator.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrackNumberController {
    private final TrackNumberService trackNumberService;

    @GetMapping("/v1/next-tracking-number")
    public ResponseEntity<ApiRespDto<?>> genNextTrackingNumber(@Valid TrackNumberReqDto reqDto) {
        var data = trackNumberService.genNextTrackingNumber(reqDto);
        return ResponseUtil.okWithData(data);
    }
}
