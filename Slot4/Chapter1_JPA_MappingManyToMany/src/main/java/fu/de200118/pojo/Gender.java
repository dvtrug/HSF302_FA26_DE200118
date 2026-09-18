package fu.de200118.pojo;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Gender {
    @Enumerated(EnumType.STRING)
    MALE, FEMALE, OTHER
}
