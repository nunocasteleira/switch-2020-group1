package switchtwentytwenty.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import switchtwentytwenty.project.controllers.icontrollers.IAuthenticateUserController;
import switchtwentytwenty.project.dto.authentication.JwtResponseDTO;
import switchtwentytwenty.project.dto.authentication.LoginRequestDTO;
import switchtwentytwenty.project.security.jwt.JwtUtils;
import switchtwentytwenty.project.security.service.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthenticateUserController {
    public static final String AUTH_FAILED = "Authentication failed.";
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> role = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getUsername(), userDetails.getPersonName(),
                    userDetails.getFamilyId(), role));
        } catch (AuthenticationException | NullPointerException e) {
            return new ResponseEntity<>(AUTH_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

}
