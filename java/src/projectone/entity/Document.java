package projectone.entity;

/**
 * Representation of a document that is to be indexed
 *
 * @author Stephen Prizio
 */
public class Document {
    private int documentID;
    private String title;
    private String body;

    /**
     * Regular constructor that builds a document with an id, title and some text
     *
     * @param docId - document id
     * @param title - document title
     * @param body - document body
     */
    public Document(int docId, String title, String body) {
        this.documentID = docId;
        this.title = title;
        this.body = body;
    }


    //  ACCESSORS

    public int getDocumentID() {
        return documentID;
    }

    public String getDocumentTitle() {
        return title;
    }

    public String getDocumentBody() {
        return body;
    }

}
