package com.hadyan.tracknumbergenerator.service;

import com.google.common.hash.Hashing;
import com.hadyan.tracknumbergenerator.cache.ITrackNumberCache;
import com.hadyan.tracknumbergenerator.dto.TrackNumberReqDto;
import com.hadyan.tracknumbergenerator.dto.TrackNumberRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TrackNumberService {
    private final ITrackNumberCache trackNumberCache;

    public TrackNumberRespDto genNextTrackingNumber(TrackNumberReqDto reqDto) {
        var value = reqDto.originCountryId()
                + reqDto.destinationCountryId()
                + reqDto.weight()
                + reqDto.createdAt()
                + reqDto.customerId()
                + reqDto.customerName()
                + reqDto.customerSlug();
        
        var hash = Hashing.sha256().hashString(value, StandardCharsets.UTF_8).toString();
        var trackNumber = trackNumberCache.get(hash);
        if (trackNumber != null) return new TrackNumberRespDto(trackNumber, OffsetDateTime.now());

        var base16 = new BigInteger(hash, 16);
        var base36 = base16.toString(36).toUpperCase();
        trackNumber = base36.substring(0, 16);

        while (trackNumberCache.exists(trackNumber)) {
            var beginIndex = new Random().nextInt(10);
            var endIndex = beginIndex + 16;
            trackNumber = base36.substring(beginIndex, endIndex);
        }

        trackNumberCache.put(hash, trackNumber);
        trackNumberCache.put(trackNumber, hash);
        return new TrackNumberRespDto(trackNumber, OffsetDateTime.now());
    }
}
