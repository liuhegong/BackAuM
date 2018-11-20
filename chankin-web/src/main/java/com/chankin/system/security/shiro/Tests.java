package com.chankin.system.security.shiro;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Tests {

    @Test
    public void ss() {
        class User {
            public String name;

            public User(String name) {
                this.name = name;
            }
        }

        Collection<User> originalCollection = new ArrayList<User>();
        originalCollection.add(new User("a"));
        originalCollection.add(new User("b"));
        originalCollection.add(new User("c"));
        Collection<User> unmodifiableCollection = Collections.unmodifiableCollection(originalCollection);
        for (User user : originalCollection) {
            user.name = "ssss";
        }

        System.out.println("Original Size=" + unmodifiableCollection.size());
        unmodifiableCollection.add(new User("d"));
        System.out.println("New Size=" + unmodifiableCollection.size());
        for (User user : unmodifiableCollection) {
            System.out.println(user.name);
        }
    }
}


