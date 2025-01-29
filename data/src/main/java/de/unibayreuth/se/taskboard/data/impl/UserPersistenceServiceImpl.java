package de.unibayreuth.se.taskboard.data.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.DuplicateNameException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserPersistenceService;
import de.unibayreuth.se.taskboard.data.mapper.UserEntityMapper;
import de.unibayreuth.se.taskboard.data.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class UserPersistenceServiceImpl implements UserPersistenceService {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void clear() {
        userRepository.findAll()
                .forEach(userEntity -> eventRepository.saveAndFlush(
                        EventEntity.deleteEventOf(userEntityMapper.fromEntity(userEntity), null))
                );
        if (userRepository.count() != 0) {
            throw new IllegalStateException("Tasks not successfully deleted.");
        }
    }

    @NonNull
    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream()
                .map(userEntityMapper::fromEntity)
                .toList();
    }

    @NonNull
    @Override
    public Optional<User> getById(UUID id) {
        return userRepository.findById(id)
                .map(userEntityMapper::fromEntity);
    }

    @NonNull
    @Override
    public User upsert(User user) throws UserNotFoundException, DuplicateNameException {
        if (user.getId() == null) {
            // create new user
            user.setId(UUID.randomUUID());
            eventRepository.saveAndFlush(EventEntity.insertEventOf(user, null, objectMapper));
            return userRepository.findById(user.getId())
                    .map(userEntityMapper::fromEntity)
                    .orElseThrow(() -> new IllegalStateException("Error creating new user with ID " + user.getId()));
        }

        // update existing user
        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + user.getId() + " does not exist."));
        userEntity.setName(user.getName());
        eventRepository.saveAndFlush(EventEntity.updateEventOf(user, null, objectMapper));
        return userRepository.findById(user.getId())
                .map(userEntityMapper::fromEntity)
                .orElseThrow(() -> new IllegalStateException("Error updating user with ID " + user.getId()));
    }
}
