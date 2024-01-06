package synrgy.finalproject.skyexplorer.model.dto.response;

import java.util.HashMap;

public record FailResponse(String status, HashMap<String, String> data){
}
