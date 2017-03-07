package io.behindthemath.mjolnir;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.attack.BruteForce;
import io.behindthemath.mjolnir.run.AttackRunner;
import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;

/**
 * Runs the attack program
 * 
 * @author Antony Lees
 */
public class Main {

    public static void main(String[] args) {

        // possible characters used
        char[] charset = { '!', '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z' };

        // setup the attack type
        Attack attack = new BruteForce(charset, 1, null);

        // set up the source
        Source source = new KeystoreSource("Test keystore.jks");
        source.setup();

        // run the attack
        AttackRunner attackRunner = new AttackRunner(attack, source, 100, 20000);
        attackRunner.start();
    }

}
