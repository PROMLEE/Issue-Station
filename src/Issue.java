import java.util.List;

public class Issue {
    private String issueName;
    private String issueID;
    private boolean issueStatus;
    private String author;
    private List<Comment> comments;

    // Methods
    public void addComment(Comment comment) {
        // Implementation here
    }

    // Getters and Setters
    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public boolean isIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(boolean issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}