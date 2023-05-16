package diary;
import java.util.ArrayList;

import user.Professional;

public class Diary {
    private Professional professional;
    private ArrayList<DiaryEntry> diaryEntries;

    public void addEntry(DiaryEntry entry) {
        diaryEntries.add(entry);
    }

    public void deleteEntry(DiaryEntry entry) {
        diaryEntries.remove(entry);
    }

    public void editEntry(DiaryEntry oldEntry, DiaryEntry newEntry) {
        int index = diaryEntries.indexOf(oldEntry);
        diaryEntries.add(index, newEntry);
    }

    public ArrayList<DiaryEntry> getEntries() {
        return diaryEntries;
    }

    public void saveToDatabase() {

    }

    public void loadFromDatabase() {

    }
}
