package net.leloubil.clonecordserver.data;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Scope("singleton")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@ConfigurationProperties("app")
public class InstanceInfo {

    @NotBlank
    private String instanceName;

    private boolean acceptsLogin = true;

    private boolean acceptsRegistrations = true;

    @NotBlank
    private String statusMessage;


    //format payload pour get info externe
    //
    // //User demandant
    // //Ressource demandée
    // //signé par B
    // signé par A


}
