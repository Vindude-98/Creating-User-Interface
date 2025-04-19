import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class MenuInterface extends Application {
	private TextField timeOutput = null;
	private Scene scene = null;				// Scene contains all content
	private GridPane gridPane = null;		// Positions components within scene
	private final Random random = new Random();
	
	@Override
	public void start(Stage applicationStage) {
						
		MenuItem menuItem1 = new MenuItem("Option 1");
		MenuItem menuItem2 = new MenuItem("Option 2");
		MenuItem menuItem3 = new MenuItem("Option 3");
		MenuItem menuItem4 = new MenuItem("Option 4");

		MenuButton menuButton = new MenuButton("Options", null, menuItem1, menuItem2, menuItem3, menuItem4);
		
		HBox hbox = new HBox(menuButton);
		
		gridPane = new GridPane();
		gridPane.add(hbox, 0, 0);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setPrefWidth(300);
		gridPane.setPrefHeight(200);

		scene = new Scene(gridPane);
						
		menuItem1.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				// Get current date and time
				LocalDateTime now = LocalDateTime.now();
				
				// Format date and time
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss");
				
				// Print it
				String formattedDateTime = now.format(formatter);
				
				timeOutput = new TextField();
				timeOutput.setText(formattedDateTime);
				gridPane.add(timeOutput, 1, 0);
				
			}
		});
		
		menuItem2.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				// Button writes the output from button 1 to a log.txt file
				String text;
				text = timeOutput.getText();
				writeToFile("log.txt", text);
				
			}
			
			private void writeToFile(String fileName, String content) {
				try (PrintWriter output = new PrintWriter(new FileWriter(fileName, true))){
					output.println(content);
				} catch(IOException e) {
					
				}
			}
		});
		
		
		menuItem3.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				Color randomGreen = getRandomGreen();
				gridPane.setBackground(new Background(new BackgroundFill(randomGreen, null, null)));
				updateMenuItem(menuItem3);
			}
			
			// Generates a random green color with varying hue
			private Color getRandomGreen() {
				double hue = 90 + random.nextDouble() * 60; 
				return Color.hsb(hue, 0.7 + random.nextDouble() * 0.3, 0.7 + random.nextDouble() * 0.3);
			}
			
			// Updates the menuItem with a preview of the next color
			private void updateMenuItem(MenuItem item) {
				Color previewColor = getRandomGreen();
				
				// Create a small rectangle to show the color
				Rectangle swatch = new Rectangle(16, 16);
				swatch.setFill(previewColor);
				swatch.setStroke(Color.GRAY); // gives it a border
				
				String rgbHex = String.format("#%02X%02X%02X",
		                (int)(previewColor.getRed() * 255),
		                (int)(previewColor.getGreen() * 255),
		                (int)(previewColor.getBlue() * 255));
				
				item.setGraphic(swatch);
			}
		});
		
		menuItem4.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		
				
		applicationStage.setScene(scene);
		applicationStage.setTitle("User Interface");
		applicationStage.sizeToScene();
		applicationStage.show();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}


/*
Things we need: 
1. Frame that has a top menu bar
2. Option 1 - displays current date/time
3. Option 2 - current display (textField) is written to a file named "log.txt"
4. Option 3 - Frame background changes to random hue of the color green. 
			  The menu option should display the random color before each 
			  execution
5. Option 4 - Program Exits 
*/