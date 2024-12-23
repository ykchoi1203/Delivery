package com.bestcat.delivery.area.web;
import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.service.AreaService;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AreaController {

    private final AreaService areaService;

    @Secured({"ROLE_MASTER", "ROLE_MANAGER", "ROLE_OWNER", "ROLE_CUSTOMER"})
    @GetMapping("/areas")
    public ResponseEntity<Page<AreaResponseDto>> searchAreas(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) UUID areaId,
            @RequestParam(required = false) String areaName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<AreaResponseDto> areas = areaService.searchAreas(city, areaId, areaName, page, size);
        return ResponseEntity.ok(areas);
    }

    @Secured({"ROLE_MASTER","ROLE_MANAGER"})
    @PostMapping("/areas")
    public ResponseEntity<String> createArea(@Valid @RequestBody AreaRequestDto areaRequestDto) {
        areaService.save(areaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Area가 추가되었습니다. "+areaRequestDto);
    }

    @Secured({"ROLE_MASTER","ROLE_MANAGER"})
    @PutMapping("/areas/{areaId}")
    public ResponseEntity<String> updateArea(@PathVariable UUID areaId, @Valid @RequestBody AreaRequestDto areaRequestDto) {
        areaService.updateArea(areaId, areaRequestDto);
        return ResponseEntity.ok("Area가 업데이트되었습니다.");
    }

    @Secured({"ROLE_MASTER","ROLE_MANAGER"})
    @DeleteMapping("/areas/{areaId}")
    public ResponseEntity<String> deleteArea(@PathVariable UUID areaId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        areaService.deleteArea(areaId,userDetails.getUserId());
        return ResponseEntity.ok("Area가 삭제되었습니다.");
    }

}
