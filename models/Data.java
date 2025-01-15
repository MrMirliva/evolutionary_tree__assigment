package models;
public class Data {
    private int id;
    private String name;
    private int numberOfChildren;
    private boolean isLeaf;
    private String tolorgLink;
    private boolean extinct;
    private int confidence;
    private int phylesis;

    public Data(int id, String name, int numberOfChildren, boolean isLeaf, String tolorgLink, boolean extinct, int confidence, int phylesis) {
        this.id = id;
        this.name = name;
        this.numberOfChildren = numberOfChildren;
        this.isLeaf = isLeaf;
        this.tolorgLink = tolorgLink;
        this.extinct = extinct;
        this.confidence = confidence;
        this.phylesis = phylesis;
    }

    public Data () {

    }

    public Data(int node_id, String node_name,int child_nodes, int leaf_node, int tolorg_link, int extinct, int confidence, int phylesis) {
        this.id = node_id;
        this.name = node_name;
        this.numberOfChildren = child_nodes;
        this.isLeaf = leaf_node == 1;
        this.tolorgLink = tolorg_link == 1 ? "http://tolweb.org/${"+ node_name +"}/${"+ node_id +"}" : "Not Found";
        this.extinct = extinct == 1;
        this.confidence = confidence;
        this.phylesis = phylesis;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }
    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
    public void setLeaf(int leaf) {
        isLeaf = leaf == 1;
    }

    public String getTolorgLink() {
        return tolorgLink;
    }
    public void setTolorgLink(String tolorgLink) {
        this.tolorgLink = tolorgLink;
    }
    public void setTolorgLink(int tolorgLink) {
        this.tolorgLink = tolorgLink == 1 ? "http://tolweb.org/"+ name.replace(' ', '_') +"/"+ id : "Not Found";
    }
    public void setTolorgLink(boolean tolorgLink) {
        this.tolorgLink = tolorgLink ? "http://tolweb.org/"+ name.replace(' ', '_') +"/"+ id : "Not Found";
    }

    public boolean isExtinct() {
        return extinct;
    }
    public void setExtinct(boolean extinct) {
        this.extinct = extinct;
    }
    public void setExtinct(int extinct) {
        this.extinct = extinct == 1;
    }

    public int getConfidence() {
        return confidence;
    }
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public int getPhylesis() {
        return phylesis;
    }
    public void setPhylesis(int phylesis) {
        this.phylesis = phylesis;
    }

    @Override
    public String toString() {
        return 
            "id=" + id +
            ", name='" + name + '\'' +
            ", numberOfChildren=" + numberOfChildren +
            ", isLeaf=" + isLeaf +
            ", tolorgLink='" + tolorgLink + '\'' +
            ", extinct=" + extinct +
            ", confidence=" + (confidence == 0 ? "confidence position" : confidence == 1 ? "problematic position" : confidence == 2 ? "unspesific position" : "unknown") +
            ", phylesis=" + (phylesis == 0 ? "monophyletic" : phylesis == 1 ? "uncertain monophyly" : phylesis == 2 ? "not monophyly" : "unknown") ;
    }

    public void print() {
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Child count: " + numberOfChildren);
        System.out.println("Leaf node: " + (isLeaf ? "yes" : "no"));
        System.out.println("Link: " + tolorgLink);
        System.out.println("Extinct: " + (extinct ? "extinct" : "living"));
        System.out.println("Confidence: " +(confidence == 0 ? "confidence position" : confidence == 1 ? "problematic position" : confidence == 2 ? "unspesific position" : "unknown"));
        System.out.println("Phylesis: " + (phylesis == 0 ? "monophyletic" : phylesis == 1 ? "uncertain monophyly" : phylesis == 2 ? "not monophyly" : "unknown"));
    }
}

