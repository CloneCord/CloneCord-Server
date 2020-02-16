package net.leloubil.clonecordserver.swagger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.bean.validators.plugins.Validators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.lang.annotation.Annotation;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;
import static springfox.bean.validators.plugins.Validators.annotationFromField;

@Component
@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class ValidatorBlankFixer implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        // we simply support all documentationTypes!
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<NotEmpty> notBlank = extractAnnotation(context);
        Optional<NotBlank> nb = extractAnnotationB(context);
        if (notBlank.isPresent() ||nb.isPresent()) {
            context.getBuilder().required(true);
        }
    }

    @VisibleForTesting
    Optional<NotEmpty> extractAnnotation(ModelPropertyContext context) {
        return annotationFromBean(context, NotEmpty.class).or(annotationFromField(context, NotEmpty.class));
    }

    @VisibleForTesting
    Optional<NotBlank> extractAnnotationB(ModelPropertyContext context) {
        return annotationFromBean(context, NotBlank.class).or(annotationFromField(context, NotBlank.class));
    }
}
