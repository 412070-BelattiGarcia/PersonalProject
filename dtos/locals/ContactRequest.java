package project.project.dtos.locals;

public record ContactRequest (
    @NotBlank @Size(max = 30) String type,
    @NotBlank @Size(max = 200) String value
) {}