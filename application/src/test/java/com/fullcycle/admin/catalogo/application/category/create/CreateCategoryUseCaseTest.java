package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;
    @Mock
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expName = "Filmes";
        final var expDescription = "A categoria";
        final var expIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expName, expDescription, expIsActive);

        when(gateway.create(any())).thenAnswer(returnsFirstArg());

        final var actOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actOutput);
        Assertions.assertNotNull(actOutput.id());

        verify(gateway, Mockito.times(1)).create(argThat(aCategory -> {
            return Objects.equals(expName, aCategory.getName())
                    && Objects.equals(expDescription, aCategory.getDescription())
                    && Objects.equals(expIsActive, aCategory.isActive())
                    && Objects.nonNull(aCategory.getId())
                    && Objects.nonNull(aCategory.getCreatedAt())
                    && Objects.nonNull(aCategory.getUpdatedAt())
                    && Objects.isNull(aCategory.getDeletedAt());
        }));
    }

    @Test
    public void givenAnInvalidCommand_whenCallsCreateCategory_shouldReturnDomainException() {
        final String expName = null;
        final var expDescription = "A categoria";
        final var expIsActive = true;
        final var expErrorMessage = "'name' should not be null";
        final var expErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expName, expDescription, expIsActive);

        final var actException = Assertions
                .assertThrows(DomainException.class, () -> useCase.execute(aCommand));
        Assertions.assertEquals(expErrorMessage, actException.getErrors().get(0).message());
        Assertions.assertEquals(expErrorCount, actException.getErrors().size());
        verify(gateway, never()).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId(){
        final var expName = "Filmes";
        final var expDescription = "A categoria";
        final var expIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expName, expDescription, expIsActive);

        when(gateway.create(any())).thenAnswer(returnsFirstArg());

        final var actOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actOutput);
        Assertions.assertNotNull(actOutput.id());

        verify(gateway, Mockito.times(1)).create(argThat(aCategory -> {
            return Objects.equals(expName, aCategory.getName())
                    && Objects.equals(expDescription, aCategory.getDescription())
                    && Objects.equals(expIsActive, aCategory.isActive())
                    && Objects.nonNull(aCategory.getId())
                    && Objects.nonNull(aCategory.getCreatedAt())
                    && Objects.nonNull(aCategory.getUpdatedAt())
                    && Objects.nonNull(aCategory.getDeletedAt());
        }));
    }


    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnException(){
        final var expName = "Filmes";
        final var expDescription = "A categoria";
        final var expIsActive = true;
        final var expErrorMessage = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expName, expDescription, expIsActive);

        when(gateway.create(any())).thenThrow(new IllegalStateException(expErrorMessage));

        final var actException = Assertions
                .assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expErrorMessage, actException.getMessage());
    }

}
