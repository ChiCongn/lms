package edu.lms.models.issue;

import edu.lms.models.book.Book;
import edu.lms.models.user.Client;

import java.time.LocalDate;

public class Issue {
    int issueId;
    Client reporter;
    Book issueBook;
    String description;
    String status;
    LocalDate reportedDate;
    LocalDate fixedDate;
    String remark;

    public Issue(Client reporter, Book issueBook, String description, LocalDate reportedDate) {
        this.reporter = reporter;
        this.issueBook = issueBook;
        this.description = description;
        this.reportedDate = reportedDate;
    }

    public Issue(int issueId, Client reporter, Book issueBook, String description, String status, LocalDate reportedDate) {
        this.issueId = issueId;
        this.reporter = reporter;
        this.issueBook = issueBook;
        this.description = description;
        this.status = status;
        this.reportedDate = reportedDate;
    }

    public Issue(int issueId, Client reporter, Book issueBook, String description, String status, LocalDate reportedDate, LocalDate fixedDate, String remark) {
        this.issueId = issueId;
        this.reporter = reporter;
        this.issueBook = issueBook;
        this.description = description;
        this.status = status;
        this.reportedDate = reportedDate;
        this.fixedDate = fixedDate;
        this.remark = remark;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public Client getReporter() {
        return reporter;
    }

    public void setReporter(Client reporter) {
        this.reporter = reporter;
    }

    public Book getIssueBook() {
        return issueBook;
    }

    public void setIssueBook(Book issueBook) {
        this.issueBook = issueBook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(LocalDate reportedDate) {
        this.reportedDate = reportedDate;
    }

    public LocalDate getFixedDate() {
        return fixedDate;
    }

    public void setFixedDate(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
