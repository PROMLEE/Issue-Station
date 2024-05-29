package com.issuestation.Controller;

import com.issuestation.Dto.Temp.TempResponse;
import com.issuestation.Service.TempService.TempQueryServiceImpl;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.converter.TempConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempRestController {
    private final TempQueryServiceImpl tempQueryService;

    @GetMapping("/test")
    public ApiResponse<TempResponse.TempTestDTO> testAPI(){

        return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
    }
    @GetMapping("/exception")
    public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer name){
        tempQueryService.CheckFlag(name);
        return ApiResponse.onSuccess(TempConverter.toTempExceptionDTO(name));
    }
}