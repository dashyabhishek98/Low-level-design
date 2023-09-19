public abstract class Entry {
    private Directory parent;
    private String name;
    private long created;
    private long lastUpdated;

    public Entry(Directory parent, String name) {
        this.parent = parent;
        this.name = name;
        this.created = System.currentTimeMillis();
        this.lastUpdated = System.currentTimeMillis();
    }



    Entry() {}

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
