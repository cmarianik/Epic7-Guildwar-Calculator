import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;

import java.util.Scanner;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Adjusted
     * https://docs.google.com/spreadsheets/d/1jEqMATF2JF84c3Kvy5FMljS3XJplxgw12kGLTJWYbgk/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1jEqMATF2JF84c3Kvy5FMljS3XJplxgw12kGLTJWYbgk";
        final String range = "FinData!A2:K";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
		
		
		//SECOND STRINGBUILD FOR RawData
		final String range2 = "RawData!A2:H";
		ValueRange rawDataRange = service.spreadsheets().values()
				.get(spreadsheetId, range2)
				.execute();
		List<List<Object>> rawDataValues = rawDataRange.getValues();
		
		
		
		
		//REFERENCE code for printing.
		/* // logs out if the entire list of lists (row/column with given range) is empty
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } 
		//otherwise, print the 0th and 4th columns.
		else {			
            System.out.println("Unit, Speed");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
				if(row.size() >= 4){					//adding this because it seems it cannot scan rows that have empty tiles.
					System.out.printf("%s, %s\n", row.get(0), row.get(4));
				}
            }
        } */

		/* //REFERENCE code for updating cells
		ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList("updated")));
		Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, "FinData!A6", body);
		request.setValueInputOption("RAW");
		
		UpdateValuesResponse update = request.execute(); */
		
		
		
		
		
		
		//array to store them in
		Unit[] listOfUnits = new Unit[6];
		Unit[] listOfEnemyUnits = new Unit[3];
		
		
		
		
		
		
		
		int ucounter = 0;
		//initializing rawData Unit objects.
		for (int i = 0; i < 6; i++){
			if (rawDataValues == null || rawDataValues.isEmpty()) {
				System.out.println("No data found.");
			}
			else {			
				List row = rawDataValues.get(i);
				if(row.size() >= 4){
					Unit tempUnit = new Unit(Integer.valueOf((String) row.get(0)), row.get(1).toString(), Integer.valueOf((String) row.get(2)), row.get(3).toString(), row.get(4).toString(), row.get(5).toString());
					listOfUnits[i] = tempUnit;
					//add to enemy units list
					if(!tempUnit.getAlly()){
						listOfEnemyUnits[ucounter] = tempUnit;
						ucounter++;
					}
				}
			}
        }
		
		
		
		//ACTUAL CODE.
		displayData(values);
		System.out.println();
		selectAllyFastest(listOfUnits, listOfEnemyUnits, service, spreadsheetId);
		printRawData(rawDataValues);
		//displayData(values);						isn't actually updated by this point.
		
		
		
		
		/* ideas:
			-Unit Object/Class
			-FastestUserUnit - T/F
			-If FastestUserUnit:
				-Prompt User for Speed of Unit
				-Calculate Speed of other units based on Michael Equations
				-Output speeds into Data-Dump
		*/
		
		
		
		
		
		
		
    }
	
	
	
	
	
	
	
	//Currently prints out the whole relevant table.
	private static void displayData(List<List<Object>> values_){
		
		// logs out if the entire list of lists (row/column with given range) is empty
        if (values_ == null || values_.isEmpty()) {
            System.out.println("No data found.");
        } 
		//otherwise, print the 0th and 4th columns.
		else {			
            System.out.printf("%20s %7s %10s %10s %7s %10s %7s %7s %7s %7s %7s\n", "Unit", "Speed", "Artifact", "4 set", "2 set", "HP", "Def", "Atk", "CDmg", "Eff", "Res");
            for (int i = 0; i < 3; i++) {
                List row = values_.get(i);
				
				if(row.size() >= 11){					//adding this because it seems it cannot scan rows that have empty tiles.
					System.out.printf("%20s %7s %10s %10s %7s %10s %7s %7s %7s %7s %7s\n", row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6), row.get(7), row.get(8), row.get(9), row.get(10));
				}
            }
        }
	}
	
	
	//prompts user to input what their fastest unit is.
	private static void selectAllyFastest(Unit[] listOfUnits_, Unit[] listOfEnemyUnits_, Sheets service_, String spreadsheetId_) throws IOException{
		Unit fastest = null;
		Unit fastestUser = null;
		boolean userFastest = false;
		double topSpeed;
		
		for(Unit unit : listOfUnits_){
			if(unit.getFastestUnit()){	//set fastest unit overall
				fastest = unit;
				System.out.println("fastest unit discovered " + unit.getName());
			}
			if(unit.getFastestAlly()){	//set fastest allied unit
				fastestUser = unit;
				userFastest = fastestUser.getFastestAlly();
				System.out.println("fastest ally unit discovered " + unit.getName());
			}
		}

		
		
		
		Scanner scanv = new Scanner(System.in);
		System.out.print("Enter the speed of your highest unit: ");
		
		if((int) fastest.getCR() == (int) fastestUser.getCR()){
			//prompt user for fastest unit
			topSpeed = scanv.nextInt();
			System.out.println();
			
			System.out.println("The user's fastest unit is THE fastest unit");
			
		}
		else{
			int userTopSpeed = scanv.nextInt();
			System.out.println(userTopSpeed);
			topSpeed = userTopSpeed / (((double) fastestUser.getCR())/100);
			System.out.println(topSpeed);
			
			System.out.println("The user is NOT the fastest unit");
			
			
		}
		
		
		
		
		
		
		//make if statement for if user's unit is not the fastest unit.
		//topSpeed = 300;
		
		int sheetCounter = 2;		//janky sheet counter
		for(Unit unit : listOfEnemyUnits_){
			//print into FinData
			double tempSpeed = topSpeed*((double) unit.getCR()/100);
			int intSpeed = (int) tempSpeed;
			

			ValueRange speeds = new ValueRange().setValues(Arrays.asList(Arrays.asList(intSpeed+"")));
			String sheetSite1 = "FinData!B"+sheetCounter;
			Sheets.Spreadsheets.Values.Update speedRequest = service_.spreadsheets().values().update(spreadsheetId_, sheetSite1, speeds);
			speedRequest.setValueInputOption("RAW");
			UpdateValuesResponse speedUpdate = speedRequest.execute();
			
			//repeat for names
			ValueRange names = new ValueRange().setValues(Arrays.asList(Arrays.asList(unit.getName())));
			String sheetSite2 = "FinData!A"+sheetCounter;
			Sheets.Spreadsheets.Values.Update nameRequest = service_.spreadsheets().values().update(spreadsheetId_, sheetSite2, names);
			nameRequest.setValueInputOption("RAW");
			UpdateValuesResponse nameUpdate = nameRequest.execute();

			sheetCounter++;
			
			
		}
		
		
		
	}
	
	private static void printRawData(List<List<Object>> rawValues_){
		//print statement
		if (rawValues_ == null || rawValues_.isEmpty()) {
            System.out.println("No data found.");
        } 
		else {			
            System.out.printf("%10s %15s %5s %15s %7s %15s\n", "Slot", "Unit", "CR%", "Enemy/Ally", "Fastest?", "Fastest Ally?");
            for (int i = 0; i < 6; i++) {
                List row = rawValues_.get(i);
				
				if(row.size() >= 4){					//adding this because it seems it cannot scan rows that have empty tiles.
					System.out.printf("%10s %15s %5s %15s %7s %15s\n", row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5));
				}
            }
        }
	}
	
	
}