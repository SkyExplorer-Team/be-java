package synrgy.finalproject.skyexplorer.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class SuccessResponse {
    public static ResponseEntity<Object> generateResponse(String status,
                                                          String result,
                                                          Object data,
                                                          HttpStatus httpStatus){

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("result", result);
        responseMap.put("data", data);
        return new ResponseEntity<>(responseMap, httpStatus);

    }
}
