package synrgy.finalproject.skyexplorer.utils;

import org.springframework.http.ResponseEntity;
import synrgy.finalproject.skyexplorer.model.dto.response.ResponseDto;

import java.util.HashMap;

public class ResponseGeneral {
    public static ResponseEntity<?> generatedResponse(String name, Object value){
        HashMap<String, Object> maps = new HashMap<>() ;
        maps.put(name, value);
        return ResponseEntity.ok(new ResponseDto("success", maps));
    }

}
