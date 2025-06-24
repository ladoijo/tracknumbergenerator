package com.hadyan.tracknumbergenerator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hadyan.tracknumbergenerator.constant.PatternConst;

import java.time.OffsetDateTime;

public record TrackNumberRespDto(
        String trackNumber,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConst.STRING_DATE_PATTERN)
        OffsetDateTime createdAt
) {
}
