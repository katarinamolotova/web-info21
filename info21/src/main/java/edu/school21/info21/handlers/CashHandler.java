package edu.school21.info21.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CashHandler {
    private final HashMap<UUID, Boolean> cashStatus = new HashMap<>();

    public UUID registry() {
        UUID temp = UUID.randomUUID();
        cashStatus.put(temp, true);
        return temp;
    }

    public void globalChanges() {
        cashStatus.replaceAll((key, value) -> value = true);
    }

    public void localChanges(final UUID uuid, final Boolean status) {
        cashStatus.put(uuid, status);
    }

    public boolean changesById(final UUID uuid) {
        return cashStatus.get(uuid);
    }
}
