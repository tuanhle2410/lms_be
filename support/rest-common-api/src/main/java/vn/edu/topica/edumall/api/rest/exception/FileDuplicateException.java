package vn.edu.topica.edumall.api.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDuplicateException extends RuntimeException {
    String fileName;
}
