package parking.model;

public abstract class Vehicle {
    private final String regNo;
    private int minutes;
    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("regNo required");
        this.regNo = regNo;
    }
    public String getRegNo() { return regNo; }
    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("duration must be >= 0");
        this.minutes = minutes;
    }
    protected abstract double ratePerHour();                       // protected: only subclasses/package
    public double calculateFee() { return ((minutes + 59) / 60) * ratePerHour(); }
    @Override public boolean equals(Object o) { return o instanceof Vehicle v && regNo.equals(v.regNo); }
    @Override public int hashCode() { return regNo.hashCode(); }
}
