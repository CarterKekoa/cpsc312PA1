/**
 * Carter Mooring
 * PA1  CPSC 314
 * Dr. Sprint
 * Sept. 5th, 2019
 */

import java.util.*;

public class Main {
    static final int NUM_OF_WORDS = 10;
    static final int NUM_OF_LETTERS = 26;
    static final int NUM_OF_INC_GUESSES = 7;

    //Fills an array with words to be used in the game
    public static void wordsToGuess(String [] myWords){
        myWords[0] = "tesseract";
        myWords[1] = "vibranium";
        myWords[2] = "mjolnir";
        myWords[3] = "jarvis";
        myWords[4] = "avengers";
        myWords[5] = "wakanda";
        myWords[6] = "mixtape";
        myWords[7] = "assemble";
        myWords[8] = "queens";
        myWords[9] = "inevitable";
    }

    public static String wordSelector(String [] myWords, int randomNum){
        int length = myWords[randomNum].length();       //sets the length of the word to guess to a variable
        System.out.println("The word to guess has " + length + " letters.");
        for(int i = 0; i < length; i++){
            System.out.print("-");
        }
        System.out.println();
        return myWords[randomNum];                     //return the random word selected from array
    }

    public static String[] wordRemover(int randomNum, String[] myWords){
        List<String> list = new ArrayList<String>(Arrays.asList(myWords));
        list.remove(myWords[randomNum]);
        myWords = list.toArray(new String[0]);
        return myWords;
    }

    public static char[] avaliableLetters(char[] letters){
        for (int i = 0; i < NUM_OF_LETTERS; i++){       //for loop to iterate the whole alphabet
            letters[i] = (char)(97 + i);                //fill the already created array with lowercase letters
        }
        return letters;
    }

    public static int usersGuess(Scanner kb) {
        System.out.println("Please enter your guess: ");//ask for users guess letter
        char letterGuess = kb.next().charAt(0);         //get users guess letter
        int letterToAscii = (int) letterGuess;          //store users guess as a ascii value
        return letterToAscii;
    }

    public static char[] visibleLetters(char[] wordToGuess, int asciiGuess){
        //int lava = 0;                                   //lava int to track if letter isnt in word
        int length = wordToGuess.length;                //get the length of word that needs to be guessed
        char[] visibleLetters = new char[length];       //create new array same size as word to be guessed

        for(int i = 0; i < length; i++){                //fill new array with '-'
            visibleLetters[i] = '-';
        }

        char userGuess = (char) asciiGuess;             //convert users guess back to char
        for(int j = 0; j < length; j++){                //iterate through whole word
            if(userGuess == wordToGuess[j]){            //check each spot in char array for users letter
                visibleLetters[j] = userGuess;          //sets second array '-' to users correct guess
            }
        }

        return visibleLetters;
    }

    public static char playAgain(Scanner kb){
        char playAgain;                                 //char variable for playing again
        System.out.println("Do you want to play again? Enter y or n. ");
        playAgain = kb.next().charAt(0);                //user inputs y or n
        return playAgain;                               //return users input
    }

    public static void main(String[] args) {
        int bound = 9;                                  //bounds used for array length
        int go = 0;                                     //restricts some code from running on first play
        char deleteWord = 'n';                          //will hold the value of playAgain
        Random random = new Random();                   //random
        Scanner kb = new Scanner(System.in);            //Scanner available to receive user input
        String[] myWords = new String[NUM_OF_WORDS];    //create the array, unfilled
        wordsToGuess(myWords);                          //pass the array and fill
        char[] letters = new char[NUM_OF_LETTERS];      //create the alphabet array, unfilled
        char[] visibleLetter = new char[letters.length];

        do{
            int youLose = NUM_OF_INC_GUESSES;               //equal to total num of guesses, set to if 0 you lose
            int randomNum = random.nextInt(bound);      //choose a random number out of 10 (0-9)

            if(go > 0) {
                if (deleteWord == 'y') {
                    bound -= 1;
                    myWords = wordRemover(randomNum, myWords);
                }
            }

            String randomWord = wordSelector(myWords, randomNum);      //assign random word to guess from array to a variable
            avaliableLetters(letters);                      //fill the alphabet array
            System.out.print("Available letters: ");
            System.out.println(Arrays.toString(letters));

            while(youLose != 0){
                int asciiGuess = usersGuess(kb);                //stores users char guess in ascii form in new variable
                char[] wordToGuess = randomWord.toCharArray();  //convert the word to guess into a char array
                int checkWord = 0;

                if(asciiGuess < 97 || asciiGuess > 122){
                    asciiGuess = usersGuess(kb);
                } else {
                    letters[asciiGuess - 97] = (char) 32;           //sets users guessed letter to a space

                    visibleLetter = visibleLetters(wordToGuess, asciiGuess);

                    char charGuess = (char) asciiGuess;
                    int l = 0;

                    for(int i = 0; i < wordToGuess.length; i++){
                            if (charGuess == wordToGuess[i]) {
                                l = i;
                                System.out.println("Nice! " + charGuess + " is in the word.");
                                System.out.println();
                                visibleLetter[i] = charGuess;
                                break;
                            } else {
                                checkWord += 1;

                                if (checkWord == wordToGuess.length) {
                                    System.out.println(charGuess + " is not in the word. Too bad. ");
                                    youLose -= 1;
                                    System.out.print(youLose + " incorrect guesses remaining.");
                                    System.out.println();
                                    break;
                                }
                        }
                    }
                        for (int m = 0; m != wordToGuess.length; m++) {
                            if (visibleLetter[m] != visibleLetter[l]) {
                                System.out.print('-');
                            } else {
                                System.out.print(visibleLetter[l]);
                            }
                        }
                    System.out.println();

                    for(int j = 0; j < NUM_OF_LETTERS; j++){
                        if(charGuess == letters[j]){
                            letters[j] = ' ';
                        }
                    }
                    System.out.print("Available letters: ");
                    System.out.println(letters);
                }
            }
            go += 1;
            deleteWord = playAgain(kb);
        } while(deleteWord != 'n');                  //if user say y then they will play again

    }
}
