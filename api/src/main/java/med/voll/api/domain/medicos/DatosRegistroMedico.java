package med.voll.api.domain.medicos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;;

public record DatosRegistroMedico(

        //Validations o Validaciones, se hace antes de mandar los datos a la base de datos
        //La base de datos no debe hacer esa clase de operaciones (verificar, validar)
        //Para ello se usan Anotaciones NotBlank, Email, NotNull, Pattern
        @NotBlank(message = "{nombre.obligatorio}") //Indicacion de que no se acepta campo nulo ni vacío
        String nombre,                              //{nombre.obligatorio} definido en ValidationMessages.properties

        @NotBlank
        @Email //Indicamos que este atributo es un email
        String email,

        @NotBlank
        String telefono,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //Agregamos que el atributo documento sigue una expresión regular o patrón
        String documento,

        @NotNull //para objetos se usa NotNull para indicar que no puede ser nulo
        Especialidad especialidad,

        @NotNull
        @Valid //indicamos que se debe verificar direccion
        DatosDireccion direccion
) {
}
