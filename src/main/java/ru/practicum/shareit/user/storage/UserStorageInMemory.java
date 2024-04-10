package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserStorageInMemory implements  UserStorage {
    private final Map<Long, User> storage = new HashMap<>();
    private final List<String> emails = new ArrayList<>();
    private long id;

    @Override
    public User get(Long id) {
        return storage.get(id);
    }

    @Override
    public User add(User user) {
        id++;
        user.setId(id);
        storage.put(user.getId(), user);
        return get(user.getId());
    }

    @Override
    public User update(User user) {
        storage.replace(user.getId(), user);
        return get(user.getId());
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }


}
