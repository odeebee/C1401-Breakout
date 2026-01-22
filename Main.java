// breakout game Main class - use this class to start the game

// We need to access some JavaFX classes so we list ('import') them here
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application
{
    String data;
    // the 'start' method - this creates the Model, View and Controller objects and
    // makes them talk to each other, it then sets up the user interface (in the View 
    // object) and starts the game running (in the Model object)
    public void start(Stage window) 
    {
        int H = 400;         // Height of game window (in pixels) 400, 300
        int W = 600;         // Width  of game window (in pixels)

        // set up debugging and print initial debugging message
        Debug.set(true);    // change this to 'false' to stop breakout printing messages         
        Debug.trace("Main::start: Breakout starting");  
        
        File highScore = new File("HighScore.txt");
        try(Scanner myReader = new Scanner(highScore)){
            while(myReader.hasNextLine()){
                data = myReader.nextLine();
                System.out.println(data);
            }
        }catch(FileNotFoundException e){
            System.out.println("Error file not found");
        }
        if(data == ""){
            System.out.println("There is no data in the file, writing 0");
            try(FileWriter myWriter = new FileWriter("HighScore.txt")){
                myWriter.write("0");
                myWriter.close();
            }catch(IOException i){
                System.out.println("Cant do it mate, sorry");
            }
            
        }
        

        // Create the Model, View and Controller objects
        Model model = new Model(W,H);
        View  view  = new View(W,H);
        Controller controller  = new Controller();

        // Link them together so they can talk to each other
        // Each one has instance variables for the other two
        view.controller = controller;
        view.model = model;
        controller.model = model;
        model.view = view;
        
        // start up the game interface (the View object, passing it the window
        // object that JavaFX passed to this method, and then tell the model to 
        // start the game
        view.start(window);                    
        model.startGame();

        // application is now running - print a debug message to say so
        Debug.trace("Main::start: Breakout running"); 
    }
    
    public static String readHighScore(){
        String output = "";
        File highScore = new File("HighScore.txt");
        try(Scanner myReader = new Scanner(highScore)){
            while(myReader.hasNextLine()){
                output = myReader.nextLine();
                System.out.println(output);
            }
        }catch(FileNotFoundException e){
            System.out.println("Error file not found");
        }
        return output;
    }
    
    public static void writeHighScore(String data){
        try(FileWriter myWriter = new FileWriter("HighScore.txt")){
            myWriter.write(data);
            myWriter.close();
        }catch(IOException i){
            System.out.println("Cant do it mate, sorry");
        }
    }
    
    // The 'main' method - this is only used when launching from the command line.
    public static void main( String args[] )
    {
        // 'launch' initialises the system and then calls 'start'
        // (When running in BlueJ, the menu option 'Run JavaFX Application'
        // calls 'start' itself)
        launch(args);
    }
}
