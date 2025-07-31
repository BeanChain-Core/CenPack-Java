package io.beanchain.tickets;

import io.beanchain.pools.OutPool;

public class TicketBill {
    private boolean executed = false;
    private pushTicket push = null;
    private responseTicket response = null;

    public TicketBill(boolean passed, OutPool outpool, String payload, String contractHash){
        this.executed = passed;

        if(outpool != null){
            this.push = new pushTicket(outpool, contractHash);
        }

        if(payload != null){
            this.response = new responseTicket(contractHash, payload);
        }
    }
}
