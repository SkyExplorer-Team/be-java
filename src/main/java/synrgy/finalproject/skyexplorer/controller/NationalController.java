package synrgy.finalproject.skyexplorer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy.finalproject.skyexplorer.model.dto.request.NationalDto;
import synrgy.finalproject.skyexplorer.model.entity.National;
import synrgy.finalproject.skyexplorer.service.NationalService;
import synrgy.finalproject.skyexplorer.utils.ResponseGeneral;

import java.util.List;

import static synrgy.finalproject.skyexplorer.utils.ResponseGeneral.generatedResponse;

@RestController
@RequestMapping("/national")
@RequiredArgsConstructor
@Slf4j
public class NationalController {

    private final NationalService nationalService;

    @GetMapping
    public ResponseEntity<?> getNationals(){
        var  nationals = nationalService.getAll();
        return generatedResponse("nationals", nationals);
    }

    @PostMapping
    public ResponseEntity<?> createNational(@RequestBody NationalDto nationalDto){
        var nationalSaved = nationalService.create(nationalDto);
        return generatedResponse("national", nationalSaved);
    }
}
