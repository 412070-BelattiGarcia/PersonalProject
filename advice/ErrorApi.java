package project.project.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorApi {
    private String timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private String path;
}
