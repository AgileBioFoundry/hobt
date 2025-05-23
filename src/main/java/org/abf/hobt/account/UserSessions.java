package org.abf.hobt.account;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates and maintains the web application sessions for users who have successfully authenticated
 *
 * @author Hector Plahar
 */
public class UserSessions {

    private final static ConcurrentHashMap<String, Set<String>> userSessionMap = new ConcurrentHashMap<>();

    public static String getUserIdBySession(String sessionId) {
        for (Map.Entry<String, Set<String>> entrySet : userSessionMap.entrySet()) {
            if (entrySet.getValue().contains(sessionId)) {
                return entrySet.getKey();
            }
        }
        return null;
    }

    /**
     * Creates a new session id for the specified user
     * stores and returns it
     *
     * @param userId unique user identifier
     * @return newly created session id
     */
    public static String createNewSessionForUser(String userId) {
        String newSession = UUID.randomUUID().toString();
        putSession(userId, newSession);
        return newSession;
    }

    protected static void putSession(String userId, String sessionId) {
        Set<String> sessionIds = userSessionMap.computeIfAbsent(userId, k -> new HashSet<>());
        sessionIds.add(sessionId);
    }

    /**
     * Uses the session id passed in the parameter and sets it as the session id for the
     * user as long as it meets a specified criteria (currently must be at least 5 xters long)
     *
     * @param userId    unique user identifier
     * @param sessionId optional session id to set for that user
     * @return session id set for specified user. It will be the same as that passed in the parameter if
     * it meets the criteria
     */
    public static String createSessionForUser(String userId, String sessionId) {
        if (sessionId == null || sessionId.length() < 5)
            return createNewSessionForUser(userId);

        putSession(userId, sessionId);
        return sessionId;
    }

    /**
     * Invalidates the session id for the specified user
     * by removing the stored session id
     *
     * @param userId unique user id
     */
    public static void invalidateSession(String userId) {
        if (userId == null)
            return;
        userSessionMap.remove(userId);
    }
}
