import java.util.List;

public class PL extends Account {
    private boolean isAdmin;

    // Methods
    public void assignIssues(List<Issue> issues) {
        // Implementation here
    }

    public void deleteIssue(Issue issue) {
        // Implementation here
    }

    public void addTeam(Account account, String nickname) {
        // Implementation here
    }

    // Getters and Setters
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}