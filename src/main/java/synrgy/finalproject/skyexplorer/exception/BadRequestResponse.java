package synrgy.finalproject.skyexplorer.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BadRequestResponse {
    @Builder.Default
    private String status = "fail";

    @JsonProperty
    @Builder.Default
    private int code = HttpStatus.BAD_REQUEST.value();

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,String> errors;
}