public class File extends Entry{
    String content;
    int size;
    public File(Directory parent, String name, String content, int size) {
        super(parent, name);
        this.content = content;
        this.size = size;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int size() {
        return this.size;
    }
    // public boolean delete()
}
