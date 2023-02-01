//package pl.kamil.praca.authentication.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import pl.kamil.praca.authentication.dto.AuthResponse;
//import pl.kamil.praca.authentication.dto.LoginRequest;
//import pl.kamil.praca.authentication.dto.RegisterRequest;
//import pl.kamil.praca.authentication.model.User;
//import pl.kamil.praca.authentication.repository.UserRepository;
//import pl.kamil.praca.authentication.security.JWT.JwtUtil;
//
//@Service
//@RequiredArgsConstructor
//
//public class AuthService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthResponse register(RegisterRequest request) {
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//
//        userRepository.save(user);
//        var jwtToken = jwtUtil.generateToken((UserDetails) user);
//        return AuthResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
////    public AuthResponse authenticate(LoginRequest loginRequest) {
////        authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(
////                        loginRequest.getUsername(),
////                        loginRequest.getPassword()
////                )
////        );
////        var user = repository.findByEmail(loginRequest.getUsername())
////                .orElseThrow();
////        var jwtToken = jwtUtil.buildJwt(user);
////        return AuthResponse.builder()
////                .token(jwtToken)
////                .build();
////    }
//}
//
