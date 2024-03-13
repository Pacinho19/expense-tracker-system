package pl.pacinho.expensetrackersystem.authorization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pacinho.expensetrackersystem.authorization.model.AuthenticationRequestDto;
import pl.pacinho.expensetrackersystem.authorization.service.AuthorizationService;

@RequiredArgsConstructor
@RequestMapping("/authorization")
@RestController
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<String> authorization(@RequestBody AuthenticationRequestDto authenticationRequest) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");
        }
        return ResponseEntity.ok(authorizationService.generateToken(authenticationRequest.username()));
    }

}
