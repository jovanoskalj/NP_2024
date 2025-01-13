package lab6_1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

public class ChatSystemTest {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt(); System.out.println();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ){

                    cr.addUser(jin.next());
                }
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,(Object[])params);
                    }
                }
            }
        }
    }

}

class ChatRoom {
    String roomName;
    Set<String> usernames;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        usernames = new TreeSet<>();
    }

    public void addUser(String username) {
        usernames.add(username);
    }

    public void removeUser(String username) {
        if (usernames.contains(username))
            usernames.remove(username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(roomName).append("\n");
        if (!usernames.isEmpty() && usernames.size() != 0) {
            usernames.forEach(
                    u -> sb.append(u).append("\n")
            );
        } else {
            sb.append("EMPTY\n");
        }
        return sb.toString();

    }

    public boolean hasUser(String username) {
        return usernames.contains(username);
    }

    public int numUsers() {
        return usernames.size();
    }

    public String getName() {
        return roomName;
    }
}

class ChatSystem {

    Map<String, ChatRoom> map;
    Set<String> registeredUser;

    public ChatSystem() {
        map = new TreeMap<>();
        registeredUser = new TreeSet<>();
    }

    public void addRoom(String roomName) {
        map.putIfAbsent(roomName, new ChatRoom(roomName));

    }

    public void removeRoom(String roomName) {
        map.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if (!map.containsKey(roomName)) {
            throw new NoSuchRoomException(roomName);
        }
        return map.get(roomName);
    }

    public void register(String username) {
        if (!registeredUser.contains(username)) {
            registeredUser.add(username);
            ChatRoom room = map.values().stream()
                    .min(Comparator.comparing(ChatRoom::numUsers).thenComparing(ChatRoom::getName)).orElse(null);
            if (room != null) {
                room.addUser(username);
            }
        }
    }

    public void registerAndJoin(String username, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (map.containsKey(roomName)) {
            map.get(roomName).addUser(username);
        }

        registeredUser.add(username);

    }

    public void joinRoom(String username, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (!map.containsKey(roomName)) {
            throw new NoSuchRoomException(username);
        }
        if (!registeredUser.contains(username)) {
            throw new NoSuchUserException(username);
        }
        map.get(roomName).addUser(username);
    }

    public void leaveRoom(String username, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if (!map.containsKey(roomName)) {
            throw new NoSuchRoomException(username);
        }
        if (!registeredUser.contains(username)) {
            throw new NoSuchUserException(username);
        }
        map.get(roomName).removeUser(username);
    }

    public void followFriend(String username, String friend_username) throws NoSuchUserException {
        if (!registeredUser.contains(username)) {
            throw new NoSuchUserException(username);
        }
        for (ChatRoom room : map.values()) {
            if (room.hasUser(friend_username)) {
                room.addUser(username);
            }
        }
    }
}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException(String roomName) {
        super(roomName);
    }
}

class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
}