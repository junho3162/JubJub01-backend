package org.example.jubjub.domain.test;

import org.example.jubjub.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test API", description = "서버 연결 테스트용 API")
@RestController
public class TestController {

    @Operation(summary = "연결 테스트", description = "공통 응답 객체가 잘 작동하는지 확인합니다.")
    @GetMapping("/api/test")
    public ApiResponse<String> test() {
        return ApiResponse.success("Hello JubJub! 패키지 경로 설정까지 완벽하네요.");
    }
}