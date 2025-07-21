package io.beanchain.tickets;

public class responseTicket {
    private String callHash;
    private String payload;
    private long ticketTimeStamp;

    public responseTicket(String hash, String payload) {
        this.callHash = hash;
        this.payload = payload;
        this.ticketTimeStamp = System.currentTimeMillis();
    }

    public String getQueryID() { return callHash; }
    public String getPayload() { return payload; }
    public long getTicketTimeStamp() { return ticketTimeStamp; }
}
