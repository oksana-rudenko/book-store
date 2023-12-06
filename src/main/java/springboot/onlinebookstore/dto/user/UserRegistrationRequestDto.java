package springboot.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import springboot.onlinebookstore.validation.FieldMatch;

@Data
@FieldMatch(
        password = "password",
        repeatPassword = "repeatPassword",
        message = "Your password shouldn't be empty and should match repeatPassword"
)
public class UserRegistrationRequestDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 8, max = 100)
    private String password;
    @NotNull
    @Length(min = 8, max = 100)
    private String repeatPassword;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
