package org.example.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            return true;
        }
        return false;
    }

    public synchronized boolean update(Account account) {
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            return true;
        }
        return false;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return accounts.containsKey(id) ? Optional.of(accounts.get(id)) : Optional.empty();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!accounts.containsKey(fromId) || !accounts.containsKey(toId)) {
            return false;
        }
        int fromAmount = accounts.get(fromId).amount();
        int toAmount = accounts.get(toId).amount();
        if (fromAmount < amount) {
            return false;
        }
        update(new Account(toId, toAmount + amount));
        update(new Account(fromId, fromAmount - amount));
        return true;
    }
}
