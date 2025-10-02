package com.jeferson.msvc.users.services;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserResponseDto;
import com.jeferson.msvc.users.entities.Role;
import com.jeferson.msvc.users.entities.Roles;
import com.jeferson.msvc.users.entities.User;
import com.jeferson.msvc.users.entities.UserStatus;
import com.jeferson.msvc.users.entities.UserStatusReason;
import com.jeferson.msvc.users.mapper.UserMapper;
import com.jeferson.msvc.users.respositories.RoleRepository;
import com.jeferson.msvc.users.respositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImp(UserRepository userRepository, 
        UserMapper userMapper, 
        PasswordEncoder passwordEncoder,
        RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
 
    @Override
    @Transactional
    public UserResponseDto save(UserRequestDto userDto){
        existUsername(userDto.getUsername());
        existEmail(userDto.getEmail());
        
        Set<Role> roles = getValidRoles(userDto.getRoles());
        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(roles);
        user.setRegistrationDate(LocalDateTime.now());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserResponseDto> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public List<UserResponseDto> findAllByStatus(UserStatus status){
        List<User> users = userRepository.findAllByStatus(status);
        return switch (status) {
            case ACTIVE -> users.stream()
                .map(userMapper::toDto)
                .toList();
            case DELETED,DISABLED,BANNED -> users.stream()
                .map(userMapper::toDisabledDto)
                .map(dto ->(UserResponseDto) dto)
                .toList();
        };  
    }

    @Override
    public UserResponseDto findById(Long id){
        User user = findUserById(id);
        return mapperByStatus(user);
    }

    @Override
    public UserResponseDto findByUsername(String userName){
        User user = userRepository.findByUsername(userName)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario "+ userName));
        return mapperByStatus(user);
    }

    @Override
    @Transactional
    public void updateEmail(Long id, String email){
        User user = findUserById(id);
        validStatus(user);
        if (email == user.getEmail()) {
            throw new IllegalArgumentException("El usuario ya tiene asociado el correo "+ email);
        }
        existEmail(email);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String oldPassword ,String newpassword){
        User user = findUserById(id);
        validStatus(user);
        validPassword(user, oldPassword, newpassword);
        user.setPassword(passwordEncoder.encode(newpassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateRoles(Long id, Set<Roles> newRoles) {
        User user = findUserById(id);
        validStatus(user);
        Set<Role> validRoles = getValidRoles(newRoles);
        Set<Role> currentRoles = new HashSet<>(user.getRoles());

        currentRoles.removeIf(role -> !validRoles.contains(role));
        currentRoles.addAll(validRoles);
        user.setRoles(currentRoles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, UserStatus status, String reason){
        User user = findUserById(id);
        validateRepeatInfo(user, status);

        user.setStatus(status);

        if (requiresReason(status)) {
            addStatusReason(user, reason);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id, String reason){
        User user = findUserById(id);
        if (user.getStatus() == UserStatus.DELETED) {
            throw new IllegalArgumentException("El usuario ya se encuentra eliminado definitivamente del sistema");
        }
        user.setStatus(UserStatus.DELETED);
        
        UserStatusReason statusReason = new UserStatusReason();
        statusReason.setReason(reason);
        statusReason.setDeactivationDate(LocalDateTime.now());
        statusReason.setUser(user);
        user.getUserStatusReason().add(statusReason);
        userRepository.save(user);
    }

    

    //helpers
    private User findUserById(Long id){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario asociado al id "+ id));
        return user;
    }

    private void existUsername(String username){
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El username ya existe registrado en el sistema");
        }
    }

    private void existEmail(String email){
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya existe registrado en el sistema");
        }
    }

    private void validPassword(User user, String oldPassword, String newPassword){
        if (newPassword == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("La contraseña incorrecta");
        }
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("contraseña debil, minimo 8 caracteres");
        }
    }

    private void validStatus (User user){
        UserStatus status = user.getStatus();

        if (status.equals(UserStatus.BANNED) || status.equals(UserStatus.DELETED) || status.equals(UserStatus.DISABLED)) {
            throw new IllegalArgumentException("No se puede actualizar la informacion de un usuario desahabilitado");
        }
    }

    private void validateRepeatInfo(User user, UserStatus status){
        if (user.getStatus() == UserStatus.DELETED) {
            throw new IllegalArgumentException("El usuario se encunetra eliminado, no se puede actualizar");
        }
        if (user.getStatus() == status) {
            throw new IllegalArgumentException("El usuario ya se encuentra asignado al estado de "+ status);
        }
    }

    private UserResponseDto mapperByStatus(User user){
        UserStatus status = user.getStatus();
        return switch (status){
            case ACTIVE -> userMapper.toDto(user);
            case DELETED, DISABLED, BANNED -> userMapper.toDisabledDto(user);
        };
    }

    private boolean requiresReason(UserStatus status) {
        return status == UserStatus.BANNED || status == UserStatus.DISABLED;
    }

    private void addStatusReason(User user, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Debe proporcionarse un motivo para este estado");
        }
        UserStatusReason statusReason = new UserStatusReason();
        statusReason.setReason(reason);
        statusReason.setDeactivationDate(LocalDateTime.now());
        statusReason.setUser(user);
        user.getUserStatusReason().add(statusReason);
    }


    private Set<Role> getValidRoles(Set<Roles> rolesFromDto) {
        Set<Role> roles = new HashSet<>();
        if (rolesFromDto == null || rolesFromDto.isEmpty()) {
            Role roleDefault = roleRepository.findByName(Roles.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("El rol ROLE_USER no existe en el sistema"));
            roles.add(roleDefault);
            return roles;
        }
        for (Roles roleEnum : rolesFromDto) {
            Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new EntityNotFoundException("El rol " + roleEnum + " no existe en el sistema"));
            roles.add(role);
        }
        return roles;
    }

}
