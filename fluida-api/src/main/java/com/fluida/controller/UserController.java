package com.fluida.controller;

import com.fluida.dto.CustomerResponse;
import com.fluida.dto.LoginRequest;
import com.fluida.dto.LoginResponse;
import com.fluida.dto.SignupRequest;
import com.fluida.mapper.UserMapper;
import com.fluida.model.Customer;
import com.fluida.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserDetailsService userDetailsService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Valida credenciais usando o UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

            // Verifica senha
            if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
                log.info("Senha não corresponde");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // Busca customer completo
            Customer customer = customerRepository.findByEmail(request.email())
                    .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

            // Monta resposta
            String encodedCredentials = Base64.getEncoder()
                    .encodeToString((request.email() + ":" + request.password()).getBytes());

            LoginResponse response = new LoginResponse(
                    UserMapper.toResponse(customer),
                    "Basic " + encodedCredentials,
                    customer.getTrainings() != null ? customer.getTrainings().size() : 0
            );

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            log.info("Usuário não encontrado enquanto tentava logar");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(@RequestBody SignupRequest request) {
        // Verifica se email já existe
        if (customerRepository.findByEmail(request.email()).isPresent()) {
            log.info("Usuário já existe");
            return ResponseEntity.badRequest().body(null);
        }

        // Cria novo customer
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPassword(passwordEncoder.encode(request.password())); // Encoda a senha

        customer = customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(customer));
    }
}