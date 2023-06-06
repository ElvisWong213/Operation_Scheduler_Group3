package backend.diary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import backend.dataStructure.BinaryTree;
import backend.dataStructure.MyLinkedList;
import backend.dataStructure.MyStack;
import backend.dataStructure.exception.DuplicateElementException;
import backend.type.DiaryAction;
import database.Database;

public class Diary {
    private BinaryTree<DiaryEntry> diaryEntries;
    private MyLinkedList<DiaryEntry> deletedDiaryEntries;
    private static MyStack<Action> undoStack = new MyStack<>();
    private static MyStack<Action> redoStack = new MyStack<>();

    private class Action {
        private DiaryEntry data;
        private DiaryAction diaryAction;
        
        public Action(DiaryEntry data, DiaryAction action) {
            this.data = data;
            this.diaryAction = action;
        }
    }

    public Diary() {
        this.diaryEntries = new BinaryTree<>();
        this.deletedDiaryEntries = new MyLinkedList<>();
    }

    public void addEntry(DiaryEntry entry, Boolean newAction) throws DuplicateElementException {
        diaryEntries.add(entry);
        if (newAction) {
            undoStack.push(new Action(entry, DiaryAction.Add));
            clearRedo();
        }
    }

    public void deleteEntry(DiaryEntry entry, boolean newAction) {
        diaryEntries.remove(entry);
        deletedDiaryEntries.add(entry);
        if (newAction) {
            undoStack.push(new Action(entry, DiaryAction.Remove));
            clearRedo();
        }
    }

    public void editEntry(DiaryEntry entry, boolean newAction) {
        diaryEntries.update(entry);
        if (newAction) {
            undoStack.push(new Action(entry, DiaryAction.Edit));
            clearRedo();
        }
    }

    public MyLinkedList<DiaryEntry> getListOfEntries() {
        return diaryEntries.inOrder();
    }

    public DiaryEntry getDataByIndex(int index) {
        return getListOfEntries().get(index);
    }

    public void saveToDatabase() {
        for (DiaryEntry entry : deletedDiaryEntries) {
            removeFromDatabase(entry);
        }
        for (DiaryEntry entry : diaryEntries.inOrder()) {
            if (entry.getId() == 0) {
                insertToDatabase(entry);
            } else {
                editToDatabase(entry);
            }
        }
        clearUndo();
        clearRedo();
    }

    private void insertToDatabase(DiaryEntry diaryEntry) {
        try {
            Date date = diaryEntry.getDate();
            Time time = diaryEntry.getTime();
            String note = diaryEntry.getNote();
            int professionalID = diaryEntry.getProfessionalID();
            int patientID = diaryEntry.getPatientID();
            Database db = new Database();
            String query = String.format("INSERT INTO diary (date, time, note, professional_id, patient_id) VALUES ('%s', '%s', '%s', %d, %d)", date, time, note, professionalID, patientID);
            db.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Fail to add diary");
        }
    }

    private void removeFromDatabase(DiaryEntry diaryEntry) {
        if (diaryEntry.getId() == 0) {
            return;
        }
        try {
            int id = diaryEntry.getId();
            Database db = new Database();
            String query = String.format("DELETE FROM diary WHERE diary_id = %d;", id);
            db.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Fail to remove diary");
        }
    }

    private void editToDatabase(DiaryEntry diaryEntry) {
        try {
            int id = diaryEntry.getId();
            Date date = diaryEntry.getDate();
            Time time = diaryEntry.getTime();
            String note = diaryEntry.getNote();
            Database db = new Database();
            String query = String.format("UPDATE diary SET date = '%s', time = '%s', note = '%s' WHERE diary_id = %d;", date, time, note, id);
            db.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Fail to remove diary");
        }
    }

    public void loadFromDatabase(int professionalID, int patientID) {
        diaryEntries.clear();
        Database db = null;
        try {
            db = new Database();
            String query = String.format("SELECT * FROM diary WHERE (professional_id = %d OR patient_id = %d);", professionalID, patientID);
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("diary_id");
                Date date = Date.valueOf(rs.getString("date"));
                Time time = Time.valueOf(rs.getString("time"));
                String note = rs.getString("note");
                int proID = rs.getInt("professional_id");
                int patID = rs.getInt("patient_id");

                DiaryEntry diaryEntry = new DiaryEntry(id, date, time, note, proID, patID);
                diaryEntries.add(diaryEntry);
            }
        } catch (SQLException e) {
            System.out.println("Fail connect to database");
            e.printStackTrace();
        } catch (DuplicateElementException d) {
            d.getMessage();
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void undo() throws DuplicateElementException, NoSuchElementException {
        Action action = undoStack.pop();
        DiaryAction newDiaryAction = perform(action, false);
        redoStack.push(new Action(action.data, newDiaryAction));
    }
    
    public void redo() throws DuplicateElementException, NoSuchElementException {
        Action action = redoStack.pop();
        DiaryAction newDiaryAction = perform(action, false);
        undoStack.push(new Action(action.data, newDiaryAction));
    }
    
    private DiaryAction perform(Action action, boolean newAction) throws DuplicateElementException {
        DiaryAction newDiaryAction = null;
        switch (action.diaryAction) {
            case Add:
                deleteEntry(action.data, newAction);
                deletedDiaryEntries.add(action.data);
                newDiaryAction = DiaryAction.Remove;
                break;
            case Edit:
                editEntry(action.data, newAction);
                newDiaryAction = DiaryAction.Edit;
                break;
            case Remove:
                addEntry(action.data, newAction);
                deletedDiaryEntries.removeByData(action.data);
                newDiaryAction = DiaryAction.Add;
                break;
            default:
                break;
        }
        return newDiaryAction;
    }


    public static void clearUndo() {
        undoStack = new MyStack<>();
    }
    
    public static void clearRedo() {
        redoStack = new MyStack<>();
    }

    public void writeFile(String fileName) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (DiaryEntry entry : diaryEntries.inOrder()) {
            jsonArray.put(entry.toJSON());
        }

        FileWriter fw = null;

        try {
            fw = new FileWriter(fileName + ".json");
            fw.write(jsonArray.toString());
            fw.close();
            System.out.println("The data has been written to a JSON file");
        } catch (IOException e) {
            throw new IOException("An error occurred while writing to the file.");
        } finally {
            fw.close();
        }
    }
    
    public void readFile(String fileURL) throws FileNotFoundException, JSONException, DuplicateElementException, IOException {
        FileReader fr = new FileReader(fileURL);
        JSONTokener tokener = new JSONTokener(fr);
        JSONArray jsonArray = new JSONArray(tokener);
        for (int i = 0; i < jsonArray.length(); i++) {
            diaryEntries.add(new DiaryEntry(jsonArray.getJSONObject(i)));
        }
        System.out.println("JSON file has been read");
        fr.close();
    }
}
