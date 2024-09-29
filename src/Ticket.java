import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class Ticket {
    private String ID;
    private String concertHall;
    private String eventCode;
    private LocalDateTime date;
    private boolean isPromo;
    private char sector;
    private double backpackWeight;
    private final Date ticketCreationTime;
    private BigDecimal price;

    public Ticket() {
        ticketCreationTime = new Date();
    }

    public Ticket(String concertHall, String eventCode, LocalDateTime date) {
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.date = date;
        ticketCreationTime = new Date();
    }

    public Ticket(String ID, String concertHall, String eventCode, LocalDateTime date, boolean isPromo, char sector, double backpackWeight, BigDecimal price) {
        this.ID = ID;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.date = date;
        this.isPromo = isPromo;
        this.sector = sector;
        this.backpackWeight = backpackWeight;
        this.price = price;
        ticketCreationTime = new Date();
    }

    public String getID() {
        return ID;
    }

    public String getConcertHall() {
        return concertHall;
    }

    public String getEventCode() {
        return eventCode;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public char getSector() {
        return sector;
    }

    public double getBackpackWeight() {
        return backpackWeight;
    }

    public Date getTicketCreationTime() {
        return ticketCreationTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket Info:\n" +
                "ID: " + getID() +
                ";\nConcert Hall: " + getConcertHall() +
                ";\nEvent Code: " + getEventCode() +
                ";\nDate: " + getDate().toLocalDate() + " Time: " + getDate().toLocalTime() +
                ";\nPromo ticket: " + isPromo() +
                ";\nSector: " + getSector() +
                ";\nBackpack weight allowed: " + getBackpackWeight() +
                ";\nWas bought: " + getTicketCreationTime() +
                ";\nPrice: " + getPrice() +
                '.';
    }
}
