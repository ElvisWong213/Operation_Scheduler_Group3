package backend.diary;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import backend.dataStructure.exception.DuplicateElementException;

import java.io.File;
import java.sql.Date;
import java.sql.Time;

public class DiaryTest {
    private Diary diary;

    @Before
    public void setUp() {
        diary = new Diary();
    }

    @Test
    public void testAddEntry() throws DuplicateElementException {
        DiaryEntry entry = new DiaryEntry(Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        diary.addEntry(entry);
        assertEquals(1, diary.getListOfEntries().size());
    }

    @Test
    public void testDeleteEntry() throws DuplicateElementException {
        DiaryEntry entry = new DiaryEntry(Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        diary.addEntry(entry);
        diary.deleteEntry(entry);
        assertEquals(0, diary.getListOfEntries().size());
    }

    @Test
    public void testEditEntry() throws DuplicateElementException {
        DiaryEntry entry = new DiaryEntry(1, Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        diary.addEntry(entry);
        entry.setNote("Updated entry");
        diary.editEntry(entry);
        assertEquals("Updated entry", diary.getListOfEntries().get(0).getNote());
    }

    @Test
    public void testSaveToDatabase() throws DuplicateElementException {
        // Assuming a database connection is available for testing
        DiaryEntry entry = new DiaryEntry(Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        diary.addEntry(entry);
        diary.saveToDatabase();
        // Add assertions to verify that the entries are saved correctly
    }

    @Test
    public void testLoadFromDatabase() {
        // Assuming a database connection is available for testing
        diary.loadFromDatabase(1, 1);
        assertEquals(1, diary.getListOfEntries().size()); // Assuming two entries are loaded from the database
    }

    @Test
    public void testUndo() throws DuplicateElementException {
        DiaryEntry entry = new DiaryEntry(Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        diary.addEntry(entry);
        diary.undo();
        assertEquals(0, diary.getListOfEntries().size());
    }

    @Test
    public void testRedo() throws DuplicateElementException {
        DiaryEntry entry = new DiaryEntry(Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        diary.addEntry(entry);
        diary.undo();
        diary.redo();
        assertEquals(1, diary.getListOfEntries().size());
    }

    @Test
    public void testWriteReadFile() throws DuplicateElementException {
        DiaryEntry entry = new DiaryEntry(Date.valueOf("2023-05-20"), Time.valueOf("10:30:00"), "Test entry", 1, 1);
        DiaryEntry entry2 = new DiaryEntry(Date.valueOf("2023-05-21"), Time.valueOf("23:30:00"), "Test entry2", 2, 2);
        diary.addEntry(entry);
        diary.addEntry(entry2);

        String fileName = "test1";
        diary.writeFile(fileName);

        File file = new File(fileName + ".json");
        assertTrue(file.exists());

        diary.readFile(fileName + ".json");
    }
}

