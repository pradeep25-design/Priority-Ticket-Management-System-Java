public class Ticket {
    public static void main(String[] args) {
        TicketQueue supportQueue = new TicketQueue();
        System.out.println("--- Adding Tickets ---");
        supportQueue.addTicket(101, "Alice", "Internet down", "Normal");
        supportQueue.addTicket(102, "Bob", "Server crash", "Critical");
        supportQueue.addTicket(103, "Charlie", "Email login issue", "High");
        supportQueue.listTickets();

        System.out.println("Position of Ticket 103 (Charlie): " + supportQueue.findTicket(103));
        System.out.println("\n--- Escalating Alice ---");
        supportQueue.escalateTicket(101);
        supportQueue.listTickets();
        System.out.println("--- Resolving Front Ticket ---");
        supportQueue.resolveTicket();
        supportQueue.listTickets();
        System.out.println("--- Cancelling Ticket 103 ---");
        supportQueue.cancelTicket(103);
        supportQueue.listTickets();
    }
}
class Ticket {
    int ticketId;
    String customerName;
    String issueDescription;
    String priority;
    Ticket next;
    public Ticket(int ticketId, String customerName, String issueDescription, String priority) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.issueDescription = issueDescription;
        this.priority = priority;
    }
    public int getPriorityValue() {
        if (priority.equalsIgnoreCase("Critical")) return 3;
        if (priority.equalsIgnoreCase("High")) return 2;
        return 1;
    }
}
class TicketQueue {
    private Ticket head;
    public void addTicket(int id, String name, String desc, String priority) {
        Ticket newTicket = new Ticket(id, name, desc, priority);
        if (head == null || newTicket.getPriorityValue() > head.getPriorityValue()) {
            newTicket.next = head;
            head = newTicket;
            return;
        }
        Ticket current = head;
        while (current.next != null && current.next.getPriorityValue() >= newTicket.getPriorityValue()) {
            current = current.next;
        }
        newTicket.next = current.next;
        current.next = newTicket;
    }
    public void resolveTicket() {
        if (head != null) head = head.next;
        else System.out.println("Queue empty.");
    }
    public void escalateTicket(int ticketId) {
        if (head == null || head.ticketId == ticketId) return;
        Ticket prev = null, curr = head;
        while (curr != null && curr.ticketId != ticketId) {
            prev = curr;
            curr = curr.next;
        }
        if (curr != null) {
            prev.next = curr.next;
            curr.next = head;
            head = curr;
        }
    }
    public void cancelTicket(int ticketId) {
        if (head == null) return;
        if (head.ticketId == ticketId) { head = head.next; return; }
        Ticket curr = head;
        while (curr.next != null && curr.next.ticketId != ticketId) curr = curr.next;
        if (curr.next != null) curr.next = curr.next.next;
    }
    public int findTicket(int ticketId) {
        Ticket curr = head;
        int pos = 1;
        while (curr != null) {
            if (curr.ticketId == ticketId) return pos;
            curr = curr.next;
            pos++;
        }
        return -1;
    }
    public void listTickets() {
        Ticket curr = head;
        if (curr == null) System.out.println("Empty queue.");
        while (curr != null) {
            System.out.println("[" + curr.priority + "] ID: " + curr.ticketId + " - " + curr.customerName);
            curr = curr.next;
        }
        System.out.println();
    }
}