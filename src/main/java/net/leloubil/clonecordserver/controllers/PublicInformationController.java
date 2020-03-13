package net.leloubil.clonecordserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.InstanceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirements()
@Tag(name = "Public Informations", description = "Query public informations about this instance")
public class PublicInformationController {

    private final InstanceInfo info;

    public PublicInformationController(InstanceInfo info) {
        this.info = info;
    }

    @GetMapping("/info")
    public InstanceInfo getInfo() {
        return info;
    }

}
