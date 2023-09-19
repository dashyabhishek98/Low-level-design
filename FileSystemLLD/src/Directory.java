import java.util.ArrayList;
import java.util.List;

public class Directory extends Entry{
    public List<Entry> getListOfEntry() {
        return listOfEntry;
    }
    public void setListOfEntry(List<Entry> listOfEntry) {
        this.listOfEntry = listOfEntry;
    }
    private List<Entry> listOfEntry;
    Directory(){}
    public Directory(Directory parent, String name) {
        super(parent, name);
        this.listOfEntry = new ArrayList<>();
    }
    public List<Entry> getList() {
        return listOfEntry;
    }
    public void setList(List<Entry> list) {
        this.listOfEntry = list;
    }
    public boolean addlistOfEntry(Entry entry){
        return this.listOfEntry.add(entry);
    }

}
