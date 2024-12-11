package FCLASS;

public class Borrowing {
    private Integer bookId;
    private Integer userId;
    private String borrowTime;
    private String returnTime;
    public Integer getBookId() {
        return bookId;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getBorrowTime() {
        return borrowTime;
    }
    public String getReturnTime() {
        return returnTime;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }
    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
