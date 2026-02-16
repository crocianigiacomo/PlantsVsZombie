package tp1.p2.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.RecordException;
import tp1.p2.view.Messages;

public class Record {
	//Constantes
	private static final int INITIAL_RECORD = 0;
	//Atributos
	private int currentRecord;
	private Level level;
	//Constructor
	public Record() {}
	public void reconfigureRecord (Level lv) throws GameException{
		level = lv;
		load();
	}
	//getters
	public int getRecord() {return currentRecord;}
	//setters
	public void setRecord(int newValue) {currentRecord = newValue;}
	// In/Out
	public void load() throws GameException {
		try(BufferedReader inStream = new BufferedReader(new FileReader(Messages.RECORD_FILENAME))) {
			String line = inStream.readLine();
			//line = inStream.readLine();
			while(line != null && !line.isEmpty()) {
				String tokens[] = line.split(":");
				
				if(level.name().equalsIgnoreCase(tokens[0])) {
					try {
						currentRecord = Integer.parseInt(tokens[1]);
						return;
					} catch (NumberFormatException nfe) {
						throw new RecordException(Messages.RECORD_READ_ERROR);
					}
				} else currentRecord = INITIAL_RECORD;				
				line = inStream.readLine();
			}
			inStream.close();
		} catch (FileNotFoundException fnfe) {
			throw new RecordException(Messages.RECORD_READ_ERROR, fnfe);
		} catch (IOException ioe) {
			throw new RecordException(Messages.RECORD_READ_ERROR, ioe);
		}
	}
	public void save() throws GameException {
		StringBuilder lines = new StringBuilder();
		//read
		try(BufferedReader inStream = new BufferedReader(new FileReader(Messages.RECORD_FILENAME))){
			String line = inStream.readLine();
			
			while (line != null && !line.isEmpty()) {
				String tokens[] = line.split(":");
				
				if(!level.name().equalsIgnoreCase(tokens[0]))
					lines.append(line);
				lines.append(Messages.LINE_SEPARATOR);
				line = inStream.readLine();
			}
			inStream.close();
		} catch (FileNotFoundException fnfe) {
			throw new RecordException(Messages.RECORD_WRITE_ERROR, fnfe);
		} catch (IOException ioe) {
			throw new RecordException(Messages.RECORD_WRITE_ERROR, ioe);
		}
		lines.append(level.name()).append(":").append(currentRecord);
		//write
		try(BufferedWriter outStream = new BufferedWriter(new FileWriter(Messages.RECORD_FILENAME))){
			outStream.append(lines);
			outStream.close();
		} catch (IOException ioe) {
			throw new RecordException(Messages.RECORD_WRITE_ERROR, ioe);
		}
	}
}