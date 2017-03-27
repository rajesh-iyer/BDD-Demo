package com.xpanxion.bdd.tests;

import org.junit.After;
import org.junit.Before;

public class Hooks {

    @Before
    public void setup() {
        System.out.println("Initialization here...");
    }
    
    @After
    public void tearDown(){
    System.out.println("...Tear Down here");
    }
}
