package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.common.ReviewController;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


public class ClientBookDetailsController extends ReviewController {

    @FXML
    private Label authorsLabel;

    @FXML
    private Label categoriesLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label languageLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private Label publishedYearLabel;

    @FXML
    private ImageView thumbnail;


    public void initialize(Book book) {
        System.out.println("initialize book detail.");
        initializeBookData(book);
        super.initialize(book, DashboardController.client.getId());
    }

    private void initializeBookData(Book book) {
        authorsLabel.setText(book.getAuthors());
        pageCountLabel.setText(Integer.toString(book.getPageCount()));
        publishedYearLabel.setText(book.getPublishedYear());
        languageLabel.setText(book.getLanguage());
        categoriesLabel.setText(book.getCategories());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setText(book.getDescription());
        descriptionTextArea.setEditable(false);
        thumbnail.setImage(book.getThumbnail());
    }


    @FXML
    private void backToClientDashboardViewView() {
        ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
    }
}
