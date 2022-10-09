# encrypt
A small program that performs simple encryption to text by shifting characters a number of steps. There are two possible algoritms to choose from. One is the Caesar cipher
that shifts only through the US alphabet and keeping the letter case. The other shifts through the 16-bit unicode characters. 
This project is part of JetBrains Academy's Basic Kotlin Track.

The program uses command line arguments as follows:
* -mode <mode>, where <mode> can be either enc for encryption or dec for decryption
* -alg <algorithm>, where the <algorithm> can be either shift for the Caesar cipher or unicode for shifting through all 1-bit unicode characters
* -key <key>, where <key> is the number each letter should be shifted when encrpted or decrypted
* -data <text>, where <text> is the text that should be encrypted or decrypted
* -in <filename>, where <filename> is the path and the name of the file where the text is to be encrypted or decrypted depending on the mode argument. If both the -data arguemnt and the -in argument exists then the -data argument is used.
* -out <filename>, where <filename> is the path and the name of the file where the result of the encrypted or decrypted text are stored. If no -out argument exists then the result is printed on the screen.

Example of usage:
encrypt -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode
encrypt -key 5 -alg shift -data "Welcome to hyperskill!" -mode enc
