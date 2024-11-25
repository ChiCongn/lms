package edu.lms;

import edu.lms.models.book.Book;
import edu.lms.models.user.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String SIGN_IN_VIEW = "/edu/lms/fxml/sign-in-view.fxml";
    public static final String SIGNUP_VIEW = "/edu/lms/fxml/sign-up-view.fxml";

    public static final String ADMIN_DASHBOARD_VIEW = "/edu/lms/fxml/dashboard-view.fxml";
    public static final String LIBRARIAN_DASHBOARD_VIEW = "/edu/lms/fxml/librarian-dashboard-view.fxml";
    public static final String CLIENT_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String TRENDING_DASHBOARD_VIEW = "/edu/lms/fxml/trending-dashboard.fxml";
    public static final String DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String CATEGORY_DASHBOARD_VIEW = "/edu/lms/fxml/category-dashboard.fxml";
    public static final String READING_DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String FAVOURITE_DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String HISTORY_DASHBOARD_VIEW = "/edu/lms/fxml/history-dashboard.fxml";
    public static final String GAME_DASHBOARD_VIEW = "/edu/lms/fxml/quiz-game.fxml";

    public static final String CLIENT_MANAGEMENT_VIEW = "/edu/lms/fxml/clients-management-view.fxml";
    public static final String BOOKS_MANAGEMENT_VIEW = "/edu/lms/fxml/books-management-view.fxml";
    public static final String CLIENTS_DETAILS_VIEW = "/edu/lms/fxml/user-details-view.fxml";
    public static final String BOOK_DETAILS_VIEW = "/edu/lms/fxml/book-details-view.fxml";

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchScene(String fxmlPath, MouseEvent event, Stage stage, Scene scene, Parent root ) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Book> recommendedBooks = FXCollections.observableArrayList();
    public static ObservableList<Book> recentlyAddedBooks = FXCollections.observableArrayList();
    public static ObservableList<Book> booksByCategory = FXCollections.observableArrayList();

    public static List<Book> recommended = new ArrayList<>();
    public static List<Book> recentlyAdded = new ArrayList<>();
    public static List<Book> horrorBooks = new ArrayList<>();
    public static List<Book> selfhelpBooks = new ArrayList<>();
    public static List<Book> educationBooks = new ArrayList<>();
    public static List<Book> artBooks = new ArrayList<>();

    public static List<Book> books = new ArrayList<>();
    public static List<Client> users = new ArrayList<>();

    public List<Client> getUsers() {
        List<Client> users = new ArrayList<>();
        Client user = new Client("kirito");
        users.add(user);

        Client user1 = new Client("Congdeptrai01");
        users.add(user1);

        Client user2 = new Client("Congdeptrai02");
        users.add(user2);

        Client user3 = new Client("Duysech69");
        users.add(user3);

        Client user4 = new Client("Datmaniac");
        users.add(user4);

        return users;
    }

    public List<Book> recentlyAdded() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("RICH DAD\nPOOR DAD");
        book.setCoverImage("/edu/lms/images/Books/rich_dad_poor_dad.jpg");
        book.setAuthors("Robert Kiyosaki");
        book.setDescription("abcs");

        book.setPublishedYear("111");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("MAN'S SEARCHING\n FOR MEANING");
        book1.setCoverImage("/edu/lms/images/Books/man's searching for meaning.jpg");
        book1.setAuthors("Viktor Frankl");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("THE STOICISM'S\n MIND");
        book2.setCoverImage("/edu/lms/images/Books/stoicism.jpg");
        book2.setAuthors("Nancy Sherman");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("THE STORYTELLER'S\n SECRET");
        book3.setCoverImage("/edu/lms/images/Books/storyteller's secrete.jpg");
        book3.setAuthors("Carmine Gallo");
        ls.add(book3);

        return ls;
    }

    public List<Book> recommendedBook(){
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("HARRY POTTER");
        book.setCoverImage("/edu/lms/images/Books/HarryPotter.jpg");
        book.setAuthors("J.K.Rowling");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("HOME");
        book1.setCoverImage("/edu/lms/images/Books/Home.jpg");
        book1.setAuthors("Lisa Allen");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("The Dark Side\n of The Mirror");
        book2.setCoverImage("/edu/lms/images/Books/TheDarkSideOfMirror.jpg");
        book2.setAuthors("Christopher Murphy");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("The UnderStory");
        book3.setCoverImage("/edu/lms/images/Books/TheUnderStory.jpg");
        book3.setAuthors("Saner Sangsuk");
        ls.add(book3);

        edu.lms.models.book.Book book4 = new edu.lms.models.book.Book();
        book4.setTitle("Educated");
        book4.setCoverImage("/edu/lms/images/Books/Educated.jpg");
        book4.setAuthors("Tara Westover");
        ls.add(book4);

        edu.lms.models.book.Book book5 = new edu.lms.models.book.Book();
        book5.setTitle("A million to one");
        book5.setCoverImage("/edu/lms/images/Books/a million to one.jpg");
        book5.setAuthors("Tony Faggioli");
        ls.add(book5);

        edu.lms.models.book.Book book6 = new edu.lms.models.book.Book();
        book6.setTitle("To Kill a Mockingbird");
        book6.setCoverImage("/edu/lms/images/Books/toKillaMockBird.jpg");
        book6.setAuthors("Harper Lee");
        ls.add(book6);

        edu.lms.models.book.Book book7 = new edu.lms.models.book.Book();
        book7.setTitle("Frankenstein");
        book7.setCoverImage("/edu/lms/images/Books/Frankenstein.jpg");
        book7.setAuthors("Marry Shelley");
        ls.add(book7);

        edu.lms.models.book.Book book8 = new edu.lms.models.book.Book();
        book8.setTitle("Beloved");
        book8.setCoverImage("/edu/lms/images/Books/Beloved.jpg");
        book8.setAuthors("Toni Morrison");
        ls.add(book8);


        return ls;
    }

    public List<Book> getHorrorBooks(){
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("The Shining");
        book.setCoverImage("/edu/lms/images/Books/The Shining.jpg");
        book.setAuthors("Stephen King");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("It");
        book1.setCoverImage("/edu/lms/images/Books/It.jpg");
        book1.setAuthors("Stephen King");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("’Salem’s Lot");
        book2.setCoverImage("/edu/lms/images/Books/Salems Lot.jpg");
        book2.setAuthors("Stephen King");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("Dracula");
        book3.setCoverImage("/edu/lms/images/Books/Dracula.jpg");
        book3.setAuthors("Bram Stoker");
        ls.add(book3);

        edu.lms.models.book.Book book4 = new edu.lms.models.book.Book();
        book4.setTitle("Pet Sematary");
        book4.setCoverImage("/edu/lms/images/Books/Pet Sematary.jpg");
        book4.setAuthors("Stephen King");
        ls.add(book4);

        edu.lms.models.book.Book book5 = new edu.lms.models.book.Book();
        book5.setTitle("The Exorcist");
        book5.setCoverImage("/edu/lms/images/Books/The Exorcist.jpg");
        book5.setAuthors("William Peter Blatty");
        ls.add(book5);

        edu.lms.models.book.Book book6 = new edu.lms.models.book.Book();
        book6.setTitle("The Haunting of Hill House");
        book6.setCoverImage("/edu/lms/images/Books/The Haunting of Hill House.jpg");
        book6.setAuthors("Shirley Jackson");
        ls.add(book6);

        Book book7 = new edu.lms.models.book.Book();
        book7.setTitle("Carrie");
        book7.setCoverImage("/edu/lms/images/Books/Carrie.jpg");
        book7.setAuthors("Stephen King");
        ls.add(book7);

        Book book8 = new edu.lms.models.book.Book();
        book8.setTitle("The Silence of the Lambs");
        book8.setCoverImage("/edu/lms/images/Books/The Silence of the Lambs.jpg");
        book8.setAuthors("Thomas Harris");
        ls.add(book8);

        Book book9 = new edu.lms.models.book.Book();
        book9.setTitle("Interview with the Vampire");
        book9.setCoverImage("/edu/lms/images/Books/Interview with the Vampire.jpg");
        book9.setAuthors("Anne Rice");
        ls.add(book9);

        Book book10 = new edu.lms.models.book.Book();
        book10.setTitle("Rosemary’s Baby");
        book10.setCoverImage("/edu/lms/images/Books/Rosemarys Baby.jpg");
        book10.setAuthors("Ira Levin");
        ls.add(book10);

        Book book11 = new edu.lms.models.book.Book();
        book11.setTitle("Ghost Story");
        book11.setCoverImage("/edu/lms/images/Books/Ghost Story.jpg");
        book11.setAuthors("Peter Straub");
        ls.add(book11);


        return ls;
    }

    public List<Book> getSelfhelpBooks() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();

        // Sách 1
        Book book1 = new Book();
        book1.setTitle("The Power of Now");
        book1.setCoverImage("/edu/lms/images/Books/ThePowerOfNow.jpg");
        book1.setAuthors("Eckhart Tolle");
        ls.add(book1);

        // Sách 2
        Book book2 = new Book();
        book2.setTitle("Atomic Habits");
        book2.setCoverImage("/edu/lms/images/Books/AtomicHabits.jpg");
        book2.setAuthors("James Clear");
        ls.add(book2);

        // Sách 3
        Book book3 = new Book();
        book3.setTitle("How to Win Friends and Influence People");
        book3.setCoverImage("/edu/lms/images/Books/HowToWinFriends.jpg");
        book3.setAuthors("Dale Carnegie");
        ls.add(book3);

        // Sách 4
        Book book4 = new Book();
        book4.setTitle("The Subtle Art of Not Giving a F*ck");
        book4.setCoverImage("/edu/lms/images/Books/TheSubtleArt.jpg");
        book4.setAuthors("Mark Manson");
        ls.add(book4);

        // Sách 5
        Book book5 = new Book();
        book5.setTitle("Think and Grow Rich");
        book5.setCoverImage("/edu/lms/images/Books/ThinkAndGrowRich.jpg");
        book5.setAuthors("Napoleon Hill");
        ls.add(book5);

        // Sách 6
        Book book6 = new Book();
        book6.setTitle("You Are a Badass");
        book6.setCoverImage("/edu/lms/images/Books/YouAreABadass.jpg");
        book6.setAuthors("Jen Sincero");
        ls.add(book6);

        // Sách 7
        Book book7 = new Book();
        book7.setTitle("Deep Work");
        book7.setCoverImage("/edu/lms/images/Books/DeepWork.jpg");
        book7.setAuthors("Cal Newport");
        ls.add(book7);

        // Sách 8
        Book book8 = new Book();
        book8.setTitle("Man’s Search for Meaning");
        book8.setCoverImage("/edu/lms/images/Books/MansSearchForMeaning.jpg");
        book8.setAuthors("Viktor E. Frankl");
        ls.add(book8);

        // Sách 9
        Book book9 = new Book();
        book9.setTitle("The Four Agreements");
        book9.setCoverImage("/edu/lms/images/Books/TheFourAgreements.jpg");
        book9.setAuthors("Don Miguel Ruiz");
        ls.add(book9);

        // Sách 10
        Book book10 = new Book();
        book10.setTitle("Mindset: The New Psychology of Success");
        book10.setCoverImage("/edu/lms/images/Books/Mindset.jpg");
        book10.setAuthors("Carol S. Dweck");
        ls.add(book10);

        // Sách 11
        Book book11 = new Book();
        book11.setTitle("Can’t Hurt Me");
        book11.setCoverImage("/edu/lms/images/Books/CantHurtMe.jpg");
        book11.setAuthors("David Goggins");
        ls.add(book11);

        // Sách 12
        Book book12 = new Book();
        book12.setTitle("Make Your Bed");
        book12.setCoverImage("/edu/lms/images/Books/MakeYourBed.jpg");
        book12.setAuthors("Admiral William H. McRaven");
        ls.add(book12);

        // Sách 13
        Book book13 = new Book();
        book13.setTitle("The 7 Habits of Highly Effective People");
        book13.setCoverImage("/edu/lms/images/Books/7Habits.jpg");
        book13.setAuthors("Stephen R. Covey");
        ls.add(book13);

        // Sách 14
        Book book14 = new Book();
        book14.setTitle("Big Magic");
        book14.setCoverImage("/edu/lms/images/Books/BigMagic.jpg");
        book14.setAuthors("Elizabeth Gilbert");
        ls.add(book14);

        // Sách 15
        Book book15 = new Book();
        book15.setTitle("Grit: The Power of Passion and Perseverance");
        book15.setCoverImage("/edu/lms/images/Books/Grit.jpg");
        book15.setAuthors("Angela Duckworth");
        ls.add(book15);

        // Sách 16
        Book book16 = new Book();
        book16.setTitle("Daring Greatly");
        book16.setCoverImage("/edu/lms/images/Books/DaringGreatly.jpg");
        book16.setAuthors("Brené Brown");
        ls.add(book16);

        // Sách 17
        Book book17 = new Book();
        book17.setTitle("Awaken the Giant Within");
        book17.setCoverImage("/edu/lms/images/Books/AwakenTheGiantWithin.jpg");
        book17.setAuthors("Tony Robbins");
        ls.add(book17);

        // Sách 18
        Book book18 = new Book();
        book18.setTitle("The Art of Happiness");
        book18.setCoverImage("/edu/lms/images/Books/TheArtOfHappiness.jpg");
        book18.setAuthors("Dalai Lama");
        ls.add(book18);

        // Sách 19
        Book book19 = new Book();
        book19.setTitle("Who Moved My Cheese?");
        book19.setCoverImage("/edu/lms/images/Books/WhoMovedMyCheese.jpg");
        book19.setAuthors("Spencer Johnson");
        ls.add(book19);

        // Sách 20
        Book book20 = new Book();
        book20.setTitle("Essentialism: The Disciplined Pursuit of Less");
        book20.setCoverImage("/edu/lms/images/Books/Essentialism.jpg");
        book20.setAuthors("Greg McKeown");
        ls.add(book20);

        return ls;
    }

    public List<Book> getEducationBooks() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();

        // Sách 1
        Book book1 = new Book();
        book1.setTitle("Educated");
        book1.setCoverImage("/edu/lms/images/Books/Educated.jpg");
        book1.setAuthors("Tara Westover");
        ls.add(book1);

        // Sách 2
        Book book2 = new Book();
        book2.setTitle("Why We Sleep");
        book2.setCoverImage("/edu/lms/images/Books/WhyWeSleep.jpg");
        book2.setAuthors("Matthew Walker");
        ls.add(book2);

        // Sách 3
        Book book3 = new Book();
        book3.setTitle("A Mind for Numbers");
        book3.setCoverImage("/edu/lms/images/Books/AMindForNumbers.jpg");
        book3.setAuthors("Barbara Oakley");
        ls.add(book3);

        // Sách 4
        Book book4 = new Book();
        book4.setTitle("Grit: The Power of Passion and Perseverance");
        book4.setCoverImage("/edu/lms/images/Books/Grit.jpg");
        book4.setAuthors("Angela Duckworth");
        ls.add(book4);

        // Sách 5
        Book book5 = new Book();
        book5.setTitle("The Element");
        book5.setCoverImage("/edu/lms/images/Books/TheElement.jpg");
        book5.setAuthors("Ken Robinson");
        ls.add(book5);

        // Sách 6
        Book book6 = new Book();
        book6.setTitle("Make It Stick: The Science of Successful Learning");
        book6.setCoverImage("/edu/lms/images/Books/MakeItStick.jpg");
        book6.setAuthors("Peter C. Brown");
        ls.add(book6);

        // Sách 7
        Book book7 = new Book();
        book7.setTitle("Outliers: The Story of Success");
        book7.setCoverImage("/edu/lms/images/Books/Outliers.jpg");
        book7.setAuthors("Malcolm Gladwell");
        ls.add(book7);

        // Sách 8
        Book book8 = new Book();
        book8.setTitle("The Talent Code");
        book8.setCoverImage("/edu/lms/images/Books/TheTalentCode.jpg");
        book8.setAuthors("Daniel Coyle");
        ls.add(book8);

        // Sách 9
        Book book9 = new Book();
        book9.setTitle("Drive: The Surprising Truth About What Motivates Us");
        book9.setCoverImage("/edu/lms/images/Books/Drive.jpg");
        book9.setAuthors("Daniel H. Pink");
        ls.add(book9);

        // Sách 10
        Book book10 = new Book();
        book10.setTitle("Peak: Secrets from the New Science of Expertise");
        book10.setCoverImage("/edu/lms/images/Books/Peak.jpg");
        book10.setAuthors("Anders Ericsson");
        ls.add(book10);

        // Sách 11
        Book book11 = new Book();
        book11.setTitle("Mindset: The New Psychology of Success");
        book11.setCoverImage("/edu/lms/images/Books/Mindset.jpg");
        book11.setAuthors("Carol S. Dweck");
        ls.add(book11);

        // Sách 12
        Book book12 = new Book();
        book12.setTitle("The Art of Learning");
        book12.setCoverImage("/edu/lms/images/Books/TheArtOfLearning.jpg");
        book12.setAuthors("Josh Waitzkin");
        ls.add(book12);

        // Sách 13
        Book book13 = new Book();
        book13.setTitle("Switch: How to Change Things When Change Is Hard");
        book13.setCoverImage("/edu/lms/images/Books/Switch.jpg");
        book13.setAuthors("Chip Heath & Dan Heath");
        ls.add(book13);

        // Sách 14
        Book book14 = new Book();
        book14.setTitle("The Smartest Kids in the World");
        book14.setCoverImage("/edu/lms/images/Books/TheSmartestKids.jpg");
        book14.setAuthors("Amanda Ripley");
        ls.add(book14);

        // Sách 15
        Book book15 = new Book();
        book15.setTitle("The Knowledge Gap");
        book15.setCoverImage("/edu/lms/images/Books/TheKnowledgeGap.jpg");
        book15.setAuthors("Natalie Wexler");
        ls.add(book15);

        // Sách 16
        Book book16 = new Book();
        book16.setTitle("Weapons of Math Destruction");
        book16.setCoverImage("/edu/lms/images/Books/WeaponsOfMathDestruction.jpg");
        book16.setAuthors("Cathy O'Neil");
        ls.add(book16);


        // Sách 18
        Book book18 = new Book();
        book18.setTitle("How Children Succeed");
        book18.setCoverImage("/edu/lms/images/Books/HowChildrenSucceed.jpg");
        book18.setAuthors("Paul Tough");
        ls.add(book18);

        // Sách 19
        Book book19 = new Book();
        book19.setTitle("The End of Average");
        book19.setCoverImage("/edu/lms/images/Books/TheEndOfAverage.jpg");
        book19.setAuthors("Todd Rose");
        ls.add(book19);

        // Sách 20
        Book book20 = new Book();
        book20.setTitle("The Innovator's DNA");
        book20.setCoverImage("/edu/lms/images/Books/TheInnovatorsDNA.jpg");
        book20.setAuthors("Jeff Dyer, Hal Gregersen, & Clayton M. Christensen");
        ls.add(book20);

        return ls;
    }

    public List<Book> getArtBooks() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();

        // Sách 1
        Book book1 = new Book();
        book1.setTitle("Ways of Seeing");
        book1.setCoverImage("/edu/lms/images/Books/WaysOfSeeing.jpg");
        book1.setAuthors("John Berger");
        ls.add(book1);

        // Sách 2
        Book book2 = new Book();
        book2.setTitle("The Story of Art");
        book2.setCoverImage("/edu/lms/images/Books/TheStoryOfArt.jpg");
        book2.setAuthors("E.H. Gombrich");
        ls.add(book2);

        // Sách 3
        Book book3 = new Book();
        book3.setTitle("Steal Like an Artist");
        book3.setCoverImage("/edu/lms/images/Books/StealLikeAnArtist.jpg");
        book3.setAuthors("Austin Kleon");
        ls.add(book3);

        // Sách 4
        Book book4 = new Book();
        book4.setTitle("The War of Art");
        book4.setCoverImage("/edu/lms/images/Books/TheWarOfArt.jpg");
        book4.setAuthors("Steven Pressfield");
        ls.add(book4);


        // Sách 6
        Book book6 = new Book();
        book6.setTitle("Drawing on the Right Side of the Brain");
        book6.setCoverImage("/edu/lms/images/Books/DrawingOnTheRightSide.jpg");
        book6.setAuthors("Betty Edwards");
        ls.add(book6);

        // Sách 7
        Book book7 = new Book();
        book7.setTitle("The Artist's Way");
        book7.setCoverImage("/edu/lms/images/Books/TheArtistsWay.jpg");
        book7.setAuthors("Julia Cameron");
        ls.add(book7);

        // Sách 8
        Book book8 = new Book();
        book8.setTitle("The Art Spirit");
        book8.setCoverImage("/edu/lms/images/Books/TheArtSpirit.jpg");
        book8.setAuthors("Robert Henri");
        ls.add(book8);

        // Sách 9
        Book book9 = new Book();
        book9.setTitle("Creative Confidence");
        book9.setCoverImage("/edu/lms/images/Books/CreativeConfidence.jpg");
        book9.setAuthors("Tom Kelley & David Kelley");
        ls.add(book9);

        // Sách 10
        Book book10 = new Book();
        book10.setTitle("The Letters of Vincent van Gogh");
        book10.setCoverImage("/edu/lms/images/Books/VanGoghLetters.jpg");
        book10.setAuthors("Vincent van Gogh");
        ls.add(book10);

        // Sách 11
        Book book11 = new Book();
        book11.setTitle("Imagine: How Creativity Works");
        book11.setCoverImage("/edu/lms/images/Books/Imagine.jpg");
        book11.setAuthors("Jonah Lehrer");
        ls.add(book11);

        // Sách 12
        Book book12 = new Book();
        book12.setTitle("How to Be an Artist");
        book12.setCoverImage("/edu/lms/images/Books/HowToBeAnArtist.jpg");
        book12.setAuthors("Jerry Saltz");
        ls.add(book12);

        // Sách 13
        Book book13 = new Book();
        book13.setTitle("Art as Therapy");
        book13.setCoverImage("/edu/lms/images/Books/ArtAsTherapy.jpg");
        book13.setAuthors("Alain de Botton & John Armstrong");
        ls.add(book13);

        // Sách 14
        Book book14 = new Book();
        book14.setTitle("The Art Book");
        book14.setCoverImage("/edu/lms/images/Books/TheArtBook.jpg");
        book14.setAuthors("Phaidon Editors");
        ls.add(book14);


        // Sách 16
        Book book16 = new Book();
        book16.setTitle("A History of Pictures");
        book16.setCoverImage("/edu/lms/images/Books/AHistoryOfPictures.jpg");
        book16.setAuthors("David Hockney & Martin Gayford");
        ls.add(book16);

        // Sách 17
        Book book17 = new Book();
        book17.setTitle("The Lives of the Artists");
        book17.setCoverImage("/edu/lms/images/Books/TheLivesOfArtists.jpg");
        book17.setAuthors("Giorgio Vasari");
        ls.add(book17);

        // Sách 18
        Book book18 = new Book();
        book18.setTitle("What Painting Is");
        book18.setCoverImage("/edu/lms/images/Books/WhatPaintingIs.jpg");
        book18.setAuthors("James Elkins");
        ls.add(book18);

        // Sách 19
        Book book19 = new Book();
        book19.setTitle("Art Before Breakfast");
        book19.setCoverImage("/edu/lms/images/Books/ArtBeforeBreakfast.jpg");
        book19.setAuthors("Danny Gregory");
        ls.add(book19);

        // Sách 20
        Book book20 = new Book();
        book20.setTitle("Creativity, Inc.");
        book20.setCoverImage("/edu/lms/images/Books/CreativityInc.jpg");
        book20.setAuthors("Ed Catmull");
        ls.add(book20);

        return ls;
    }





}
