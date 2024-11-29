package edu.lms.controllers.librarian;

import edu.lms.models.book.Book;
import edu.lms.models.issue.Issue;
import edu.lms.models.user.Client;
import edu.lms.models.user.UserManager;
import edu.lms.services.database.IssuesDao;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class IssueDetailsController {
    @FXML
    private TextArea issueInfo;

    @FXML
    private Label authors;

    @FXML
    private ImageView avatar;

    @FXML
    private Label email;

    @FXML
    private Label fixedDate;

    @FXML
    private Label id;

    @FXML
    private Label language;

    @FXML
    private Label pageCount;

    @FXML
    private TextArea remark;

    @FXML
    private Label reportedDate;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Label title;

    @FXML
    private Label username;

    @FXML
    private Label warningTypeSt;

    @FXML
    private Label successfullyMark;

    private Issue issue;

    @FXML
    public void initialize(Issue issue) {
        this.issue = issue;
        Client client = UserManager.getClient(issue.getReporterId());
        assert client != null;
        id.setText(Integer.toString(client.getId()));
        username.setText(client.getUsername());
        email.setText(client.getEmail());
        avatar.setImage(new Image(client.getAvatarPath()));
        Book bok = issue.getIssueBook();
        title.setText(bok.getTitle());
        authors.setText(bok.getAuthors());
        pageCount.setText(Integer.toString(bok.getPageCount()));
        language.setText(bok.getLanguage());
        thumbnail.setImage(new Image(bok.getCoverImage()));
        issueInfo.setText(issue.getDescription());
        issueInfo.setEditable(false);
        reportedDate.setText(issue.getReportedDate().toString());
        if (issue.getReportedDate() != null) {
            fixedDate.setText(issue.getFixedDate().toString());
        }
    }

    @FXML
    private void remarkIssue() {
        String feedback = remark.getText();
        if (feedback == null) {
            warningTypeSt.setVisible(true);
            successfullyMark.setVisible(true);
            PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
            hideLabel.setOnFinished(e -> warningTypeSt.setVisible(false));
            hideLabel.play();
            return;
        }
        if (!IssuesDao.markIssue(feedback, issue.getIssueId())) {
            System.out.println("Error can not mark issue");
        }
        issue.setRemark(feedback);
        successfullyMark.setVisible(true);
        PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
        hideLabel.setOnFinished(e -> successfullyMark.setVisible(false));
        hideLabel.play();
    }
}
