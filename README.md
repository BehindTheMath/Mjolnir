# Mjolnir
Mjolnir is a tool that attempts to crack the password to a Java keystore or key using a brute force algorithm, using multi-threading to optimize excution time.

This is a fork of Antony Lees' [Mjolnir project](https://sourceforge.net/projects/mjolnir-utils/). He wrote an excellent [blog post](https://antonylees.blogspot.com/2012/05/how-i-brute-forced-my-own-keystore.html) explaining the rationale behind his algorithms and code.  
Since his project was not on Github, I was unable to fork it. However, I did clone it with git, and it is preserved in this repo, in the [`original`](/tree/original) branch.

## Changelog
The basic structure of the project is the same as Antony Lees' original Mjolnir project.  
I had originally planned on only adding a command line argument parser to make it more flexible. However, one thing led to another, and I ended up changing and adding a lot more.

##### v0.1.0
- Command line arguments instead of hard-coded values
- Gradle build system to simplify building and testing
- Ability to start in middle of a run
- Switched from Threads and Runnables to ExecutorService and Callables
- Added a BufferedInputStream to the KeystoreLoader (this increased performance by over 4x)
- Lots of unit tests

## Usage
1. Download the latest `mjolnir.jar` file from the [Releases](/releases) page.
2. Run the program using the following syntax: `java -jar mjolnir.jar options`.

#### Command Line Options
| Switch | Argument | Explanation |
| ------ | -------- | ----------- |
| -s | ["keystore" \| "key"] | Determines which password is being brute forced: the keystore or a key inside the keystore. This argument must be first.
| -p | password | The password for the keystore. Required if you want to brute-force the password of a key.
| -k | keyname | The name of the key to brute-force.
| -f | filepath | The path to the keystore file. Can be relative to the working directory.
| -t | threads | The number of concurrent threads to use (default is 4).
| -n | numAttempts | Log the progress every numAttempts for each thread (default is 20000). Set to 0 to disable logging.
| -l | lastattempt | The last attempt already tried. The program will start after that attempt.
| -m | minGuessLength | The minimum guess length to attack.
| -x | minGuessLength | The maximum guess length to attempt (default is the same as minGuessLength).
| -c | characterSet | The set of possible characters to try.

##### Examples:
`java -jar mjolnir.jar -s keystore -f test_keystore.jks -t 4 -n 20000 -l taaa -m 4 -x 5 -c abcdefghijklmnopqrstuvwxyz`

`java -jar mjolnir.jar -s key -f test_keystore.jks -p test -k "test key" -t 4 -n 20000 -m 5 -c abcdefghijklmnopqrstuvwxyz1234567890`

## To do
- Instead of calculating the next guess on the fly when it's requested, use a separate thread to preload guesses into a BlockingDeque, and have the other threads pull guess from it.  
In my tests, this didn't result in a performance gain, but it might help prevent concurrency issues and race conditions.
- Instead of using the standard Keystore API to attempt loading the keystore, create a custom implementation that only does the necessary calculations to validate the attempted password. This should improve performabce by eliminating unnecessary calculations.

## License

    Copyright © 2017 Behind The Math

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.