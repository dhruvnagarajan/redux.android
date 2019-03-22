package com.dhruvnagarajan.redux.android;

import java.util.HashSet;

/**
 * @author Dhruvaraj Nagarajan
 */
public class Store {

    enum Singleton {

        INSTANCE(new HashSet<String>());

        private HashSet<String> set;

        Singleton(HashSet<String> set) {
            this.set = set;
        }

        public HashSet<String> getStore() {
            return set;
        }
    }
}
