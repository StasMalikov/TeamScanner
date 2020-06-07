package teamScanner.dto;

import lombok.Data;

@Data
public class StringDTO {
    String info;

    public StringDTO() {
    }

    public StringDTO(String info) {
        this.info = info;
    }
}
