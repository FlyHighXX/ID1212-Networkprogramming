/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1212.chatapplication.server.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author Diaco Uthman
 */
public class Conversation {
    private static final Set<Session> members = Collections.synchronizedSet(new HashSet<Session>());

    public void addUser(Session session) {
        members.add(session);
    }

    public void removeUser(Session session) {
        members.remove(session);
    }
    
    public boolean checkUser(Session session){
        return members.contains(session);
    }
}
